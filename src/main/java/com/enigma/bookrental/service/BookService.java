package com.enigma.bookrental.service;

import com.enigma.bookrental.dao.BookDAO;
import com.enigma.bookrental.model.Book;

public class BookService {
    private final BookDAO bookDAO;

    public BookService(BookDAO bookDAO){
        this.bookDAO = bookDAO;
    }

    public boolean isTitleExist(String title){
        return bookDAO.findAll().stream().anyMatch(b -> b.getTitle().equals(title));
    }

    public void addBook(Book book){
        if(isTitleExist(book.getTitle())){
            throw new IllegalArgumentException("Title"+ book.getTitle() +" sudah digunakan");
        }
        bookDAO.save(book);
    }

    public void updateBook(Book book){
        if(book.getId() == null){
            throw new IllegalArgumentException("Id tidak boleh kosong");
        }
        findById(book.getId());
        bookDAO.save(book);
    }

    public void deleteBook(Long id){
        bookDAO.delete(id);
    }

    public Book findById(Long id){
        return bookDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Buku dengan id " + id + " tidak ditemukan"));
    }

    public Iterable<Book> findAll(){
        return bookDAO.findAll();
    }



}
