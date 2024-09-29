package ex03;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long container = 0;
        int weekNum = 1;

        for (; weekNum <= 18; weekNum++) {
            String weekStr = scanner.nextLine();
            if (weekStr.equals("42")) break;
            if (!weekStr.equals("Week " + weekNum)) {
                System.err.println("Illegal Argument: Week number out of order or invalid.");
                System.exit(-1);
            }

            long minGrade = getMin(scanner);
            container = updateContainer(container, minGrade, weekNum);
        }

        printInfo(container, weekNum - 1);
    }

    private static void printInfo(long container, int weeks) {
        for (int weekNum = 1; weekNum <= weeks; weekNum++) {
            System.out.print("Week " + weekNum + " ");

            long divisor = pow10(weekNum - 1);
            long grade = (container / divisor) % 10;

            for (int i = 0; i < grade; i++) {
                System.out.print("=");
            }
            System.out.println(">");
        }
    }

    private static long getMin(Scanner scanner) {
        long min = 10;
        for (int i = 0; i < 5; i++) {
            long grade = scanner.nextLong();
            if (grade < 1 || grade > 9) {
                System.err.println("Invalid grade. Each grade should be between 1 and 9.");
                System.exit(-1);
            }
            if (grade < min) {
                min = grade;
            }
        }
        scanner.nextLine();
        return min;
    }

    private static long pow10(int exponent) {
        long result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= 10;
        }
        return result;
    }

    private static long updateContainer(long container, long minGrade, int weekNum) {
        return container + (minGrade * pow10(weekNum - 1));
    }
}
