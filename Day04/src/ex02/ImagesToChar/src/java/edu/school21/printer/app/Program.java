package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.diogonunes.jcolor.Attribute;
import edu.school21.printer.logic.ImagePrinter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

@Parameters(separators = "=")
public class Program {

    @Parameter(names = "--white", description = "Color for white pixels")
    private String whiteColor = "WHITE";

    @Parameter(names = "--black", description = "Color for black pixels")
    private String blackColor = "BLACK";

    public static void main(String[] args) {
        Program program = new Program();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(program)
                .build();
        try {
            jCommander.parse(args);
            program.run();
        } catch (ParameterException e) {
            System.err.println(e.getMessage());
            jCommander.usage();
        }
    }

    public void run() {
        Attribute whiteAttr = getAttribute(whiteColor);
        Attribute blackAttr = getAttribute(blackColor);

        try (InputStream imageStream = Program.class.getResourceAsStream("/it.bmp")) {
            if (imageStream == null) {
                System.err.println("Image file not found in resources.");
                return;
            }

            BufferedImage image = ImageIO.read(imageStream);
            ImagePrinter printer = new ImagePrinter(whiteAttr, blackAttr);
            printer.printImage(image);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the image.");
            e.printStackTrace();
        }
    }

    private Attribute getAttribute(String color) {
        switch (color.toUpperCase()) {
            case "BLACK":
                return Attribute.BLACK_BACK();
            case "RED":
                return Attribute.RED_BACK();
            case "GREEN":
                return Attribute.GREEN_BACK();
            case "YELLOW":
                return Attribute.YELLOW_BACK();
            case "BLUE":
                return Attribute.BLUE_BACK();
            case "MAGENTA":
                return Attribute.MAGENTA_BACK();
            case "CYAN":
                return Attribute.CYAN_BACK();
            case "WHITE":
                return Attribute.WHITE_BACK();
            default:
                return Attribute.NONE();
        }
    }
}
