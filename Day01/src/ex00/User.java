package ex00;

public class User {
    private static int idCounter = 0;
    private final int id;
    private String name;
    private int balance;

    public User(String name, int balance) {
        this.id = ++idCounter;
        this.name = name;
        setBalance(balance);
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
}
