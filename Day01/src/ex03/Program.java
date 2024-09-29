package ex03;

import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User user = new User("John", 1000);
        System.out.println("Initial size of the transactions list: " + user.getTransactionsSize());

        Transaction transaction1 = new Transaction(user, new User("Recipient A", 500), Transaction.TransferCategory.DEBIT, 100);
        Transaction transaction2 = new Transaction(new User("Sender B", 1500), user, Transaction.TransferCategory.CREDIT, -200);

        user.addTransaction(transaction1);
        user.addTransaction(transaction2);

        Transaction[] transactions = user.getTransactionsArray();
        for (Transaction t : transactions) {
            System.out.println(t);
        }

        System.out.println("Size of the transactions list: " + user.getTransactionsSize());

        try {
            System.out.println("Removing a transaction...");
            user.removeTransactionById(transaction1.getId().toString());
            user.removeTransactionById(UUID.randomUUID().toString());
        } catch (TransactionNotFoundException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Final size of the transactions list: " + user.getTransactionsSize());
        System.out.println("Final balance for " + user.getName() + ": $" + user.getBalance());
    }
}
