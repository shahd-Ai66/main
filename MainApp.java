/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mainapp;

/**
 *
 * @author AC
 */
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainApp {

    public static boolean isValidEmail(String email) {
        return Pattern.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+", email);
    }

    public static Polynomial readPolynomial(Scanner sc) {
        Polynomial p = new Polynomial();
        System.out.println("Enter the polynomial (example: 3X^2 + 2X + 1): ");
        String input = sc.nextLine().replaceAll("-", "+-").replaceAll("x", "X");// If the user enters an x instead of  X the code it will be modified.

        String[] parts = input.split("\\+");

        for (String part : parts) {
            part = part.trim();
            if (part.isEmpty()) continue;

            int coef = 0, expo = 0;

            if (part.contains("X")) {
                String[] split = part.split("X\\^?");
                coef = split[0].isEmpty() ? 1 : split[0].equals("-") ? -1 : Integer.parseInt(split[0]);
                expo = split.length > 1 ? Integer.parseInt(split[1]) : 1;
            } else {
                coef = Integer.parseInt(part);
                expo = 0;
            }

            p.addTerm(coef, expo);
        }

        return p;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String email;
        do {
            System.out.print("Enter your email : ");
            email = sc.nextLine();
        } while (!isValidEmail(email));
        System.out.println("Email verified.");

        Polynomial p1 = readPolynomial(sc);
        Polynomial p2 = readPolynomial(sc);

        System.out.println("Select the process: (+ - *): ");
        String op = sc.nextLine();

        Polynomial result = null;
        switch (op) {
            case "+":
                result = p1.add(p2);
                break;
            case "-":
                result = p1.subtract(p2);
                break;
            case "*":
                result = p1.multiply(p2);
                break;
            default:
                System.out.println("The operation is not supported..");
                return;
        }

        System.out.println("Choose how to display the result:");
        System.out.println("1. Infix (Default)");
        System.out.println("2. Postfix");
        System.out.println("3. Prefix");
        int viewChoice = sc.nextInt(); sc.nextLine();

        switch (viewChoice) {
            case 2:
                System.out.println(" Postfix: " + result.toPostfixString());
                break;
            case 3:
                System.out.println("Prefix: " + result.toPrefixString());
                break;
            default:
                System.out.println(" Infix: " + result);
        }

        System.out.print("to compensate enter a value X : ");
        int xVal = sc.nextInt();
        System.out.println(" الناتج عند X = " + xVal + " is : " + result.evaluate(xVal));
    }
}
