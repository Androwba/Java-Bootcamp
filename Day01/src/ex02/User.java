package ex02;

public class User {
    private final int id;
    private String name;
    private int balance;

    public User(String name, int balance) {
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        this.balance = balance > 0 ? balance : 0;
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

    public String getUserDetails() {
        return "User ID = " + id + ", Name = " + name + ", Balance = $" + balance;
    }
}
