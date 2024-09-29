class RunnableTask implements Runnable {
    private MessagePrinter printer;
    private String message;

    public RunnableTask(MessagePrinter printer, String message) {
        this.printer = printer;
        this.message = message;
    }

    @Override
    public void run() {
        printer.print(message);
    }
}
