package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {
    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean authenticate(String login, String password) throws EntityNotFoundException, AlreadyAuthenticatedException {
        User user = usersRepository.findByLogin(login);

        if (user == null) {
            throw new EntityNotFoundException("User does not exist");
        }

        if (user.isAuthenticated()) {
            throw new AlreadyAuthenticatedException("User is already authenticated");
        }

        if (user.getPassword().equals(password)) {
            user.setAuthenticated(true);
            usersRepository.update(user);
            return true;
        } else {
            return false;
        }
    }
}
