package com.jircik.springcrud.service;

import com.jircik.springcrud.model.User;
import com.jircik.springcrud.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para a camada Service usando Mockito.
 * Foca na lógica de negócio e na interação com o Repository.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository; // O mock do repositório

    @InjectMocks
    private UserService userService; // A classe a ser testada, com o mock injetado

    private final User mockUser = new User(1, "Ana", "ana@test.com");

    // --- TESTES GET (READ) ---

    @Test
    void getUsers_shouldReturnAllUsers() {
        // Arrange
        List<User> users = List.of(mockUser);
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.getUsers();

        // Assert
        assertEquals(1, result.size());
        verify(userRepository).findAll(); // Verifica se o método foi chamado
    }

    // TESTE ADICIONADO: GET by ID bem-sucedido
    @Test
    void getUserById_shouldReturnUser_whenExists() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));

        // Act
        User result = userService.getUserById(1);

        // Assert
        assertEquals(1, result.getId());
        verify(userRepository).findById(1);
    }

    // TESTE ADICIONADO: GET by ID com falha
    @Test
    void getUserById_shouldThrowException_whenNotFound() {
        // Arrange
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> userService.getUserById(99),
                "Deve lançar IllegalStateException quando o usuário por ID não é encontrado");
    }

    @Test
    void getUserByName_shouldReturnUser_whenExists() {
        // Arrange
        when(userRepository.findByName("Ana")).thenReturn(Optional.of(mockUser));

        // Act
        User result = userService.getUserByName("Ana");

        // Assert
        assertEquals("Ana", result.getName());
    }

    @Test
    void getUserByName_shouldThrowException_whenNotFound() {
        // Arrange
        when(userRepository.findByName("Ghost")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> userService.getUserByName("Ghost"),
                "Deve lançar IllegalStateException quando o usuário por nome não é encontrado");
    }

    // --- TESTE POST (CREATE) ---

    @Test
    void insertUser_shouldSaveUser() {
        // Arrange
        User user = new User(null, "Bob", "bob@test.com");

        // Act
        userService.insertUser(user);

        // Assert
        verify(userRepository).save(user); // Verifica se o save foi chamado
    }

    // --- TESTES DELETE ---

    @Test
    void deleteById_shouldDelete_whenUserExists() {
        // Arrange
        when(userRepository.existsById(1)).thenReturn(true);

        // Act
        userService.deleteById(1);

        // Assert
        verify(userRepository).deleteById(1);
    }

    @Test
    void deleteById_shouldThrowException_whenUserDoesNotExist() {
        // Arrange
        when(userRepository.existsById(99)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> userService.deleteById(99));
        verify(userRepository, never()).deleteById(any()); // Garante que o delete NÃO foi chamado
    }

    @Test
    void deleteByName_shouldDeleteUser_whenExists() {
        // Arrange
        when(userRepository.findByName("Ana")).thenReturn(Optional.of(mockUser));

        // Act
        userService.deleteByName("Ana");

        // Assert
        verify(userRepository).delete(mockUser);
    }

    @Test
    void deleteByName_shouldThrowException_whenNotFound() {
        // Arrange
        when(userRepository.findByName("Ghost")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> userService.deleteByName("Ghost"));
        verify(userRepository, never()).delete(any()); // Garante que o delete NÃO foi chamado
    }

    // --- TESTES PUT (UPDATE) ---

    @Test
    void updateUser_shouldUpdateNameAndEmail() {
        // Arrange
        User existingUser = new User(1, "Old", "old@test.com");
        User updatedUser = new User(1, "New", "new@test.com");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));

        // Act
        userService.updateUser(updatedUser);

        // Assert: Verifica se o objeto existente foi modificado e salvo
        assertEquals("New", existingUser.getName());
        assertEquals("new@test.com", existingUser.getEmail());
        verify(userRepository).save(existingUser);
    }

    // TESTE ADICIONADO: Não deve atualizar se os campos forem nulos ou vazios
    @Test
    void updateUser_shouldNotUpdateIfFieldsAreNullOrEmpty() {
        // Arrange
        User existingUser = new User(1, "Existing Name", "existing@test.com");
        User updatedUser = new User(1, null, ""); // Nome null, Email vazio

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));

        // Act
        userService.updateUser(updatedUser);

        // Assert: Garante que os valores NÃO foram alterados
        assertEquals("Existing Name", existingUser.getName());
        assertEquals("existing@test.com", existingUser.getEmail());
        verify(userRepository).save(existingUser);
    }

    // TESTE ADICIONADO: Não deve atualizar se os campos forem IGUAIS
    @Test
    void updateUser_shouldNotUpdateIfFieldsAreSame() {
        // Arrange
        User existingUser = new User(1, "Same Name", "same@test.com");
        User updatedUser = new User(1, "Same Name", "same@test.com");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));

        // Act
        userService.updateUser(updatedUser);

        // Assert: Garante que os valores são os mesmos e o save é chamado
        assertEquals("Same Name", existingUser.getName());
        assertEquals("same@test.com", existingUser.getEmail());
        verify(userRepository).save(existingUser);
    }

    @Test
    void updateUser_shouldThrowException_whenUserDoesNotExist() {
        // Arrange
        User updatedUser = new User(99, "New", "new@test.com");
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> userService.updateUser(updatedUser));
        verify(userRepository, never()).save(any()); // Garante que o save NÃO foi chamado
    }
}