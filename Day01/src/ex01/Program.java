package ex01;

public class Program {
    public static void main(String[] args) {
        User user1 = new User("John", 1000);
        User user2 = new User("Jane", 1500);
        User user3 = new User("Kate", 756);
        User user4 = new User("Lily", 4848);

        System.out.println(user1.getUserDetails());
        System.out.println(user2.getUserDetails());
        System.out.println(user3.getUserDetails());
        System.out.println(user4.getUserDetails());
    }
}
