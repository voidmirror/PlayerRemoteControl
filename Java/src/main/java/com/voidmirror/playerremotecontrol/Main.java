package com.voidmirror.playerremotecontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.ArrayList;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
//        PlayerController playerController = new PlayerController();
//        Server server = new Server(playerController);
//        SpringApplication.run(Main.class, args);

//        PraxisGenerator generator = new PraxisGenerator();
//        generator.setVarNum(7);
//        ArrayList<String> simple = new ArrayList<>();
//        ArrayList<String> medium = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            simple.add(generator.generateSimpleMedium(false));
//            medium.add(generator.generateSimpleMedium(true));
//        }
//        for (String s : simple) {
//            System.out.println(s);
//        }
//        System.out.println();
//        System.out.println();
//        for (String s : medium) {
//            System.out.println(s);
//        }
//
//        System.out.println();
//
//        for (String s : simple) {
//            System.out.println(generator.insertBrackets(s));
//            System.out.println();
//        }

        SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
        builder.headless(false).run(args);
    }
}
