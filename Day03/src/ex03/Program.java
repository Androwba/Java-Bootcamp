import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Program {
    private static final String FILE_URLS = "files_urls.txt";

    public static void main(String[] args) {
        int threadsCount = 0;
        if (args.length == 1 && args[0].startsWith("--threadsCount=")) {
            threadsCount = Integer.parseInt(args[0].split("=")[1]);
        } else {
            System.err.println("Usage: java Program --threadsCount=<number_of_threads>");
            System.exit(-1);
        }

        List<String> fileUrls = readFileUrls(FILE_URLS);
        if (fileUrls == null) {
            System.err.println("Failed to read file URLs.");
            System.exit(-1);
        }

        DownloadQueue queue = new DownloadQueue(fileUrls);

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadsCount; i++) {
            Thread thread = new Thread(new DownloadTask(queue));
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

        private static List<String> readFileUrls (String filename){
            List<String> urls = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = br.readLine()) != null) {
                    urls.add(line.split(" ", 2)[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return urls;
    }
}
