package edu.school21.printer.logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImagePrinter {
    private final char whiteChar;
    private final char blackChar;

    public ImagePrinter(char whiteChar, char blackChar) {
        this.whiteChar = whiteChar;
        this.blackChar = blackChar;
    }

    public void readImage(String filePath) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            System.err.println("Error: Unable to read the image file. Please check the file path and try again.");
            return;
        }
        if (image == null) {
            System.err.println("Error: The file could not be read as an image. Ensure the file is a valid BMP image.");
            return;
        }
        printImageContent(image);
    }

    private void printImageContent(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        System.out.println("Image dimensions: " + width + "x" + height);

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
