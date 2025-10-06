package com.enigma.bookrental.service;

import com.enigma.bookrental.dao.BookDAO;
import com.enigma.bookrental.dao.MemberDAO;
import com.enigma.bookrental.dao.RentalDAO;
import com.enigma.bookrental.model.Book;
import com.enigma.bookrental.model.Member;
import com.enigma.bookrental.model.Rental;

import java.sql.Timestamp;
import java.util.List;

public class RentalService {
    private final RentalDAO rentalDAO;
    private final MemberService memberService;
    private final BookService bookService;

    public RentalService(RentalDAO rentalDAO, MemberService memberService, BookService bookService){
        this.rentalDAO = rentalDAO;
        this.memberService = memberService;
        this.bookService = bookService;
    }

    public void borrowBook(Long memberId, Long bookId){
        if(bookId == null || memberId == null){
            throw new IllegalArgumentException("book and member id can't be null");
        }
        Member member = memberService.findById(memberId);
        Book book = bookService.findById(bookId);

        if(book.getStock() == 0){
            throw new IllegalArgumentException("Book stock is empty");
        }
        book.setStock(book.getStock()-1);
        bookService.updateBook(book);
        Rental rental = Rental.builder()
                .book(book)
                .member(member)
                .rentalDate(new Timestamp(System.currentTimeMillis()))
                .build();
        rentalDAO.save(rental);
        System.out.println("Rental created, Book stock available: " + book.getStock());
    }

    public void returnBook(Long rentalId){
        if(rentalId == null){
            throw new IllegalArgumentException("Id rental can't be null");
        }
        Rental rental = rentalDAO.findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Rental dengan id " + rentalId + " tidak ditemukan"));

        if(rental.getReturnDate() != null){
            throw new IllegalArgumentException("Book has already been returned");
        }
        rental.setReturnDate(new Timestamp(System.currentTimeMillis()));
        rentalDAO.save(rental);
        System.out.println("Book returned");

        Book book = rental.getBook();
        book.setStock(book.getStock()+1);
        bookService.updateBook(book);
        System.out.println("Book stock restored: " + book.getStock());
    }

    public List<Rental> findAll(){
        return rentalDAO.findAll();
    }

    public Rental findById(Long id){
        return rentalDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Book returnment/borrowment with id " + id + " not found"));
    }

    public void deleteRental(Long id){
        findById(id);
        rentalDAO.delete(id);
    }

    public List<Rental> findActive(){
        return rentalDAO.findAll().stream().filter(r -> r.getReturnDate() == null).toList();
    }

    public List<Rental> findByMember(Long memberId){
        return rentalDAO.findAll().stream().filter(r -> r.getMember().getId().equals(memberId)).toList();
    }


}
