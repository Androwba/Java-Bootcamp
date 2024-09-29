package ex01;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        scanner.close();

        if(number < 2) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }

        int iterationCount = 0;
        boolean isPrime = true;

        for(int i = 2; i * i <= number; i++) {
            iterationCount++;
            if(number % i == 0) {
                isPrime = false;
                break;
            }
        }

        if(isPrime) {
            System.out.println(isPrime + " " + (iterationCount + 1));
        } else {
            System.out.println(isPrime + " " + iterationCount);
        }
    }
}
