package edu.school21.printer.logic;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePrinter {
    private final Attribute whiteAttr;
    private final Attribute blackAttr;

    public ImagePrinter(Attribute whiteAttr, Attribute blackAttr) {
        this.whiteAttr = whiteAttr;
        this.blackAttr = blackAttr;
    }

    public void printImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                if (pixel == Color.WHITE.getRGB()) {
                    System.out.print(Ansi.colorize(" ", whiteAttr));
                } else {
                    System.out.print(Ansi.colorize(" ", blackAttr));
                }
            }
            System.out.println();
        }
    }
}
