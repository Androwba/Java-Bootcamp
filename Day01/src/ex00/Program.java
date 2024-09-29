package ex00;

public class Program {
    public static void main(String[] args) {
        User john = new User("John", 1000);
        User mike = new User("Mike", 500);

        Transaction t1 = new Transaction(john, mike, Transaction.TransferCategory.DEBIT, 500);
        printOutput(t1);
        Transaction t2 = new Transaction(mike, john, Transaction.TransferCategory.CREDIT, -10);
        printOutput(t2);
    }

    private static void printOutput(Transaction transaction) {
        User sender = transaction.getSender();
        User recipient = transaction.getRecipient();

        System.out.println(transaction);
        System.out.println(sender.getId() + ":" + sender.getName() + "'s balance: $" + sender.getBalance());
        System.out.println(recipient.getId() + ":" + recipient.getName() + "'s balance: $" + recipient.getBalance());
        System.out.println();
    }
}
