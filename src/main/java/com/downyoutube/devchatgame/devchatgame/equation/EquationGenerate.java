package com.downyoutube.devchatgame.devchatgame.equation;

import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Random;

public class EquationGenerate {
    public static String generateEquation(int a) {
        Random random = new Random();
        int num1 = random.nextInt(100) + 1; // Random number between 1 and 10
        int num2 = random.nextInt(100) + 1; // Random number between 1 and 10
        int operator = random.nextInt(4);   // 0: +, 1: -, 2: *, 3: /
        if (operator == 3 || operator == 2) {
            num2 = random.nextInt(10) + 1;
        }

        int result;
        char operatorChar;

        switch (operator) {
            case 0 -> {
                operatorChar = '+';
            }
            case 1 -> {
                operatorChar = '-';
            }
            case 2 -> {
                operatorChar = '*';
            }
            case 3 -> {
                result = num1 / num2;
                if (num1 % num2 > 0) num1 = num2 * result;
                operatorChar = '/';
            }
            default -> throw new IllegalStateException("Invalid operator");
        }

        if (a > 1) {
            String equation;
            double equation_result;
            do {
                equation = generateEquation(a-1) + " " + operatorChar + " " + num2;
                equation_result = new ExpressionBuilder(equation).build().evaluate();
            }
            while (equation_result != (int) equation_result);

            return equation;
        } else {
            return num1 + " " + operatorChar + " " + num2;
        }
    }

    public static int getAnswer(String equation) {
        return (int) new ExpressionBuilder(equation).build().evaluate();
    }
}
