package com.voidmirror.playerremotecontrol.olympmsk16;

import java.util.Scanner;

public class Task3 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int s = scanner.nextInt();
        int n = scanner.nextInt();

        int rack = 0;
        int infin = 0;

        for (int i = 0; i < n; i++) {
            int current = scanner.nextInt();
            if (rack + current <= s) {
                rack += current;
            } else {
                infin += current;
            }
        }

        System.out.println(rack);
        System.out.println(infin);


    }

}
