package com.voidmirror.playerremotecontrol.olympmsk16;

import java.util.Scanner;

public class Task2 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int x1, y1, x2, y2, x, y;
        x1 = scanner.nextInt();
        y1 = scanner.nextInt();
        x2 = scanner.nextInt();
        y2 = scanner.nextInt();
        x = scanner.nextInt();
        y = scanner.nextInt();

        if (x < x1 && y > y2) {
            System.out.println("NW");
        } else if (x > x1 && x < x2 && y > y2) {
            System.out.println("N");
        } else if (x > x2 && y > y2) {
            System.out.println("NE");
        } else if (x > x2 && y > y1 && y < y2) {
            System.out.println("E");
        } else if (x > x2 && y < y1) {
            System.out.println("SE");
        } else if (x > x1 && x < x2 && y < y1) {
            System.out.println("S");
        } else if (x < x1 && y < y1) {
            System.out.println("SW");
        } else if (x < x1 && y > y1 && y < y2) {
            System.out.println("W");
        }


    }

}
