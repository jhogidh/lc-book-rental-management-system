package com.enigma.bookrental.delivery.controller;

import com.enigma.bookrental.model.Member;
import com.enigma.bookrental.service.MemberService;

import java.util.Scanner;

public class MemberController {
    private final MemberService memberService;
    private final Scanner scanner = new Scanner(System.in);

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    public void showMenu(){
        System.out.println();
        System.out.println("Welcome to Member Menu");
        System.out.println("1. Create Member");
        System.out.println("2. Update Member");
        System.out.println("3. Delete Member");
        System.out.println("4. Find Member By Id");
        System.out.println("5. Find All Members");
        System.out.println("0. Go back");
        System.out.println();
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
                deleteHandler();
                break;
            case 4:
                findByIdHandler();
                break;
            case 5:
                findAllHandler();
                break;
            case 6:
                System.out.println("Go back to main menu");
                return;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    public void createHandler(){
        System.out.println();
        System.out.println("Create Member");
        System.out.println("Please enter email: ");
        String email = scanner.nextLine();
        System.out.println("Please enter name: ");
        String name = scanner.nextLine();
        Member member = Member.builder().email(email).name(name).build();
        memberService.createMember(member);
        System.out.println("Member created");
    }

    public void updateHandler(){
        System.out.println();
        System.out.println("Update Member");
        System.out.println("Please enter id: ");
        Long id = Long.parseLong(scanner.nextLine());
        Member member = memberService.findById(id);
        System.out.println("Please enter email (enter to keep old value): ");
        String email = scanner.nextLine();
        if (email.isBlank()){
            email = member.getEmail();
        }
        System.out.println("Please enter name (enter to keep old value): ");
        String name = scanner.nextLine();
        member.setEmail(email);
        member.setName(name);
        memberService.updateMember(member);
        System.out.println("Member updated");
    }

    public void deleteHandler(){
        System.out.println();
        System.out.println("Delete Member");
        System.out.println("Please enter id: ");
        Long id = Long.parseLong(scanner.nextLine());
        memberService.deleteMember(id);
        System.out.println("Member deleted");
    }

    public void findByIdHandler(){
        System.out.println();
        System.out.println("Find Member By Id");
        System.out.println("Please enter id: ");
        Long id = Long.parseLong(scanner.nextLine());
        Member member = memberService.findById(id);
        System.out.println(member);
    }

    public void findAllHandler(){
        System.out.println();
        System.out.println("Find All Members");
        memberService.findAll().forEach(System.out::println);
    }


}
