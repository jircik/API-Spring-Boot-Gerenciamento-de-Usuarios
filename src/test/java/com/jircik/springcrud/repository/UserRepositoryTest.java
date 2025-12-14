package com.jircik.springcrud.repository;

import com.jircik.springcrud.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByName_shouldReturnUser() {
        User user = new User(null, "TestName", "test@repo.com");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByName("TestName");

        assertTrue(foundUser.isPresent());
        assertEquals("TestName", foundUser.get().getName());
    }

    @Test
    void findByName_shouldReturnEmptyOptional_whenNotFound() {
        Optional<User> foundUser = userRepository.findByName("NonExistentUser");

        assertTrue(foundUser.isEmpty());
    }
}
