package com.voidmirror.playerremotecontrol;

import java.util.Random;

public class PraxisGenerator {

    private int varNum = 3;
    private int varBoundary = 50;

    public int getVarNum() {
        return varNum;
    }

    public void setVarNum(int varNum) {
        this.varNum = varNum;
    }

    public int getVarBoundary() {
        return varBoundary;
    }

    public void setVarBoundary(int varBoundary) {
        this.varBoundary = varBoundary;
    }

    public String generateSign(boolean addMultiply) {
        Random random = new Random();
        switch (random.nextInt(addMultiply ? 4 : 2)) {
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "*";
            case 3:
                return "/";
            default:
                return "NaN";
        }
    }

    // [addMultiply == false] Includes only + and - | [addMultiply == true] Includes * and /
    public String generateSimpleMedium(boolean addMultiply) {

        StringBuilder praxis = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < varNum - 1; i++) {
            praxis.append(random.nextInt(varBoundary));
            praxis.append(generateSign(addMultiply));
        }
        praxis.append(random.nextInt(varBoundary));

        return praxis.toString();
    }

    // Includes *, / and Brackets
    public String insertBrackets(String praxis) {

        StringBuilder builder = new StringBuilder(praxis);

        Random random = new Random();
        int leftBracketIndex = random.nextInt(praxis.length() - 3);
        System.out.println("Random index left: " + leftBracketIndex);
        if (leftBracketIndex != 0) {
            if (builder.charAt(leftBracketIndex) <= '9' && builder.charAt(leftBracketIndex) >= '0') {
                while (leftBracketIndex != 0 && builder.charAt(leftBracketIndex - 1) <= '9' && builder.charAt(leftBracketIndex - 1) >= '0') {
                    leftBracketIndex--;
                }
            } else {
                while (!(builder.charAt(leftBracketIndex) <= '9' && builder.charAt(leftBracketIndex) >= '0')) {
                    leftBracketIndex++;
                }
            }
        }
//        System.out.println(leftBracketIndex);

        int rightBracketIndex = leftBracketIndex + 1;
        System.out.println("First right: " + rightBracketIndex);
        while (builder.charAt(leftBracketIndex) <= '9' && builder.charAt(leftBracketIndex) >= '0') {
            rightBracketIndex++;
        }
        while (!(builder.charAt(leftBracketIndex) <= '9' && builder.charAt(leftBracketIndex) >= '0')) {
            rightBracketIndex++;
        }
        while (builder.charAt(leftBracketIndex) <= '9' && builder.charAt(leftBracketIndex) >= '0') {
            rightBracketIndex++;
        }
//        while (!(builder.charAt(leftBracketIndex) <= '9' && builder.charAt(leftBracketIndex) >= '0')) {
//            rightBracketIndex++;
//        }

        int shift = rightBracketIndex - leftBracketIndex;
        rightBracketIndex = random.nextInt(praxis.length() - rightBracketIndex);
        rightBracketIndex = rightBracketIndex + shift;
        System.out.println("Right + shift: " + rightBracketIndex);
        if (rightBracketIndex < praxis.length() - 1) {
            while (builder.charAt(leftBracketIndex) <= '9' && builder.charAt(leftBracketIndex) >= '0') {
                rightBracketIndex++;
            }
        }
//
        builder.insert(leftBracketIndex, '(');
        builder.insert(rightBracketIndex, ')');


        return builder.toString();
    }

}
