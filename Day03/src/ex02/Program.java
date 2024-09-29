import java.util.Random;

public class Program {
    public static void main(String[] args) {
        int arraySize = 0;
        int threadsCount = 0;

        for (String arg : args) {
            if (arg.startsWith("--arraySize=")) {
                arraySize = Integer.parseInt(arg.substring("--arraySize=".length()));
            } else if (arg.startsWith("--threadsCount=")) {
                threadsCount = Integer.parseInt(arg.substring("--threadsCount=".length()));
            }
        }

        if (arraySize <= 0 || arraySize > 2000000 || threadsCount <= 0 || threadsCount > arraySize) {
            System.out.println("Invalid input parameters");
            return;
        }

        int[] array = new int[arraySize];
        Random random = new Random();
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(1000);
        }

        int singleThreadedSum = 0;
        for (int num : array) {
            singleThreadedSum += num;
        }
        System.out.println("Single-threaded sum: " + singleThreadedSum);

        Thread[] threads = new Thread[threadsCount];
        SumThread[] tasks = new SumThread[threadsCount];
        int segmentSize = (arraySize + threadsCount - 1) / threadsCount;

        for (int i = 0; i < threadsCount; i++) {
            int start = i * segmentSize;
            int end = Math.min(start + segmentSize, arraySize);
            tasks[i] = new SumThread(array, start, end);
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }

        int sumByThreads = 0;
        for (int i = 0; i < threadsCount; i++) {
            try {
                threads[i].join();
                sumByThreads += tasks[i].getSegmentSum();
                System.out.printf("Thread %d processed from index %d to %d with segment sum %d%n", i + 1, tasks[i].getStart(), tasks[i].getEnd() - 1, tasks[i].getSegmentSum());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Sum by threads: " + sumByThreads);
    }
}
