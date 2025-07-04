package com.cognixia.fh.capstone.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import com.cognixia.fh.capstone.model.User;

public interface UserDAO {
    
    public void establishConnection() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException;
	
	public void closeConnection() throws SQLException;

    public Optional<User> findUser (String username, String password) throws UserNotFoundException;
}
