package ex02;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int countPrimeSum = 0;

        while (true) {
            int query = input.nextInt();
            if (query == 42) {
                break;
            }

            if (isPrime(sumOfDigits(query))) {
                countPrimeSum++;
            }
        }
        System.out.println("Count of coffee-request – " + countPrimeSum);
        input.close();
    }

    public static int sumOfDigits(int number) {
        int sum = 0;
        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }

    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
