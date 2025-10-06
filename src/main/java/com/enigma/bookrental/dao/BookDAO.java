package com.enigma.bookrental.dao;

import com.enigma.bookrental.config.JPAConfig;
import com.enigma.bookrental.model.Book;
import jakarta.persistence.EntityManager;


import java.util.List;
import java.util.Optional;

public class BookDAO implements BaseDAO<Book, Long> {
    private final EntityManager em = JPAConfig.getEm();

    @Override
    public void save(Book book) {
       var tx = em.getTransaction();
        try {
            tx.begin();
            if(book.getId() == null){
                em.persist(book);
            }else{
                em.merge(book);
            }
            tx.commit();
        }catch (Exception e){
            if(tx != null && tx.isActive() ){
                tx.rollback();
            }
            throw new RuntimeException("Error menyimpan buku", e);
        }
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b where b.isDeleted = false or b.isDeleted is null", Book.class).getResultList();
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = em.find(Book.class, id);
        if(book != null && Boolean.TRUE.equals(book.getIsDeleted())){
            System.out.println("Buku dengan id " + id + " sudah dihapus/tidak aktif.");
            return Optional.empty();
        }
        return Optional.ofNullable(book);
    }

    @Override
    public void delete(Long id) {
        var tx = em.getTransaction();
        try {
            tx.begin();
            var book = em.find(Book.class, id);
            if(book != null){
                book.setIsDeleted(true);
                em.merge(book);
            }
            tx.commit();
        }catch (Exception e){
            if(tx != null && tx.isActive() ){
                tx.rollback();
            }
            throw new RuntimeException("Error menghapus buku", e);
        }
    }

}
