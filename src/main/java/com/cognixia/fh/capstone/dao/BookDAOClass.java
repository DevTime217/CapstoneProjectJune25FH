package com.cognixia.fh.capstone.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cognixia.fh.capstone.connection.ConnectionManager;
import com.cognixia.fh.capstone.model.Book;
import com.cognixia.fh.capstone.model.User;

public class BookDAOClass implements BookDAO{

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
    public List<Book> getAll(User user) {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pStmt = connection.prepareStatement("SELECT book_id, name, author, progress FROM jointable WHERE username = ?");
            pStmt.setString(1, user.getUsername());
            ResultSet rs = pStmt.executeQuery();

            List<Book> books = new ArrayList<>();
            
            while(rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String author = rs.getString(3);
                String progress = rs.getString(4);

                Book b = new Book(id, name, author, progress);

                books.add(b);
            }

            return books;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Optional<Book> findById(User user, int id) {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pStmt = connection.prepareStatement("SELECT book_id, name, author, progress FROM jointable WHERE book_id = ? and user_id = ?");
            pStmt.setInt(1, id);
            pStmt.setInt(2, user.getId());

            ResultSet rs = pStmt.executeQuery();
            Book b = null;

            while(rs.next()) {
                int resultId = rs.getInt(1);
                String name = rs.getString(2);
                String author = rs.getString(3);
                String progress = rs.getString(4);

                b = new Book(resultId, name, author, progress);
            }

            Optional<Book> opt = Optional.of(b);
            return opt;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public boolean update(User user, Book book) {
        try {
            connection = ConnectionManager.getConnection();

            PreparedStatement pStmt1 = connection.prepareStatement("SET sql_safe_updates=0");

            PreparedStatement pStmt2 = connection.prepareStatement("UPDATE jointable SET name = ?, author = ?, progress = ? WHERE book_id = ? AND user_id = ?");
            pStmt2.setString(1, book.getName());
            pStmt2.setString(2, book.getAuthor());
            pStmt2.setString(3, book.getProgress());
            pStmt2.setInt(4, book.getId());
            pStmt2.setInt(5, user.getId());

            PreparedStatement pStmt3 = connection.prepareStatement("SET sql_safe_updates=1");

            pStmt1.executeUpdate();
			pStmt2.executeUpdate();
            pStmt3.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }

    @Override
    public boolean delete(User user, int id) {
        try {
			connection = ConnectionManager.getConnection();

            PreparedStatement pStmt1 = connection.prepareStatement("SET sql_safe_updates=0");

			PreparedStatement pStmt2 = connection.prepareStatement("DELETE FROM jointable WHERE book_id = ? and user_id = ?");
			pStmt2.setInt(1, id);
            pStmt2.setInt(2, user.getId());

            PreparedStatement pStmt3 = connection.prepareStatement("SET sql_safe_updates=1");
            
            pStmt1.executeUpdate();
			pStmt2.executeUpdate();
            pStmt3.executeUpdate();

			return true;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
    }

    @Override
    public void add(User user, Book book) throws BookNotCreatedException{
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pStmt1 = connection.prepareStatement("SET @max_book = 0");

            PreparedStatement pStmt2 = connection.prepareStatement("SELECT max(book_id) from jointable into @max_book");

            PreparedStatement pStmt3 = connection.prepareStatement("INSERT INTO jointable (user_id, username, book_id, name, author, progress) VALUES (?, ?, @max_book + 1, ?, ?, ?)");
            pStmt3.setInt(1, user.getId());
            pStmt3.setString(2, user.getUsername());
            pStmt3.setString(3, book.getName());
            pStmt3.setString(4, book.getAuthor());
            pStmt3.setString(5, book.getProgress());

            pStmt1.executeUpdate();
            pStmt2.executeQuery();
            pStmt3.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
