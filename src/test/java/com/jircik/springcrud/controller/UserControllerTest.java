package com.jircik.springcrud.controller;

import com.jircik.springcrud.model.User;
import com.jircik.springcrud.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes unitários para a camada Controller (apenas a camada Web/MVC).
 * Usa @WebMvcTest para focar no Controller, mockando o UserService.
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc; // Objeto para simular requisições HTTP

    @MockBean
    private UserService userService; // Mock do Service injetado no Controller

    private final User mockUser = new User(1, "Ana", "ana@test.com");
    private final String API_BASE = "/api/v1/users";
    private final String USER_JSON = """
            {
              "id": 1,
              "name": "Ana",
              "email": "ana@test.com"
            }
            """;

    // --- TESTES GET (READ) ---

    @Test
    void getAllUsers_shouldReturnList() throws Exception {
        // Arrange: Simula o service retornando uma lista
        when(userService.getUsers())
                .thenReturn(List.of(mockUser));

        // Act & Assert
        mockMvc.perform(get(API_BASE))
                .andExpect(status().isOk()) // Espera status 200
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Ana"));
    }

    @Test
    void getUserById_shouldReturnUser() throws Exception {
        // Arrange: Simula o service retornando o usuário
        when(userService.getUserById(1))
                .thenReturn(mockUser);

        // Act & Assert
        mockMvc.perform(get(API_BASE + "/1"))
                .andExpect(status().isOk()) // Espera status 200
                .andExpect(jsonPath("$.id").value(1));
    }

    // TESTE ADICIONADO: GET by ID com falha
    @Test
    void getUserById_shouldReturnNotFound_whenUserDoesNotExist() throws Exception {
        // Arrange
        Integer id = 999;
        // Simula o Service lançando a exceção (tratada pelo @ExceptionHandler)
        doThrow(new IllegalStateException("User not found")).when(userService).getUserById(id);

        // Act & Assert: Espera o status 404 (Not Found)
        mockMvc.perform(get(API_BASE + "/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }

    @Test
    void getUserByName_shouldReturnUser() throws Exception {
        // Arrange
        when(userService.getUserByName("Ana"))
                .thenReturn(mockUser);

        // Act & Assert
        mockMvc.perform(get(API_BASE + "/name/Ana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ana"));
    }

    // --- TESTE POST (CREATE) ---

    @Test
    void insertUser_shouldReturnCreated() throws Exception {
        // Arrange: Simula a inserção sem lançar exceção
        doNothing().when(userService).insertUser(any(User.class));

        // Act & Assert: Espera status 201 (Created)
        mockMvc.perform(post(API_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USER_JSON))
                .andExpect(status().isCreated());
    }

    // --- TESTE PUT (UPDATE) ---

    // TESTE ADICIONADO: Update bem-sucedido
    @Test
    void updateUser_shouldReturnOk() throws Exception {
        // Arrange: Simula o update sem lançar exceção
        doNothing().when(userService).updateUser(any(User.class));

        // Act & Assert: Espera status 200 (OK)
        mockMvc.perform(put(API_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USER_JSON))
                .andExpect(status().isOk());
    }

    // TESTE ADICIONADO: Update com falha
    @Test
    void updateUser_shouldReturnNotFound_whenUserDoesNotExist() throws Exception {
        // Arrange
        String notFoundMessage = "User not found for update";
        // Simula o Service lançando a exceção
        doThrow(new IllegalStateException(notFoundMessage)).when(userService).updateUser(any(User.class));

        // Act & Assert: Espera o status 404 (Not Found)
        mockMvc.perform(put(API_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USER_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(notFoundMessage));
    }

    // --- TESTES DELETE ---

    @Test
    void deleteUserById_shouldReturnNoContent() throws Exception {
        // Arrange: Simula a exclusão por ID
        doNothing().when(userService).deleteById(1);

        // Act & Assert: Espera status 204 (No Content)
        mockMvc.perform(delete(API_BASE + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUserById_shouldReturnNotFound_whenUserDoesNotExist() throws Exception {
        // Arrange
        Integer id = 999;
        String notFoundMessage = "User not found for deletion";

        // Simula o Service lançando a exceção
        doThrow(new IllegalStateException(notFoundMessage)).when(userService).deleteById(id);

        // Act & Assert
        mockMvc.perform(delete(API_BASE + "/{id}", id))
                .andExpect(status().isNotFound()) // Espera 404
                .andExpect(content().string(notFoundMessage));
    }

    // TESTE ADICIONADO: Delete by Name bem-sucedido
    @Test
    void deleteUserByName_shouldReturnNoContent() throws Exception {
        // Arrange: Simula a exclusão por nome
        doNothing().when(userService).deleteByName("Ana");

        // Act & Assert: Espera status 204 (No Content)
        mockMvc.perform(delete(API_BASE + "/name/Ana"))
                .andExpect(status().isNoContent());
    }

    // TESTE ADICIONADO: Delete by Name com falha
    @Test
    void deleteUserByName_shouldReturnNotFound_whenUserDoesNotExist() throws Exception {
        // Arrange
        String name = "Ghost";
        String notFoundMessage = "User not found for deletion by name";

        // Simula o Service lançando a exceção
        doThrow(new IllegalStateException(notFoundMessage)).when(userService).deleteByName(name);

        // Act & Assert
        mockMvc.perform(delete(API_BASE + "/name/{name}", name))
                .andExpect(status().isNotFound()) // Espera 404
                .andExpect(content().string(notFoundMessage));
    }
}