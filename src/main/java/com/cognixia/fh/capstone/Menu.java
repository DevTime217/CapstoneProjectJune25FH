package com.cognixia.fh.capstone;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import com.cognixia.fh.capstone.dao.BookDAO;

import com.cognixia.fh.capstone.dao.BookDAOClass;
import com.cognixia.fh.capstone.dao.UserDAO;
import com.cognixia.fh.capstone.dao.UserDAOClass;
import com.cognixia.fh.capstone.model.Book;
import com.cognixia.fh.capstone.model.User;

public class Menu {
    private static Scanner sc;

    private static BookDAO bookDao = new BookDAOClass();
    private static UserDAO userDao = new UserDAOClass();
    private static User u = null;
    
    public static void mainMenu() {
        
        try {
			bookDao.establishConnection();
			userDao.establishConnection();
			
		} catch (IOException | ClassNotFoundException | SQLException e1) {
			
			System.out.println("\nCould not connect to the Book Database, application cannot run at this time.");
		}

        sc = new Scanner(System.in);

        boolean login = false;
        boolean exit = false;
        String username = null;
        String password = null;

        while(!login) {
            System.out.println("Enter username: ");
            username = sc.nextLine();
            System.out.println("\nEnter password: ");
            password = sc.nextLine();

            try {
                u = userDao.findUser(username, password).orElse(null);                
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(u == null) {
                System.out.println("\nTry again.\n");
            } else {
                login = true;
            }
        }

        System.out.println("\nWelcome to the Book Tracker Application!");

        while(!exit) {
            System.out.println("\nWhat would you like to do?");
			System.out.println("1. List all books");
			System.out.println("2. Get book by ID");
			System.out.println("3. Create book");
			System.out.println("4. Update book");
			System.out.println("5. Delete book");
			System.out.println("6. Exit");

            int input = sc.nextInt();
			sc.nextLine();

            switch (input) {
                case 1:
				    getAllBooks();
				    break;
                case 2:
                    getBookById();
                    break;
                case 3:
                    createBook();
                    break;
                case 4:
                    updateBook();
                    break;
                case 5:
                    deleteBook();
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("\nPlease enter an option listed (number 1 - 6)");
                    break;
                }
        }

        System.out.println("\n\nGoodBye!");
		
		sc.close();

        try {
			bookDao.closeConnection();
            userDao.closeConnection();
		} catch (SQLException e) {
			System.out.println("Could not close connection properly");
		}
    }

    public static void getAllBooks() {
        List<Book> books = bookDao.getAll(u);

        for(int i = 0; i < books.size(); i++) {
            System.out.println(books.get(i).toString());
        }
    }

    public static void getBookById() {
        System.out.println("Enter book id: ");
        int id = sc.nextInt();

        Book book = bookDao.findById(u, id).orElse(null);

        if(book == null) {
            System.out.println("Book not found.");
        } else {
            System.out.println(book.toString());
        }
    }

    public static void createBook() {
        System.out.println("Enter book name: ");
        String name = sc.nextLine();
        System.out.println("Enter book author: ");
        String author = sc.nextLine();
        System.out.println("Enter book progress: ");
        String progress = sc.nextLine();

        Book book = new Book(0, name, author, progress);

        try {
            bookDao.add(u, book);
            System.out.println("Book created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateBook() {
        System.out.println("Enter book id: ");
        int id = sc.nextInt();
        sc.nextLine();
        
        System.out.println("Enter book name: ");
        String name = sc.nextLine();
        
        System.out.println("Enter book author: ");
        String author = sc.nextLine();
        
        System.out.println("Enter book progress: ");
        String progress = sc.nextLine();

        Book book = new Book(id, name, author, progress);

        if(!bookDao.update(u, book)) {
            System.out.println("Book not found.");
        } else {
            System.out.println("Book updated");
        }
    }

    public static void deleteBook() {
        System.out.println("Enter book id: ");
        int id = sc.nextInt();

        if(!bookDao.delete(u, id)) {
            System.out.println("Book not found.");
        } else {
            System.out.println("Book deleted.");
        }
    }
}
