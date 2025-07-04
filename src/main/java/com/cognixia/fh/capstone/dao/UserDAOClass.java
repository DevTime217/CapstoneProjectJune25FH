package com.cognixia.fh.capstone.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.cognixia.fh.capstone.connection.ConnectionManager;
import com.cognixia.fh.capstone.model.User;

public class UserDAOClass implements UserDAO{

    private Connection connection = null;

    @Override
    public void establishConnection() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
		
		if(connection == null) {
			connection = ConnectionManager.getConnection();
		}
	}

    @Override
    public void closeConnection() throws SQLException {
        connection.close();
    }

    @Override
    public Optional<User> findUser(String username, String password) throws UserNotFoundException{
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM user WHERE username = ? and password = ?");
            pStmt.setString(1, username);
            pStmt.setString(2, password);

            ResultSet rs = pStmt.executeQuery();
            User u = null;

            while (rs.next()) {
                int id = rs.getInt(1);
                String resultUsername = rs.getString(2);
                String resultPassword = rs.getString(3);

                u = new User(id, resultUsername, resultPassword);
            }

            Optional<User> opt = Optional.of(u);
            return opt;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
