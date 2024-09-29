public class Program {
    public static void main(String[] args) {
        int count = 50;
        if (args.length == 1) {
            try {
                String[] parts = args[0].split("=");
                if (parts.length == 2 && "--count".equals(parts[0])) {
                    int parsedCount = Integer.parseInt(parts[1]);
                    if (parsedCount > 0) {
                        count = parsedCount;
                    } else {
                        System.out.println("Invalid count value: " + parsedCount + ". Count must be greater than 0. Using default count: 50.");
                    }
                } else {
                    System.out.println("Invalid argument format. Expected '--count'. Received: " + parts[0]);
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format for 'count'. Using default count: 50");
            }
        }

        Thread eggThread = new Thread(new MessagePrinter("Egg", count));
        Thread henThread = new Thread(new MessagePrinter("Hen", count));

        eggThread.start();
        henThread.start();

        try {
            eggThread.join();
            henThread.join();
        } catch (InterruptedException e) {
            System.out.println("Thread Interrupted");
        }

        for (int i = 0; i < count; i++) {
            System.out.println("Human");
        }
    }
}
