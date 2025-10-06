package com.enigma.bookrental.delivery;

import com.enigma.bookrental.config.JPAConfig;
import com.enigma.bookrental.dao.BookDAO;
import com.enigma.bookrental.dao.MemberDAO;
import com.enigma.bookrental.delivery.controller.BookController;
import com.enigma.bookrental.delivery.controller.MemberController;
import com.enigma.bookrental.service.BookService;
import com.enigma.bookrental.service.MemberService;

import java.util.Scanner;

public class Server {
    private final Scanner scanner = new Scanner(System.in);
    private final MemberService memberService;
    private final BookService bookService;

    public Server(MemberService memberService, BookService bookService){
        this.memberService = memberService;
        this.bookService = bookService;
    }

    public void start(){
        while(true){
            System.out.println("======== RENTAL BOOK SYSTEM ========");
            System.out.println("1. Member Menu");
            System.out.println("2. Book Menu");
            System.out.println("0. EXIT");
            System.out.print("Choose menu: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> new MemberController(memberService).showMenu();
                case 2 -> new BookController(bookService).showMenu();
                case 0 -> {
                    System.out.println("Thank you for using our service");
                    JPAConfig.closeEmf();
                    return;
                }
                default -> System.out.println("Choose a valid option");
            }
        }
    }

    public static Server serve(){
        MemberDAO memberDAO = new MemberDAO();
        BookDAO bookDAO = new BookDAO();
        MemberService memberService = new MemberService(memberDAO);
        BookService bookService = new BookService(bookDAO);

        return new Server(memberService, bookService);
    }



}
