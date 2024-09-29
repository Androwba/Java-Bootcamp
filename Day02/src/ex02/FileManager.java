import java.nio.file.StandardCopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {

    private Path currentDirectory;

    public FileManager(String directory) {
        this.currentDirectory = Paths.get(directory);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Starting file manager in: " + currentDirectory.toAbsolutePath());

        String command;
        while (true) {
            System.out.print("-> ");
            command = scanner.nextLine();
            if (command.equals("exit")) {
                System.out.println("Exiting...");
                break;
            }
            processCommand(command.split(" "));
        }
    }

    private void processCommand(String[] args) {
        try {
            switch (args[0]) {
                case "ls":
                    listDirectory();
                    break;
                case "cd":
                    changeDirectory(args[1]);
                    break;
                case "mv":
                    moveFile(args[1], args[2]);
                    break;
                default:
                    System.out.println("Unknown command.");
            }
        } catch (Exception e) {
            System.out.println("Error processing command: " + e.getMessage());
        }
    }

    private void listDirectory() throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentDirectory)) {
            for (Path file : stream) {
                System.out.println(file.getFileName() + " " + Files.size(file) / 1024 + " KB");
            }
        }
    }

    private void changeDirectory(String newDir) throws IOException {
        Path newPath = currentDirectory.resolve(newDir).normalize();;
        if (Files.exists(newPath) && Files.isDirectory(newPath)) {
            currentDirectory = newPath.toAbsolutePath().normalize();;
            System.out.println("Current directory: " + currentDirectory);
        } else {
            System.out.println("Directory does not exist.");
        }
    }

    private void moveFile(String sourceName, String destinationPath) throws IOException {
        Path source = currentDirectory.resolve(sourceName).normalize();
        Path destination = currentDirectory.resolve(destinationPath).normalize();
        if (Files.notExists(source)) {
            System.out.println("Source file does not exist.");
            return;
        }
        if (Files.isDirectory(destination)) {
            destination = destination.resolve(source.getFileName());
        }
        try {
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved/Renamed: " + sourceName + " to " + destination);
        } catch (IOException e) {
            System.out.println("Failed to move/rename: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--current-folder=")) {
            System.out.println("Usage: java FileManager --current-folder=<path>");
            return;
        }

        String folderPath = args[0].substring("--current-folder=".length());
        new FileManager(folderPath).start();
    }
}
