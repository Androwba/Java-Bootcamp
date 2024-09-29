import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

class DownloadTask implements Runnable {
    private final DownloadQueue queue;

    public DownloadTask(DownloadQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            String url = queue.getNextUrl();
            if (url == null) {
                break;
            }
            try {
                downloadFile(url);
            } catch (IOException | URISyntaxException e) {
                System.err.println(Thread.currentThread().getName() + " failed to download file " + url + ": " + e.getMessage());
            }
        }
    }

    private void downloadFile(String fileUrl) throws IOException, URISyntaxException {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
        System.out.println(Thread.currentThread().getName() + " start download file " + fileUrl);

        URI uri = new URI(fileUrl);
        URL url = uri.toURL();

        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
            System.out.println(Thread.currentThread().getName() + " finish download file " + fileUrl);
        } catch (IOException e) {
            throw new IOException("Error downloading file " + fileUrl, e);
        }
    }
}
