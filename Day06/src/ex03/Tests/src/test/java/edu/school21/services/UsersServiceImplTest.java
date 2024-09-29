package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersServiceImplTest {

    private UsersRepository usersRepository;
    private UsersServiceImpl usersService;

    @BeforeEach
    void setUp() {
        usersRepository = mock(UsersRepository.class);
        usersService = new UsersServiceImpl(usersRepository);
    }

    @Test
    void testAuthenticateCorrectLoginAndPassword() throws EntityNotFoundException {
        User user = new User(1L, "test", "password", false);
        when(usersRepository.findByLogin("test")).thenReturn(user);
        boolean result = usersService.authenticate("test", "password");
        assertTrue(result);
        assertTrue(user.isAuthenticated());
        verify(usersRepository).update(user);
    }

    @Test
    void testAuthenticateIncorrectLogin() {
        when(usersRepository.findByLogin("unknown")).thenThrow(new EntityNotFoundException("User not found"));
        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                usersService.authenticate("unknown", "password");
            }
        });
    }

    @Test
    void testAuthenticateIncorrectPassword() throws EntityNotFoundException {
        User user = new User(1L, "test", "password", false);
        when(usersRepository.findByLogin("test")).thenReturn(user);
        boolean result = usersService.authenticate("test", "wrongpassword");
        assertFalse(result);
        assertFalse(user.isAuthenticated());
        verify(usersRepository, never()).update(user);
    }

    @Test
    void testAuthenticateAlreadyAuthenticated() throws EntityNotFoundException {
        User user = new User(1L, "test", "password", true);
        when(usersRepository.findByLogin("test")).thenReturn(user);
        assertThrows(AlreadyAuthenticatedException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                usersService.authenticate("test", "password");
            }
        });
    }
}
