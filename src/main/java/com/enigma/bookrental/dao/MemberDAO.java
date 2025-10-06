package com.enigma.bookrental.dao;

import com.enigma.bookrental.config.JPAConfig;
import com.enigma.bookrental.model.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class MemberDAO implements BaseDAO<Member, Long>{
    private final EntityManager em = JPAConfig.getEm();

    @Override
    public void save(Member member) {
        var tx = em.getTransaction();
        try {
            tx.begin();
            if(member.getId() == null){
                em.persist(member);
            }else{
                em.merge(member);
            }
            tx.commit();
        }catch (Exception e){
            tx.rollback();
            throw new RuntimeException("Error menyimpan member", e);
        }
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m where m.isDeleted = false or m.isDeleted is null", Member.class).getResultList();
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        if(member != null && Boolean.TRUE.equals(member.getIsDeleted())){
            System.out.println("Member dengan id " + id + " sudah dihapus/tidak aktif.");
            return null;
        }
        return Optional.ofNullable(member);
    }

    @Override
    public void delete(Long id) {
        var tx = em.getTransaction();
        try {
            tx.begin();
            var member = em.find(Member.class, id);
            if(member != null){
                member.setIsDeleted(true);
                em.merge(member);
            }
            tx.commit();
        }catch (Exception e){
            tx.rollback();
            throw new RuntimeException("Error menghapus member", e);
        }

    }
}
