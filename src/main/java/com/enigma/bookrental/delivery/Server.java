package com.enigma.bookrental.delivery;

import com.enigma.bookrental.config.JPAConfig;
import com.enigma.bookrental.dao.BookDAO;
import com.enigma.bookrental.dao.MemberDAO;
import com.enigma.bookrental.dao.RentalDAO;
import com.enigma.bookrental.delivery.controller.BookController;
import com.enigma.bookrental.delivery.controller.MemberController;
import com.enigma.bookrental.delivery.controller.RentalController;
import com.enigma.bookrental.delivery.controller.ReportController;
import com.enigma.bookrental.service.BookService;
import com.enigma.bookrental.service.MemberService;
import com.enigma.bookrental.service.RentalService;

import java.util.Scanner;

public class Server {
    private final Scanner scanner = new Scanner(System.in);
    private final MemberService memberService;
    private final BookService bookService;
    private final RentalService rentalService;

    public Server(MemberService memberService, BookService bookService, RentalService rentalService){
        this.memberService = memberService;
        this.bookService = bookService;
        this.rentalService = rentalService;
    }

    public void start(){
        while(true){
            System.out.println();
            System.out.println("======== RENTAL BOOK SYSTEM ========");
            System.out.println("1. Member Menu");
            System.out.println("2. Book Menu");
            System.out.println("3. Rental Menu");
            System.out.println("4. Report Menu");
            System.out.println("0. EXIT");
            System.out.println();
            System.out.print("Choose menu: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> new MemberController(memberService).showMenu();
                case 2 -> new BookController(bookService).showMenu();
                case 3 -> new RentalController(rentalService).showMenu();
                case 4 -> new ReportController(bookService, memberService, rentalService).showMenu();
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
        RentalDAO rentalDAO = new RentalDAO();
        MemberService memberService = new MemberService(memberDAO);
        BookService bookService = new BookService(bookDAO);
        RentalService rentalService = new RentalService(rentalDAO, memberService, bookService);

        return new Server(memberService, bookService, rentalService);
    }



}
