package ex03;

public class User {
    private final int id;
    private String name;
    private int balance;
    private TransactionsList transactionsList;

    public User(String name, int balance) {
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        setBalance(balance);
        this.transactionsList = new TransactionsLinkedList();
    }

    public void addTransaction(Transaction transaction) {
        transactionsList.addTransaction(transaction);
    }

    public void removeTransactionById(String id) throws TransactionNotFoundException {
        transactionsList.removeTransactionById(id);
    }

    public Transaction[] getTransactionsArray() {
        return transactionsList.toArray();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
    }

    public int getTransactionsSize() {
        return this.transactionsList.size();
    }
}
