package edu.school21.printer.logic;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class ImageReader {
    private final char whiteChar;
    private final char blackChar;

    public ImageReader(char whiteChar, char blackChar) {
        this.whiteChar = whiteChar;
        this.blackChar = blackChar;
    }

    public void printImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                if (pixel == Color.WHITE.getRGB()) {
                    System.out.print(whiteChar);
                } else {
                    System.out.print(blackChar);
                }
            }
            System.out.println();
        }
    }
}
