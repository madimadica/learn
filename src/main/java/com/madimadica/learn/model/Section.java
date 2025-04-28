package com.madimadica.learn.model;

import com.madimadica.hyde.ast.AST;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Objects;

public class Section implements Comparable<Section> {

    @Override
    public int compareTo(Section o) {
        var chapterComparison = Integer.compare(this.sectionNumber.chapter, o.sectionNumber.chapter);
        if (chapterComparison != 0) return chapterComparison;
        return Integer.compare(this.sectionNumber.section, o.sectionNumber.section);
    }

    public record SectionNumber(int chapter, int section) {}

    private Path systemPath;
    private String title;
    private String urlPath;
    private String h1;
    private SectionNumber sectionNumber;
    private String markdown;
    private AST ast;

    public Path getSystemPath() {
        return systemPath;
    }

    public void setSystemPath(Path systemPath) {
        Objects.requireNonNull(systemPath);
        this.systemPath = systemPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Objects.requireNonNull(title);
        this.title = title;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        Objects.requireNonNull(urlPath);
        this.urlPath = urlPath;
    }

    public String getH1() {
        return h1;
    }

    public void setH1(String h1) {
        this.h1 = h1;
    }

    public SectionNumber getSectionNumber() {
        return sectionNumber;
    }

    public String getFormattedNumber() {
        return sectionNumber.chapter + "." + sectionNumber.section;
    }

    public void setSectionNumber(SectionNumber sectionNumber) {
        Objects.requireNonNull(sectionNumber);
        this.sectionNumber = sectionNumber;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        Objects.requireNonNull(markdown);
        this.markdown = markdown;
    }

    public AST getAst() {
        return ast;
    }

    public void setAst(AST ast) {
        this.ast = ast;
    }

    @Override
    public String toString() {
        return "Section{" +
                "systemPath=" + systemPath +
                ", title='" + title + '\'' +
                ", urlPath='" + urlPath + '\'' +
                ", h1='" + h1 + '\'' +
                ", sectionNumber=" + sectionNumber +
                '}';
    }
}
