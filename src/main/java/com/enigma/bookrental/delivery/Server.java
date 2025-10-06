package com.enigma.bookrental.delivery;

import com.enigma.bookrental.config.JPAConfig;
import com.enigma.bookrental.dao.MemberDAO;
import com.enigma.bookrental.delivery.controller.MemberController;
import com.enigma.bookrental.service.MemberService;

import java.util.Scanner;

public class Server {
    private final Scanner scanner = new Scanner(System.in);
    private final MemberService memberService;

    public Server(MemberService memberService){
        this.memberService = memberService;
    }

    public void start(){
        while(true){
            System.out.println("======== RENTAL BOOK SYSTEM ========");
            System.out.println("1. Member Menu");
            System.out.println("0. EXIT");
            System.out.print("Choose menu: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> new MemberController(memberService).showMenu();
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
        MemberService memberService = new MemberService(memberDAO);

        return new Server(memberService);
    }



}
