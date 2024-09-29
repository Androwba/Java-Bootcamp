package edu.school21.sockets.services;

import edu.school21.sockets.exceptions.InvalidCredentialsException;
import edu.school21.sockets.exceptions.UserAlreadyExistsException;

public interface UsersService {
    void signUp(String username, String password) throws UserAlreadyExistsException;
    void signIn(String username, String password) throws InvalidCredentialsException;
}
