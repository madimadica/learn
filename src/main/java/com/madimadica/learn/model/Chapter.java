package com.madimadica.learn.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Chapter implements Comparable<Chapter> {
    private int number;
    private String title;
    private Path systemPath;
    private String urlPath;
    private String description;
    private List<Section> sections = new ArrayList<>();

    public Path getSystemPath() {
        return systemPath;
    }

    public void setSystemPath(Path systemPath) {
        Objects.requireNonNull(systemPath);
        this.systemPath = systemPath;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public void addSection(Section section) {
        Objects.requireNonNull(section);
        this.sections.add(section);
    }

    public int getNumber() {
        return number;
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

    public List<Section> getSections() {
        return sections;
    }

    public List<Section> getSortedSections() {
        return sections.stream().sorted().toList();
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "number=" + number +
                ", title='" + title + '\'' +
                ", systemPath=" + systemPath +
                ", urlPath='" + urlPath + '\'' +
                ", description='" + description + '\'' +
                ", sections=" + sections +
                '}';
    }

    @Override
    public int compareTo(Chapter o) {
        return Integer.compare(this.number, o.number);
    }
}
