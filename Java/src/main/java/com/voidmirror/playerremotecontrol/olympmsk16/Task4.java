package com.voidmirror.playerremotecontrol.olympmsk16;

import java.util.Scanner;

public class Task4 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int up = 0, plato = 0, down = -1;
        int upInd, platoInd;

        int upIndexStorage = 0, downIndexStorage = (int) (Math.pow(10, 5) + 1);


        if (n < 3) {
            System.out.println(0);
        } else {
            up = scanner.nextInt();
            upInd = 0;
            plato = scanner.nextInt();
            platoInd = 1;
            System.out.println("Up: " + up + " " + upInd + " Plato: " + plato + " " + platoInd);
            for (int i = 2; i < n; i++) {
                int current = scanner.nextInt();
                if (current > plato) {
                    up = plato;
                    upInd = platoInd;
                    plato = current;
                    platoInd = i;
                    System.out.println("IF ## Up: " + up + " " + upInd + " Plato: " + plato + " " + platoInd);
                } else if (current == plato) {
                    platoInd = i;
                }
                else if (current < plato) {
                    if (plato > up) {
                        if (i - upInd < downIndexStorage - upIndexStorage) {
                            upIndexStorage = upInd;
                            downIndexStorage = i;
                            System.out.println("Storage: " + upIndexStorage + " " + downIndexStorage);
                        }
                    } else {
                        up = plato;
                        upInd = platoInd;
                        plato = current;
                        platoInd = i;
                        System.out.println("ELSE ## Up: " + up + " " + upInd + " Plato: " + plato + " " + platoInd);
                    }
                }
            }

            if (downIndexStorage - upIndexStorage < (int) (Math.pow(10, 5) + 1)) {
                System.out.println(upIndexStorage + 1);
                System.out.println(downIndexStorage + 1);
            } else {
                System.out.println(0);
            }
        }




    }

}
