package Challenge2_EvaluateArithmeticExpressions;

import java.io.*;
import java.util.*;

public class EvaluateArithmeticExpressions {

    // This method returns priority of operators
    // Higher value means higher priority
    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-': return 1;
            case '*':
            case '/': return 2;
            case '^': return 3;
        }
        return -1;
    }
    // perform calculation
    private static double apply(double a, double b, char op) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
            case '^': return Math.pow(a, b);
        }
        return 0;
    }

    // remove spaces and handle cases
    private static String normalize(String expr) {
        expr = expr.replaceAll("\\s+", "");
        expr = expr.replaceAll("\\)\\(", ")*(");
        return expr;
    }

    // evaluate the arithmetic expression
    private static double evaluate(String expr) {
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);

            // number or unary minus
            if (Character.isDigit(ch) || ch == '.' ||
                    (ch == '-' && (i == 0 || "+-*/(^".indexOf(expr.charAt(i - 1)) >= 0))) {

                StringBuilder sb = new StringBuilder();
                sb.append(ch);
                i++;

                while (i < expr.length() &&
                        (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    sb.append(expr.charAt(i++));
                }
                i--;
                values.push(Double.parseDouble(sb.toString()));
            }

            else if (ch == '(') {
                ops.push(ch);
            }

            else if (ch == ')') {
                while (!ops.isEmpty() && ops.peek() != '(') {
                    double b = values.pop();
                    double a = values.pop();
                    values.push(apply(a, b, ops.pop()));
                }
                ops.pop();
            }

            else {
                while (!ops.isEmpty() &&
                        (precedence(ops.peek()) > precedence(ch) ||
                                (precedence(ops.peek()) == precedence(ch) && ch != '^'))) {
                    double b = values.pop();
                    double a = values.pop();
                    values.push(apply(a, b, ops.pop()));
                }
                ops.push(ch);
            }
        }

        while (!ops.isEmpty()) {
            double b = values.pop();
            double a = values.pop();
            values.push(apply(a, b, ops.pop()));
        }

        return values.pop();
    }

    // main method
    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader("Gauri_Soni/input1.txt"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("Gauri_Soni/output.txt"));

        String line;
        while ((line = reader.readLine()) != null) {
            String expr = normalize(line.replace("=", ""));
            double result = evaluate(expr);
            if (result == Math.floor(result)) {
                writer.write(expr + " = " + (long) result);
            } else {
                writer.write(expr + " = " + result);
            }

            writer.newLine();
        }

        reader.close();
        writer.close();
        System.out.println("Results written to output.txt");
    }
}
