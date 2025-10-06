package com.enigma.bookrental;

import com.enigma.bookrental.config.JPAConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 */
@Slf4j
public class App {
    public static void main(String[] args) {

        // command clean compile maven For this project :
        // mvn clean compile --% exec:java -Dexec.mainClass=com.enigma.bookrental.App


        try(var db = JPAConfig.getEm();){
            log.info("Test Connection success");
        }catch (Exception e){
            log.error("Connection failed" + e.getMessage());
        }

    }
}
