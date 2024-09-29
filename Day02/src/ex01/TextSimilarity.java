import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TextSimilarity {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java TextSimilarity <file1> <file2>");
            System.exit(-1);
        }
        String file1Path = args[0];
        String file2Path = args[1];
        try {
            Map<String, Integer> wordDict = buildDictionary(file1Path, file2Path);
            List<Integer> vectorA = buildFrequencyVector(wordDict, file1Path);
            List<Integer> vectorB = buildFrequencyVector(wordDict, file2Path);

            double similarity = calculateCosineSimilarity(vectorA, vectorB);
            System.out.printf("Similarity = %.2f\n", similarity);
            saveDictionary(wordDict);
        } catch (IOException e) {
            System.out.println("Error processing files: " + e.getMessage());
        }
    }

    private static Map<String, Integer> buildDictionary(String... filePaths) throws IOException {
        Map<String, Integer> dictionary = new TreeMap<>();
        for (String path : filePaths) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] words = line.toLowerCase().split("[^\\p{L}\\p{Nd}]+");
                    for (String word : words) {
                        if (!word.isEmpty()) {
                            dictionary.putIfAbsent(word, 0);
                        }
                    }
                }
            }
        }
        return dictionary;
    }

    private static List<Integer> buildFrequencyVector(Map<String, Integer> dictionary, String filePath) throws IOException {
        Map<String, Integer> wordCount = new HashMap<>(dictionary);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().split("[^\\p{L}\\p{Nd}]+");
                for (String word : words) {
                    if (wordCount.containsKey(word)) {
                        wordCount.put(word, wordCount.get(word) + 1);
                    }
                }
            }
        }
        List<Integer> frequencyVector = new ArrayList<>();
        for (String key : dictionary.keySet()) {
            frequencyVector.add(wordCount.get(key));
        }
        return frequencyVector;
    }

    private static double calculateCosineSimilarity(List<Integer> vectorA, List<Integer> vectorB) {
        int dotProduct = 0;
        double normA = 0.0, normB = 0.0;
        for (int i = 0; i < vectorA.size(); i++) {
            dotProduct += vectorA.get(i) * vectorB.get(i);
            normA += Math.pow(vectorA.get(i), 2);
            normB += Math.pow(vectorB.get(i), 2);
        }
        double normProduct = Math.sqrt(normA) * Math.sqrt(normB);
        if (normProduct == 0) {
            return 0;
        }
        return dotProduct / normProduct;
    }

    private static void saveDictionary(Map<String, Integer> dictionary) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("dictionary.txt")))) {
            int count = 0;
            int size = dictionary.size();
            for (String word : dictionary.keySet()) {
                writer.write(word);
                if (count < size - 1) {
                    writer.write(", ");
                }
                count++;
            }
        }
    }
}
