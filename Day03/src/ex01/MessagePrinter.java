class MessagePrinter {
    private int count;
    private boolean eggTurn = true;

    public MessagePrinter(int count) {
        this.count = count;
    }

    public synchronized void print(String message) {
        for (int i = 0; i < count; i++) {
            while ((eggTurn && !message.equals("Egg")) || (!eggTurn && !message.equals("Hen"))) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Thread interrupted");
                }
            }
            System.out.println(message);
            eggTurn = !eggTurn;
            notifyAll();
        }
    }
}
