package ex04;

import java.util.Scanner;

public class Program {
    private static final int MAX_CHAR_VALUE = 65536;

    public static void main(String[] args) {
        String input = readInput();
        char[] characters = input.toCharArray();
        int[] frequencies = new int[MAX_CHAR_VALUE];
        countCharacterFrequencies(characters, frequencies);

        char[] topChars = new char[10];
        int[] topFrequencies = new int[10];
        findTopCharacters(frequencies, topChars, topFrequencies);

        double scale = calculateScalingFactor(topFrequencies);
        int[] scaledFrequencies = scaleFrequencies(topFrequencies, scale);

        printHistogram(scaledFrequencies, topFrequencies);
        printTopCharacters(topChars, topFrequencies);
    }

    private static String readInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private static void countCharacterFrequencies(char[] characters, int[] frequencies) {
        for (char c : characters) {
            frequencies[c]++;
        }
    }

    private static void findTopCharacters(int[] frequencies, char[] topChars, int[] topFrequencies) {
        for (int i = 0; i < frequencies.length; i++) {
            for (int j = 0; j < topFrequencies.length; j++) {
                if (frequencies[i] > topFrequencies[j]) {
                    for (int k = topFrequencies.length - 1; k > j; k--) {
                        topFrequencies[k] = topFrequencies[k - 1];
                        topChars[k] = topChars[k - 1];
                    }
                    topFrequencies[j] = frequencies[i];
                    topChars[j] = (char) i;
                    break;
                }
            }
        }
    }

    private static double calculateScalingFactor(int[] topFrequencies) {
        int maxFrequency = topFrequencies[0];
        return maxFrequency > 10 ? 10.0 / maxFrequency : 1;
    }

    private static int[] scaleFrequencies(int[] topFrequencies, double scale) {
        int[] scaledFrequencies = new int[topFrequencies.length];
        for (int i = 0; i < topFrequencies.length; i++) {
            scaledFrequencies[i] = (int) (topFrequencies[i] * scale);
        }
        return scaledFrequencies;
    }

    private static void printHistogram(int[] scaledFrequencies, int[] topFrequencies) {
        int maxHeight = 0;
        for (int frequency : scaledFrequencies) {
            if (frequency > maxHeight) {
                maxHeight = frequency;
            }
        }

        maxHeight += 1;

        for (int height = maxHeight; height >= 1; height--) {
            for (int i = 0; i < scaledFrequencies.length; i++) {
                if (topFrequencies[i] == 0) {
                    continue;
                }
                if (scaledFrequencies[i] + 1 == height) {
                    System.out.printf("%2d  ", topFrequencies[i]);
                } else if (scaledFrequencies[i] >= height) {
                    System.out.print(" #  ");
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println();
        }
    }

    private static void printTopCharacters(char[] topChars, int[] topFrequencies) {
        for (int i = 0; i < topChars.length; i++) {
            if (topFrequencies[i] > 0) {
                System.out.printf(" %c  ", topChars[i]);
            }
        }
        System.out.println();
    }
}
