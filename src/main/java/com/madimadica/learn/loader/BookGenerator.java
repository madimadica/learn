package com.madimadica.learn.loader;

import com.madimadica.hyde.ast.*;
import com.madimadica.hyde.parser.Parser;
import com.madimadica.hyde.renderer.HtmlAstRenderer;
import com.madimadica.learn.model.Book;
import com.madimadica.learn.model.Chapter;
import com.madimadica.learn.model.Section;
import com.madimadica.learn.model.TableOfContents;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class BookGenerator {

    private final Path baseOutputDir;
    private TableOfContents toc;
    private int tocIndex;

    public BookGenerator(Path baseOutputDir) {
        this.baseOutputDir = baseOutputDir;
    }

    // TODO quizzes

    // TODO formatting code
    // TODO global links

    public void generateBook(Book book) throws IOException {
        Path bookDir = baseOutputDir.resolve(book.getUrlPath());
        if (Files.exists(bookDir)) {
            // Delete the previously generated book
            SystemUtils.deleteDirectoryRecursive(bookDir);
        }
        Files.createDirectories(bookDir); // Chapter dir, e.g. /java


        this.toc = TableOfContents.of(book);
        this.tocIndex = -1; // Hacky way to trigger diff between book page and chapter 1

        // Book index.html
        StringBuilder markdown = new StringBuilder("""
                # %s
                
                %s
                
                """.formatted(book.getTitle(), book.getDescription()));

        var chapters = book.getSortedChapters();

        var tocChapters = toc.getEntries().stream().filter(entry -> entry.depth() == 1).toList();
        for (var tocChapter : tocChapters) {
            markdown.append("%n * [%s](<%s> \"%s\")".formatted(tocChapter.labelTitle(), tocChapter.url(), tocChapter.labelTitle()));
        }

        AST ast = Parser.parse(markdown.toString());
        var renderer = new HtmlAstRenderer();
        String mainHtml = renderer.render(ast);
        String fullHtml = getFullHtml(mainHtml);
        Path bookIndexPath = bookDir.resolve("index.html");
        Files.writeString(bookIndexPath, fullHtml);

        tocIndex = 0;

        // Generate each chapter
        for (var chapter : chapters) {
            generateChapter(bookDir, chapter);
        }

    }

    void generateChapter(Path bookDir, Chapter chapter) throws IOException {
        Path chapterDir = bookDir.resolve(chapter.getUrlPath());
        Files.createDirectory(chapterDir); // Chapter dir, e.g. /java/loops

        // Copy images if they exist
        var originalImages = chapter.getSystemPath().resolve("img");
        System.out.println(originalImages);
        if (Files.exists(originalImages)) {
            SystemUtils.copyDirectoryFiles(originalImages, chapterDir.resolve("img"));
        }

        StringBuilder markdown = new StringBuilder("""
                # Chapter %d: %s
                                
                %s
                                
                """.formatted(chapter.getNumber(), chapter.getTitle(), chapter.getDescription()));

        var sections = chapter.getSortedSections();

        for (int i = 0; i < sections.size(); ++i) {
            var sectionEntry = toc.get(tocIndex + i + 1).orElseThrow();
            markdown.append("%n * [%s](<%s> \"%s\")".formatted(sectionEntry.labelTitle(), sectionEntry.url(), sectionEntry.labelTitle()));
        }

        AST ast = Parser.parse(markdown.toString());
        var renderer = new HtmlAstRenderer();
        String mainHtml = renderer.render(ast);
        String fullHtml = getFullHtml(mainHtml);
        Path chapterIndexPath = chapterDir.resolve("index.html");
        Files.writeString(chapterIndexPath, fullHtml);

        tocIndex++;

        for (var section : sections) {
            generateSection(chapterDir, section);
            tocIndex++;
        }
    }

    private String getCurrentUrl() {
        return toc.get(tocIndex).get().url();
    }

    void generateSection(Path chapterDir, Section section) throws IOException {
        Path sectionFile = chapterDir.resolve(section.getUrlPath() + ".html");

        String markdown = "# %s %s%n"
                .formatted(section.getFormattedNumber(), section.getH1())
                + section.getMarkdown();

        AST ast = Parser.parse(markdown);

        // Replace shortcut image syntax, also wrap in divs
        String chapterImageRoot = this.toc.get(tocIndex).get().chapterBaseUrl() + "/img/";
        for (var it : ast) {
            if (it.isEntering()) {
                if (it.node() instanceof InlineImageNode imageNode) {
                    String originalDest = imageNode.getDestination();
                    if (originalDest.startsWith("./")) {
                        imageNode.setDestination(chapterImageRoot + originalDest.substring(2));
                    }
                    if (imageNode.getParent() instanceof ParagraphNode parent && parent.getLastChild() == imageNode) {
                        StringJoiner classes = new StringJoiner(" ");
                        classes.add("d-flex").add("img-wrapper");
                        List<String> parts = List.of(imageNode.getDestination().split("::"));
                        for (int i = 1; i < parts.size(); ++i) {
                            String part = parts.get(i);
                            if (part.equals("centered")) {
                                classes.add("justify-content-center").add("align-items-center");
                            } else {
                                classes.add(part);
                            }
                        }
                        imageNode.setDestination(parts.getFirst());

                        parent.insertBefore(new InlineHTMLNode("<div class=\"%s\">".formatted(classes.toString())));
                        parent.insertBefore(imageNode);
                        parent.insertBefore(new InlineHTMLNode("</div>"));
                        parent.unlink();
                    }

                }
            }
        }

        var renderer = new HtmlAstRenderer();
        String mainHtml = renderer.render(ast);

        String fullHtml = getFullHtml(mainHtml);
        Files.writeString(sectionFile, fullHtml);
    }

    private String getFullHtml(String mainHtml) {
        return """
                <!DOCTYPE html>
                <html lang="en">
                %s
                <body>
                %s
                %s
                <main class="container">
                %s
                </main>
                %s
                </body>
                </html>
                """.formatted(
                getHeadElement(),
                getHeader(),
                getNav(),
                mainHtml,
                getClosingScripts()
        );
    }

    private String getHeadElement() {
        String title = tocIndex == -1 ? this.toc.getBook().getTitle() : toc.get(tocIndex).get().metaTitle();
        return """
                <head>
                  <title>%s</title>
                  <meta charset="UTF-8" />
                  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                  <link rel="apple-touch-icon" sizes="180x180" href="/apple-touch-icon.png" />
                  <link rel="icon" type="image/png" sizes="32x32" href="/favicon-32x32.png" />
                  <link rel="icon" type="image/png" sizes="16x16" href="/favicon-16x16.png" />
                  <link rel="manifest" href="/site.webmanifest" />
                  <link
                    rel="stylesheet"
                    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
                  />
                  <link
                    rel="stylesheet"
                    href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
                  />
                  <link rel="stylesheet" href="/static/styles.css" />
                </head>
                """.formatted(title);
    }

    private static String getHeader() {
        return """
                <header>
                  <nav id="madimadica-navbar">
                    <div class="container-fluid splitter">
                      <a class="home-link" href="/">
                        <img src="/static/brand.png" alt="main-icon" />
                      </a>
                      <div class="other-links-wide">
                        <div class="other-link">
                          <a href="/java-intro/">Introduction to Java</a>
                        </div>
                        <div class="other-link">
                          <a href="/advanced-java/">Advanced Java</a>
                        </div>
                      </div>
                      <div id="other-links-narrow">
                        All eBooks
                        <i id="other-links-down" class="bi bi-chevron-down"></i>
                        <i id="other-links-up" class="bi bi-chevron-up"></i>
                        <ul>
                          <li><a href="/java-intro/">Introduction to Java</a></li>
                          <li><a href="/advanced-java/">Advanced Java</a></li>
                        </ul>
                      </div>
                    </div>
                  </nav>
                </header>
                """;
    }

    private String getNav() {
        String s = "<nav id=\"bottom-page-nav\">";
        var $prev = toc.get(tocIndex - 1);
        var $next = toc.get(tocIndex + 1);
        String backUrl;
        String backTitle;
        if (tocIndex == -1) {
            backUrl = "/";
            backTitle = "Home";
        } else if (tocIndex == 0) {
            backUrl = "/" + this.toc.getBook().getUrlPath();
            backTitle = this.toc.getBook().getTitle();
        } else if ($prev.isPresent()) {
            backUrl = $prev.get().url();
            backTitle = $prev.get().labelTitle();
        } else {
            throw new IllegalStateException();
        }
        s += """
                   <a
                     aria-keyshortcuts="Left"
                     aria-label="Previous page"
                     class="prev-link"
                     href="%s"
                     rel="prev"
                     title="%s"
                   >
                     <i class="bi bi-chevron-left"></i>
                   </a>
                    """.formatted(backUrl, backTitle);

        if ($next.isPresent()) {
            s += """
                   <a
                     aria-keyshortcuts="Right"
                     aria-label="Next page"
                     class="next-link"
                     href="%s"
                     rel="next"
                     title="%s"
                   >
                     <i class="bi bi-chevron-right"></i>
                   </a>
                    """.formatted($next.get().url(), $next.get().labelTitle());
        }
        s += "</nav>";
        return s;
    }

    private static String getClosingScripts() {
        return """
                <script
                  src="https://code.jquery.com/jquery-3.7.1.min.js"
                  integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
                  crossorigin="anonymous"
                ></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
                <script src="/static/functions.js"></script>
                """;
    }
}
