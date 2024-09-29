package edu.school21.repositories;

import edu.school21.models.User;
import edu.school21.exceptions.EntityNotFoundException;

public interface UsersRepository {
    User findByLogin(String login) throws EntityNotFoundException;
    void update(User user) throws EntityNotFoundException;
}
