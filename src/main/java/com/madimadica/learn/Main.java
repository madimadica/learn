package com.madimadica.learn;

import com.madimadica.betterenv.BetterEnv;
import com.madimadica.learn.loader.BookGenerator;
import com.madimadica.learn.loader.BookLoader;
import com.madimadica.learn.model.Book;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String bookRoot = BetterEnv.get("BOOKS_ROOT_PATH").orElse("C:/Code/madimadica/learn/books");

        var loader = new BookLoader(bookRoot);
        var books = loader.load();
        var generator = new BookGenerator(Path.of("C:/Code/madimadica/learn/docs"));
        Book java = books.get(0);
        generator.generateBook(java);
    }
}
