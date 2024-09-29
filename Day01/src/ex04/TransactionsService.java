package ex04;

import java.util.UUID;

public class TransactionsService {
    private UsersList users;

    public TransactionsService() {
        this.users = new UsersArrayList();
    }

    public void addUser(String name, int balance) {
        users.addUser(new User(name, balance));
    }

    public int retrieveUserBalance(int userId) throws UserNotFoundException {
        return users.getUserById(userId).getBalance();
    }

    public void performTransfer(int senderId, int recipientId, int amount) throws TransactionNotFoundException, UserNotFoundException {
        User sender = users.getUserById(senderId);
        User recipient = users.getUserById(recipientId);

        if (amount <= 0) {
            throw new TransactionNotFoundException("Amount must be positive.");
        }

        if (sender.getBalance() < amount) {
            throw new TransactionNotFoundException("Sender does not have enough balance.");
        }

        sender.updateBalance(-amount);
        recipient.updateBalance(amount);

        UUID transactionId = UUID.randomUUID();
        Transaction debit = new Transaction(sender, recipient, Transaction.TransferCategory.DEBIT, amount, transactionId);
        Transaction credit = new Transaction(recipient, sender, Transaction.TransferCategory.CREDIT, amount, transactionId);

        sender.getTransactions().addTransaction(debit);
        recipient.getTransactions().addTransaction(credit);
    }

    public void removeTransactionById(int userId, UUID transactionId) throws UserNotFoundException, TransactionNotFoundException {
        User user = users.getUserById(userId);
        TransactionsList transactions = user.getTransactions();

        boolean found = false;
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.toArray()[i];
            if (transaction.getId().equals(transactionId)) {
                transactions.removeTransactionById(transactionId.toString());
                found = true;
                break;
            }
        }
        if (!found) {
            throw new TransactionNotFoundException("Transaction with ID " + transactionId + " not found for user " + userId);
        }
    }

    public Transaction[] retrieveTransfers(int userId) throws UserNotFoundException {
        return users.getUserById(userId).getTransactions().toArray();
    }

    public User getUserById(int id) throws UserNotFoundException {
        return users.getUserById(id);
    }

    public User getUserByIndex(int index) throws UserNotFoundException {
        return users.getUserByIndex(index);
    }

    public int getUsersCount() {
        return users.getUsersCount();
    }

    public String getUserName(int userId) throws UserNotFoundException {
        User user = users.getUserById(userId);
        return user.getName();
    }

    public void removeTransactionById(int userId, String transactionId) throws TransactionNotFoundException, UserNotFoundException {
        User user = users.getUserById(userId);
        user.getTransactions().removeTransactionById(transactionId);
    }

    public Transaction[] checkValidTransactions() {
        Transaction[] allTransactions = new Transaction[100];
        int totalTransactions = 0;

        // Gather all transactions
        for (int i = 1; i <= users.getUsersCount(); i++) {
            try {
                User user = users.getUserById(i);
                for (Transaction transaction : user.getTransactions().toArray()) {
                    allTransactions[totalTransactions++] = transaction;
                    if (totalTransactions >= allTransactions.length) break;
                }
            } catch (UserNotFoundException e) {
            }
        }

        // Identify unpaired transactions
        boolean[] paired = new boolean[totalTransactions];
        for (int i = 0; i < totalTransactions; i++) {
            for (int j = 0; j < totalTransactions; j++) {
                if (i != j && allTransactions[i].getId().equals(allTransactions[j].getId())
                        && !allTransactions[i].getCategory().equals(allTransactions[j].getCategory())) {
                    // If they have the same ID but different categories, they are a pair
                    paired[i] = true;
                    paired[j] = true;
                }
            }
        }

        // Accumulate unpaired transactions
        Transaction[] unpairedTransactions = new Transaction[totalTransactions];
        int unpairedCount = 0;
        for (int i = 0; i < totalTransactions; i++) {
            if (!paired[i]) {
                unpairedTransactions[unpairedCount++] = allTransactions[i];
            }
        }

        // Manually trim the array to remove null values
        Transaction[] trimmedUnpairedTransactions = new Transaction[unpairedCount];
        for (int i = 0; i < unpairedCount; i++) {
            trimmedUnpairedTransactions[i] = unpairedTransactions[i];
        }
        return trimmedUnpairedTransactions;
    }
}
