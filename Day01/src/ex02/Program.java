package ex02;

public class Program {
    public static void main(String[] args) {
        UsersList usersList = new UsersArrayList();

        usersList.addUser(new User("John", 1000));
        usersList.addUser(new User("Jane", 1500));
        usersList.addUser(new User("Kate", 756));


        try {
            User userById = usersList.getUserById(2);
            System.out.println("Retrieved User by ID: " + userById.getUserDetails());
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
        }

        try {
            User userByIndex = usersList.getUserByIndex(2);
            System.out.println("Retrieved User by Index: " + userByIndex.getUserDetails());
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Total Users: " + usersList.getUsersCount());
    }
}
