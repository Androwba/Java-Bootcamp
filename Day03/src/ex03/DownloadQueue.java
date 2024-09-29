import java.util.List;

public class DownloadQueue {
    private final List<String> urls;
    private int currentIndex = 0;

    public DownloadQueue(List<String> urls) {
        this.urls = urls;
    }

    public synchronized String getNextUrl() {
        if (currentIndex < urls.size()) {
            return urls.get(currentIndex++);
        }
        return null;
    }
}
