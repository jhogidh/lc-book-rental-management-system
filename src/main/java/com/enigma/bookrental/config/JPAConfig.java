package com.enigma.bookrental.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JPAConfig {
    private static final String PERSISTENCE_UNIT_NAME = "bookrental-pu";
    private static EntityManagerFactory emf;

    private JPAConfig(){}

    private static EntityManagerFactory getEmf(){
        try{
            if(emf == null){
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
                log.info("EntityManagerFactory created");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return emf;
    }

    public static EntityManager getEm(){
        return getEmf().createEntityManager();
    }

    public static void closeEmf(){
        if(emf != null){
            emf.close();
            log.info("EntityManagerFactory closed");
        }
    }

}
