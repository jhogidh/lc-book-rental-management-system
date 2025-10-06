package com.enigma.bookrental.delivery.controller;

import com.enigma.bookrental.model.Book;
import com.enigma.bookrental.model.Member;
import com.enigma.bookrental.model.Rental;
import com.enigma.bookrental.service.BookService;
import com.enigma.bookrental.service.MemberService;
import com.enigma.bookrental.service.RentalService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReportController {
    private final BookService bookService;
    private final MemberService memberService;
    private final RentalService rentalService;
    private final Scanner scanner = new Scanner(System.in);

    public ReportController(BookService bookService, MemberService memberService, RentalService rentalService){
        this.bookService = bookService;
        this.memberService = memberService;
        this.rentalService = rentalService;
    }

    public void showMenu(){
        System.out.println();
        System.out.println("Welcome to Report Menu");
        System.out.println("1. Report All book with stock");
        System.out.println("2. Report All Book Rented");
        System.out.println("3. Report Member with book rented");
        System.out.println("0. Go back");
        System.out.println();
        System.out.print("please enter your choice: ");
        Integer choice = Integer.parseInt(scanner.nextLine());

        switch (choice){
            case 1:
                reportAllBookWithStockHandler();
                break;
            case 2:
                reportAllBookRentedHandler();
                break;
            case 3:
                reportMemberWithBookRented();
                break;
            case 0:
                System.out.println("Back to main menu");
                break;
            default: System.out.println("Invalid choice");
        }

    }

    public void reportAllBookWithStockHandler(){
        System.out.println();
        System.out.println("Report All Book With Stock");

        List<Book> books = bookService.findAll();

        if (books.isEmpty()){
            System.out.println("No book found");
            return;
        }

        books.forEach(book -> {
            System.out.println(book);
        });

        System.out.println("Books Total: " + books.size());
    }
    public void reportAllBookRentedHandler(){
        System.out.println();
        System.out.println("Report All Book Rented");

        List<Rental> rentals = rentalService.findActive();

        if(rentals.isEmpty()){
            System.out.println("No rental found");
            return;
        }

        rentals.forEach(rental -> {
            System.out.println(rental);
        });

        System.out.println("Book rented Total: " + rentals.size());

    }
    public void reportMemberWithBookRented(){
        System.out.println();
        System.out.println("Report Member with Book Rented");

        List<Member> members = memberService.findAll();
        List<Rental> activeRentals = rentalService.findActive();

        Map<Long, List<Rental>> memberRentalMap = activeRentals.stream()
                .collect(Collectors.groupingBy(
                        rental -> rental.getMember().getId(),
                        Collectors.toList()
                ));

        if(members.isEmpty()){
            System.out.println("No member found");
            return;
        }

        members.forEach(member -> {
            System.out.println(member);
            List<Rental> rentals = memberRentalMap.get(member.getId());
            if(rentals != null){
                rentals.forEach(rental -> {
                    System.out.println(rental);
                });
            }
        });

        System.out.println("Members Total: " + members.size());
        System.out.println("Rentals Total: " + activeRentals.size());
    }



}
