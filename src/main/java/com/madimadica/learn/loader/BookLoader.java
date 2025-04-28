package com.madimadica.learn.loader;

import com.madimadica.learn.model.Book;
import com.madimadica.learn.model.Chapter;
import com.madimadica.learn.model.Section;
import org.yaml.snakeyaml.Yaml;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.madimadica.learn.loader.SystemUtils.getDirName;
import static com.madimadica.learn.loader.SystemUtils.readFile;

public class BookLoader {

    private final Path rootPath;

    public BookLoader(String rootDirectory) {
        this.rootPath = Path.of(rootDirectory);
    }

    public List<Book> load() {
        var bookDirectories = SystemUtils.listDirectories(rootPath);
        List<Book> books = new ArrayList<>();
        for (var dir : bookDirectories) {
            Book book = loadBook(dir);
            books.add(book);
        }
        return books;
    }

    private static Map<String, Object> readYaml(Path dir, String fileName) {
        return new Yaml().load(readFile(dir.resolve(fileName)));
    }

    public Book loadBook(Path bookDir) {
        Book book = new Book();
        book.setSystemPath(bookDir);

        var bookYaml = readYaml(bookDir, "book.yml");
        book.setTitle((String) bookYaml.get("title"));
        book.setDescription((String) bookYaml.get("description"));
        if (bookYaml.containsKey("path")) {
            book.setUrlPath((String) bookYaml.get("path")); // Override the directory's path name
        } else {
            book.setUrlPath(getDirName(bookDir)); // Use the directory name as the url path
        }
        List<String> categories = (List<String>) bookYaml.getOrDefault("categories", List.of());
        categories.forEach(book::addCategory);

        var chapterDirs = SystemUtils.listDirectories(bookDir);
        for (var chapterDir : chapterDirs) {
            if (SystemUtils.getDirName(chapterDir).startsWith("chapter-")) {
                book.addChapter(loadChapter(chapterDir));
            } else {
                System.err.println("Skipping chapter " + chapterDir);
            }
        }
        return book;
    }

    static Chapter loadChapter(Path dir) {
        var chapterFiles = SystemUtils.listFiles(dir);
        Chapter chapter = new Chapter();
        chapter.setSystemPath(dir);
        chapter.setNumber(parseChapterNumber(getDirName(dir)));
        var yaml = readYaml(dir, "chapter.yml");
        chapter.setTitle((String) yaml.get("title"));
        chapter.setUrlPath((String) yaml.get("path"));
        chapter.setDescription((String) yaml.get("description"));

        for (var filePath : chapterFiles) {
            if (SystemUtils.isFileType(filePath, ".md")) {
                chapter.addSection(loadSection(filePath));
            }
        }
        return chapter;
    }

    // todo generate all the books/sections, then do the rendering. Might need to prepend the link refs each time, or modify the map with a new API

    /**
     * Loads the section data by parsing frontmatter and trimming it off, setting the remaining markdown content.
     * This DOES NOT perform a markdown parsing.
     */
    static Section loadSection(Path filePath) {
        var sectionNumber = parseSectionNumber(filePath.getFileName().toString());
        Section section = new Section();
        section.setSystemPath(filePath);
        section.setSectionNumber(sectionNumber);
        String rawContents = readFile(filePath);

        var matcher = FRONTMATTER_END_PATTERN.matcher(rawContents);
        if (matcher.find(3)) { // Skip opening ---
            int frontmatterStart = rawContents.indexOf("\n") + 1; // works with \r?\n
            int frontmatterEnd = matcher.start();
            int markdownStart = matcher.end();
            String frontmatter = rawContents.substring(frontmatterStart, frontmatterEnd);
            String markdown = rawContents.substring(markdownStart);
            Map<String, String> yaml = new Yaml().load(frontmatter);
            String title = yaml.get("title");
            String path = yaml.get("path");
            if (path == null) {
                path = toKebabCase(title);
            }
            section.setUrlPath(path);
            section.setTitle(title);
            section.setH1(yaml.getOrDefault("h1", title));
            section.setMarkdown(markdown);
        } else {
            throw new IllegalArgumentException("Cannot find frontmatter for " + filePath);
        }
        return section;
    }

    private static final Pattern CHAPTER_PATTERN = Pattern.compile("^chapter-(\\d+)");
    private static final Pattern SECTION_PATTERN = Pattern.compile("^section-(\\d+)\\.(\\d+)");
    private static final Pattern FRONTMATTER_END_PATTERN = Pattern.compile("\\R---\\R");

    private static int parseChapterNumber(String chapterDirName) {
        Matcher matcher = CHAPTER_PATTERN.matcher(chapterDirName);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            throw new RuntimeException("Invalid chapter directory name format. Expected 'chapter-*' but got <" + chapterDirName + ">");
        }
    }

    private static Section.SectionNumber parseSectionNumber(String sectionFileName) {
        Matcher matcher = SECTION_PATTERN.matcher(sectionFileName);
        if (matcher.find()) {
            int chapter = Integer.parseInt(matcher.group(1));
            int section = Integer.parseInt(matcher.group(2));
            return new Section.SectionNumber(chapter, section);
        } else {
            throw new RuntimeException("Invalid section file name format. Expected 'section-#.#*' but got <" + sectionFileName + ">");
        }
    }

    private static String toKebabCase(String input) {
        return input
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")  // Replace non-letter/number sequences with -
                .replaceAll("(^-+|-+$)", "");   // Remove leading/trailing dashes
    }


}
