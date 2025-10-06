package com.enigma.bookrental;

import com.enigma.bookrental.config.JPAConfig;
import com.enigma.bookrental.delivery.Server;
import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 */
@Slf4j
public class App {
    public static void main(String[] args) {

        // command clean compile maven For this project :
        // mvn clean compile --% exec:java -Dexec.mainClass=com.enigma.bookrental.App


        log.info("Starting application");

        System.out.println();
        Server server = Server.serve();
        server.start();

        log.info("Application stopped");

    }
}
