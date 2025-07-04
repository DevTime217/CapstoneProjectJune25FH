package com.cognixia.fh.capstone.dao;

import com.cognixia.fh.capstone.model.Book;

public class BookNotCreatedException extends Exception {
    public static final long serialVersionUID = 1L;

    public BookNotCreatedException(Book book) {
        super("Book with the following values could not be created: " + book);
    }
}
