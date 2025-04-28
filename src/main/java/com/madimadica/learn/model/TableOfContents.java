package com.madimadica.learn.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TableOfContents {
    private Book book;
    private final List<Entry> entries = new ArrayList<>();

    public record Entry(String chapterBaseUrl, String url, String metaTitle, String labelTitle, int depth) {}

    public static TableOfContents of(Book book) {
        var toc = new TableOfContents();
        toc.book = book;
        String bookUrl = "/" + book.getUrlPath();
        for (var chapter : book.getSortedChapters()) {
            String chapterUrl = bookUrl + '/' + chapter.getUrlPath();
            String chapterTitle = "Chapter %d: %s".formatted(chapter.getNumber(), chapter.getTitle());
            toc.entries.add(new Entry(chapterUrl, chapterUrl + "/index.html", chapterTitle, chapterTitle, 1));
            for (var section : chapter.getSortedSections()) {
                String sectionUrl = chapterUrl + '/' + section.getUrlPath() + ".html";
                String sectionMetaTitle = "Section %s: %s".formatted(section.getFormattedNumber(), section.getTitle());
                toc.entries.add(new Entry(chapterUrl, sectionUrl, sectionMetaTitle, section.getFormattedNumber() + " " + section.getTitle() , 2));
            }
        }
        return toc;
    }

    public Book getBook() {
        return book;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public Optional<Entry> get(int index) {
        try {
            return Optional.of(entries.get(index));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }
}
