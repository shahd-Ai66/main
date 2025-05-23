import java.util.Scanner;

public class MainApp {

    
    public static boolean isValidEmail(String email) {
    return email.contains("@") &&
           email.contains(".") &&
           email.indexOf("@") < email.lastIndexOf(".");
}


    public static Polynomial readPolynomial(Scanner sc) {
        Polynomial p = new Polynomial();
        System.out.println("Enter the polynomial (example: 3X^2 + 2X + 1): ");
        String input = sc.nextLine().replace("-", "+-").replace("x", "X");

        String[] parts = input.split("\\+");

        for (String part : parts) {
            part = part.trim();
            if (part.isEmpty()) continue;

            int coef = 0, expo = 0;

            if (part.contains("X")) {
                int xIndex = part.indexOf("X");
                String coefStr = part.substring(0, xIndex);
                String expoStr = part.contains("^") ? part.substring(xIndex + 2) : "1";

                coef = coefStr.isEmpty() ? 1 : coefStr.equals("-") ? -1 : Integer.parseInt(coefStr);
                expo = Integer.parseInt(expoStr);
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
            System.out.print("Enter your email: ");
            email = sc.nextLine();
        } while (!isValidEmail(email));
        System.out.println("Email verified.");

        Polynomial p1 = readPolynomial(sc);
        Polynomial p2 = readPolynomial(sc);

        System.out.println("Select the operation: (+ - * /): ");
        String op = sc.nextLine();

        Polynomial result = null;

        try {
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
                case "/":
                    Polynomial[] divisionResult = p1.divide(p2);
                    System.out.println(" Quotient: " + divisionResult[0]);
                    System.out.println(" Remainder: " + divisionResult[1]);
                    result = divisionResult[0];
                    break;
                default:
                    System.out.println(" The operation is not supported.");
                    return;
            }
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
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
                System.out.println(" Prefix: " + result.toPrefixString());
                break;
            default:
                System.out.println(" Infix: " + result);
        }

        System.out.print("Enter a value for X to evaluate: ");
        int xVal = sc.nextInt();
        System.out.println(" Result at X = " + xVal + " is: " + result.evaluate(xVal));
    }
}

