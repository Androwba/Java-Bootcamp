public class Program {
    public static void main(String[] args) {
        int count = 10;
        if (args.length == 1) {
            try {
                String[] parts = args[0].split("=");
                if (parts.length == 2 && "--count".equals(parts[0])) {
                    int parsedCount = Integer.parseInt(parts[1]);
                    if (parsedCount > 0) {
                        count = parsedCount;
                    } else {
                        System.out.println("Invalid count value: " + parsedCount + ". Count must be greater than 0. Using default count: 10.");
                    }
                } else {
                    System.out.println("Invalid argument format. Expected '--count'. Received: " + parts[0]);
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format for 'count'. Using default count: 10");
            }
        }
        MessagePrinter printer = new MessagePrinter(count);
        Thread eggThread = new Thread(new RunnableTask(printer, "Egg"));
        Thread henThread = new Thread(new RunnableTask(printer, "Hen"));

        eggThread.start();
        henThread.start();
    }
}
