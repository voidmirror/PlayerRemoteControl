package com.voidmirror.playerremotecontrol.olympmsk16;

import java.util.Scanner;

public class Task1 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int k = scanner.nextInt();
        int p = scanner.nextInt();
        int s = scanner.nextInt();

        double expensive = k + k * p / 100.;
        expensive = Math.round(expensive * 100) / 100.;
        System.out.println(expensive);
        System.out.println(Math.floor(s / expensive));


    }

}
