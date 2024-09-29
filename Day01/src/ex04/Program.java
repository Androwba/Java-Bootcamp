package ex04;

import java.util.UUID;

public class Program {

    private static void displayUnpairedTransactions(TransactionsService service) {
        Transaction[] unpairedTransactions = service.checkValidTransactions();
        if (unpairedTransactions.length > 0) {
            System.out.println("Unpaired transactions:");
            for (Transaction t : unpairedTransactions) {
                System.out.println(t);
            }
        } else {
            System.out.println("All transactions are paired!");
        }
    }
    public static void main(String[] args) {
        TransactionsService service = new TransactionsService();

        service.addUser("John", 1000);
        service.addUser("Jane", 1500);
        service.addUser("Kate", 700);
        service.addUser("Adam", 900);

        for (int userId = 1; userId <= service.getUsersCount(); userId++) {
            try {
                System.out.println("Initial balance for " + service.getUserName(userId) + ": $" + service.retrieveUserBalance(userId));
            } catch (UserNotFoundException e) {
                System.err.println("User not found: " + e.getMessage());
            }
        }

        try {
            service.performTransfer(1, 2, 200);
            service.performTransfer(3, 4, 500);
            System.out.println("Valid transfers completed successfully.");
        } catch (TransactionNotFoundException | UserNotFoundException e) {
            System.err.println(e.getMessage());
        }

        try {
            System.out.println("Attempting an invalid transfer...");
            service.performTransfer(1, 2, 5000);
        } catch (TransactionNotFoundException | UserNotFoundException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        for (int userId = 1; userId <= service.getUsersCount(); userId++) {
            try {
                System.out.println("Transactions for " + service.getUserName(userId) + ":");
                for (Transaction transaction : service.retrieveTransfers(userId)) {
                    System.out.println(transaction);
                }
            } catch (UserNotFoundException e) {
                System.err.println("User not found: " + e.getMessage());
            }
        }

        displayUnpairedTransactions(service);

        for (int userId = 1; userId <= service.getUsersCount(); userId++) {
            try {
                System.out.println("Updated balance for " + service.getUserName(userId) + ": $" + service.retrieveUserBalance(userId));
            } catch (UserNotFoundException e) {
                System.err.println("User not found: " + e.getMessage());
            }
        }
        // Retrieve and print transactions for a specific user
        try {
            Transaction[] userTransactions = service.retrieveTransfers(2);
            System.out.println("Retrieving transfers of a specific user:");
            for (Transaction transaction : userTransactions) {
                System.out.println(transaction);
            }
        } catch (UserNotFoundException e) {
            System.err.println("User not found: " + e.getMessage());
        }
        // Remove a specific transaction by ID for a user
        try {
            Transaction[] transactions = service.retrieveTransfers(1);
            if (transactions.length > 0) {
                UUID transactionId = transactions[0].getId();
                service.removeTransactionById(1, transactionId);
                System.out.println("Transaction " + transactionId + " removed successfully.");
            } else {
                System.out.println("No transactions available for removal.");
            }
        } catch (UserNotFoundException | TransactionNotFoundException e) {
            System.err.println(e.getMessage());
        }
        displayUnpairedTransactions(service);
    }
}
