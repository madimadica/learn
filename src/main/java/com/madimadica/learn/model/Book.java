package com.madimadica.learn.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book {
    private String title;
    private Path systemPath;
    private String urlPath;
    private String description;
    private List<String> categories = new ArrayList<>();
    private List<Chapter> chapters = new ArrayList<>();

    public Path getSystemPath() {
        return systemPath;
    }

    public void setSystemPath(Path systemPath) {
        Objects.requireNonNull(systemPath);
        this.systemPath = systemPath;
    }

    public void setTitle(String title) {
        Objects.requireNonNull(title);
        this.title = title;
    }

    public void setUrlPath(String urlPath) {
        Objects.requireNonNull(urlPath);
        this.urlPath = urlPath;
    }

    public void setDescription(String description) {
        Objects.requireNonNull(description);
        this.description = description;
    }

    public void addCategory(String category) {
        Objects.requireNonNull(category);
        this.categories.add(category);
    }

    public void addChapter(Chapter chapter) {
        Objects.requireNonNull(chapter);
        this.chapters.add(chapter);
    }

    public String getTitle() {
        return title;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public List<Chapter> getSortedChapters() {
        return chapters.stream().sorted().toList();
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", systemPath=" + systemPath +
                ", urlPath='" + urlPath + '\'' +
                ", description='" + description + '\'' +
                ", categories=" + categories +
                ", chapters=" + chapters +
                '}';
    }
}
