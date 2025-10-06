package com.enigma.bookrental.dao;

import com.enigma.bookrental.config.JPAConfig;
import com.enigma.bookrental.model.Rental;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class RentalDAO implements BaseDAO<Rental, Long>{
    private final EntityManager em = JPAConfig.getEm();


    @Override
    public void save(Rental rental) {
        var tx = em.getTransaction();
        try {
            tx.begin();
            if(rental.getId() == null){
                em.persist(rental);
            }else{
                em.merge(rental);
            }
            tx.commit();
        }catch (Exception e){
            if(tx != null && tx.isActive() ){
                tx.rollback();
            }
            throw new RuntimeException("Error menyimpan data peminjaman/pengembalian buku", e);
        }
    }

    @Override
    public List<Rental> findAll() {
        return em.createQuery("SELECT r FROM Rental r WHERE r.isDeleted = false OR r.isDeleted IS NULL", Rental.class).getResultList();
    }

    @Override
    public Optional<Rental> findById(Long id) {
        Rental rental = em.find(Rental.class, id);
        if(rental != null && Boolean.TRUE.equals(rental.getIsDeleted())){
            return Optional.empty();
        }
        return Optional.ofNullable(rental);
    }

    @Override
    public void delete(Long id) {
        var tx = em.getTransaction();
        try {
            tx.begin();
            var rental = em.find(Rental.class, id);
            if(rental != null){
                rental.setIsDeleted(true);
                em.merge(rental);
            }
            tx.commit();
        }catch (Exception e){
            if(tx != null && tx.isActive() ){
                tx.rollback();
            }
            throw new RuntimeException("Error menghapus data peminjaman/pengembalian buku", e);
        }
    }
}
