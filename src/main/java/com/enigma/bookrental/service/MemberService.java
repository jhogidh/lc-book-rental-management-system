package com.enigma.bookrental.service;

import com.enigma.bookrental.dao.MemberDAO;
import com.enigma.bookrental.model.Member;

import java.util.List;

public class MemberService {
    private final MemberDAO memberDAO;

    public MemberService(MemberDAO memberDAO){
        this.memberDAO = memberDAO;
    }

    public boolean isEmailExist(String email){
        return memberDAO.findAll().stream().anyMatch(m -> m.getEmail().equals(email));
    }

    public Member findById(Long id){
        return memberDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Member dengan id " + id + " tidak ditemukan"));
    }

    public void createMember(Member member){
        if(isEmailExist(member.getEmail())){
            throw new IllegalArgumentException("Email"+ member.getEmail() +" sudah digunakan");
        }
        memberDAO.save(member);
    }

    public void updateMember(Member member){
        if(member.getId() == null){
            throw new IllegalArgumentException("Id tidak boleh kosong");
        }
        findById(member.getId());
        memberDAO.save(member);
    }

    public List<Member> findAll(){
        return memberDAO.findAll();
    }

    public void deleteMember(Long id){
        findById(id);
        memberDAO.delete(id);
    }





}
