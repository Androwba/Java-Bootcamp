import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileSignatureAnalyzer {
    private static final String SIGNATURES_FILE_PATH = "Java_Bootcamp.Day02-1/src/ex00/signatures.txt";
    private static final String RESULT_FILE_PATH = "Java_Bootcamp.Day02-1/src/ex00/result.txt";
    private static final int SIGNATURE_LENGTH = 12;
    private static final Map<String, String> signatures = new HashMap<>();

    public static void main(String[] args) {
        loadSignatures();
        processFiles();
    }

    private static void loadSignatures() {
        try (Scanner scanner = new Scanner(new FileInputStream(SIGNATURES_FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(", ");
                if (parts.length == 2) {
                    signatures.put(parts[1].trim(), parts[0].trim());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Signatures file not found at " + SIGNATURES_FILE_PATH);
            System.exit(1);
        }
    }

    private static void processFiles() {
        File resultFile = new File(RESULT_FILE_PATH);
        try (Scanner scanner = new Scanner(System.in);
             PrintWriter out = new PrintWriter(new FileOutputStream(resultFile, false))) {
            System.out.println("Enter file paths to analyze, or type '42' to exit:");
            while (scanner.hasNextLine()) {
                String filePath = scanner.nextLine().trim();
                if ("42".equals(filePath)) {
                    break;
                }
                String fileType = getFileType(filePath);
                if (fileType != null) {
                    out.println(fileType);
                    out.flush();
                    System.out.println("PROCESSED");
                } else {
                    System.out.println("UNDEFINED");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Could not access or create " + RESULT_FILE_PATH);
        }
    }

    private static String getFileType(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("Error: File does not exist - " + filePath);
            return null;
        }
        try (InputStream is = new FileInputStream(file)) {
            String fileSignature = getFileSignature(is);
            for (Map.Entry<String, String> entry : signatures.entrySet()) {
                if (fileSignature.startsWith(entry.getKey())) {
                    return entry.getValue();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
        }
        return null;
    }

    private static String getFileSignature(InputStream is) throws IOException {
        byte[] bytes = new byte[SIGNATURE_LENGTH];
        int length = is.read(bytes);
        StringBuilder signatureBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            signatureBuilder.append(String.format("%02X ", bytes[i]));
        }
        return signatureBuilder.toString().trim();
    }
}
