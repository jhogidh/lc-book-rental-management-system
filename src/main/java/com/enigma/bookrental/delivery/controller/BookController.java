package com.enigma.bookrental.delivery.controller;

import com.enigma.bookrental.model.Book;
import com.enigma.bookrental.service.BookService;

import java.util.Scanner;

public class BookController {
    private final BookService bookService;
    private final Scanner scanner = new Scanner(System.in);

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    public void showMenu(){
        System.out.println("Welcome to Book Menu");
        System.out.println("1. Create Book");
        System.out.println("2. Update Book");
        System.out.println("3. Find Book");
        System.out.println("4. Delete Book");
        System.out.println("0. Exit");
        System.out.print("Please enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice){
            case 1:
                createHandler();
                break;
            case 2:
                updateHandler();
                break;
            case 3:
                System.out.println("Find Book");
                System.out.println("1. find all");
                System.out.println("2. find available");
                System.out.println("3. search by title");
                System.out.println("4. search by author");
                System.out.println("5. find by id");
                System.out.println("0. Go back");
                System.out.print("Please enter your choice: ");
                int findChoice = Integer.parseInt(scanner.nextLine());
                switch (findChoice){
                    case 1:
                        findAllHandler();
                        break;
                    case 2:
                        findAvailableHandler();
                        break;
                    case 3:
                        searchByTitleHandler();
                        break;
                    case 4:
                        searchByAuthorHandler();
                        break;
                    case 5:
                        findByIdHandler();
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
                break;
            case 4:
                deleteHandler();
                break;
            case 0:
                System.out.println("Thank you for using our service");
            return;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    public void createHandler(){
        System.out.println("Create Book");
        System.out.println("Please enter Title: ");
        String title = scanner.nextLine();
        if (title.isBlank()){
            System.out.println("Title cannot be blank");
            return;
        }
        System.out.println("Please enter author: ");
        String author = scanner.nextLine();
        if (author.isBlank()){
            System.out.println("Author cannot be blank");
            return;
        }
        System.out.println("Please enter stock: ");
        Integer stock = Integer.parseInt(scanner.nextLine());
        if(stock < 0){
            System.out.println("Stock must be greater than 0");
            return;
        }
        Book book = Book.builder().title(title).author(author).stock(stock).build();
        bookService.addBook(book);
        System.out.println("Book created");
    }

    public void updateHandler(){
        System.out.println("Update Book");
        System.out.println("Please enter id: ");
        Long id = Long.parseLong(scanner.nextLine());
        Book book = bookService.findById(id);
        System.out.println("Please enter Title (enter to keep old value): ");
        String title = scanner.nextLine();
        if (title.isBlank()){
            title = book.getTitle();
        }
        System.out.println("Please enter author (enter to keep old value): ");
        String author = scanner.nextLine();
        if (author.isBlank()){
            author = book.getAuthor();
        }
        System.out.println("Please enter stock (enter to keep old value): ");
        Integer stock;
        try{
             stock = Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException e){
            System.out.println("Using old value for stock");
            stock = book.getStock();
        }
        if (stock < 0){
            System.out.println("Stock must be greater than 0");
            return;
        }
        book.setTitle(title);
        book.setAuthor(author);
        book.setStock(stock);
        bookService.updateBook(book);
        System.out.println("Book updated");
    }

    public void deleteHandler(){
        System.out.println("Delete Book");
        System.out.println("Please enter id: ");
        Long id = Long.parseLong(scanner.nextLine());
        bookService.deleteBook(id);
        System.out.println("Book deleted");
    }

    public void findByIdHandler(){
        System.out.println("Find Book By Id");
        System.out.println("Please enter id: ");
        Long id = Long.parseLong(scanner.nextLine());
        Book book = bookService.findById(id);
        System.out.println(book);
    }

    public void findAllHandler(){
        System.out.println("Find All Books");
        bookService.findAll().forEach(System.out::println);
    }

    public void findAvailableHandler(){
        System.out.println("Find Available Books");
        bookService.findAvailable().forEach(System.out::println);
    }

    public void searchByTitleHandler(){
        System.out.println("Search By Title");
        System.out.println("Please enter title: ");
        String title = scanner.nextLine();
        bookService.searchByTitle(title).forEach(System.out::println);
    }

    public void searchByAuthorHandler(){
        System.out.println("Search By Author");
        System.out.println("Please enter author: ");
        String author = scanner.nextLine();
        bookService.searchByAuthor(author).forEach(System.out::println);
    }


}
