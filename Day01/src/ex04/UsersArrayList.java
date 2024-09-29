package ex04;

public class UsersArrayList implements UsersList {
    private User[] users;
    private int size = 0;

    public UsersArrayList() {
        this.users = new User[10];
    }

    @Override
    public void addUser(User user) {
        if (size == users.length) {
            increaseArraySize();
        }
        users[size++] = user;
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        for (int i = 0; i < size; i++) {
            if (users[i].getId() == id) {
                return users[i];
            }
        }
        throw new UserNotFoundException("User with ID " + id + " not found.");
    }

    @Override
    public User getUserByIndex(int index) throws UserNotFoundException {
        if (index < 0 || index >= size) {
            throw new UserNotFoundException("User at index " + index + " not found.");
        }
        return users[index];
    }

    @Override
    public int getUsersCount() {
        return size;
    }

    private void increaseArraySize() {
        int newSize = users.length + users.length / 2;
        User[] newUsers = new User[newSize];
        for (int i = 0; i < users.length; i++) {
            newUsers[i] = users[i];
        }
        users = newUsers;
    }
}
