package com.enigma.bookrental.delivery.controller;

import com.enigma.bookrental.service.RentalService;

import java.util.Scanner;

public class RentalController {
    private final RentalService rentalService;
    private final Scanner scanner = new Scanner(System.in);

    public RentalController(RentalService rentalService){
        this.rentalService = rentalService;
    }

    public void showMenu(){
        System.out.println();
        System.out.println("Welcome to Rental Menu");
        System.out.println("1. Borrow Book");
        System.out.println("2. Return Book");
        System.out.println("3. Show All Rentals");
        System.out.println("4. Show Active Rentals");
        System.out.println("5. Show Rentals By Member");
        System.out.println("0. Exit");
        System.out.println();
        System.out.print("Please enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice){
            case 1:
                borrowHandler();
                break;
            case 2:
                returnHandler();
                break;
            case 3:
                showAllHandler();
                break;
            case 4:
                showActiveHandler();
                break;
            case 5:
                showByMemberHandler();
                break;
            case 0:
                System.out.println("Thank you for using our service");
            return;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    public void borrowHandler(){
        System.out.println();
        System.out.println("Borrow Book");
        System.out.println("Please enter member id: ");
        Long memberId = Long.parseLong(scanner.nextLine());
        System.out.println("Please enter book id: ");
        Long bookId = Long.parseLong(scanner.nextLine());

        rentalService.borrowBook(memberId, bookId);
        System.out.println("Book borrowed");
    }

    public void returnHandler(){
        System.out.println();
        System.out.println("Return Book");
        System.out.println("Please enter rental id: ");
        Long rentalId = Long.parseLong(scanner.nextLine());

        rentalService.returnBook(rentalId);
        System.out.println("Book returned");
    }

    public void showAllHandler(){
        System.out.println();
        System.out.println("Show All Rentals");
        rentalService.findAll().forEach(System.out::println);
    }

    public void showActiveHandler(){
        System.out.println();
        System.out.println("Show Active Rentals");
        rentalService.findActive().forEach(System.out::println);
    }

    public void showByMemberHandler(){
        System.out.println();
        System.out.println("Show Rentals By Member");
        System.out.println("Please enter member id: ");
        Long memberId = Long.parseLong(scanner.nextLine());
        rentalService.findByMember(memberId).forEach(System.out::println);
    }


}
