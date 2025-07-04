package com.cognixia.fh.capstone.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.cognixia.fh.capstone.model.Book;
import com.cognixia.fh.capstone.model.User;

public interface BookDAO {
    
    public void establishConnection() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException;
	
	public void closeConnection() throws SQLException ;

    public List<Book> getAll(User user);

    public Optional<Book> findById(User user, int id);

    public boolean update(User user, Book book);

    public boolean delete(User user, int id);

    public void add(User user, Book book) throws BookNotCreatedException;
}
