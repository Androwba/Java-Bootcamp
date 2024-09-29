package edu.school21.printer.app;

import edu.school21.printer.logic.ImageReader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Program {
    public static void main(String[] args) {

        char whiteChar = '.';
        char blackChar = '0';

        try (InputStream imageStream = Program.class.getResourceAsStream("/it.bmp")) {
            if (imageStream == null) {
                System.err.println("Image file not found in resources.");
                return;
            }

            BufferedImage image = ImageIO.read(imageStream);
            ImageReader printer = new ImageReader(whiteChar, blackChar);
            printer.printImage(image);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the image.");
        }
    }
}
