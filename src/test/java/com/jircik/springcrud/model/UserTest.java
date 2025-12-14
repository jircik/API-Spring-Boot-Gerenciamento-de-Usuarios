package com.jircik.springcrud.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserPropertiesAndConstructors() {
        // Testa o construtor vazio (se existir)
        User emptyUser = new User();
        assertNotNull(emptyUser);

        // Testa o construtor completo e os Getters
        User user = new User(1, "TestName", "test@model.com");
        assertEquals(1, user.getId());
        assertEquals("TestName", user.getName());
        assertEquals("test@model.com", user.getEmail());

        // Testa os Setters e garante a mutabilidade
        user.setId(2);
        user.setName("NewName");
        user.setEmail("new@model.com");

        assertEquals(2, user.getId());
        assertEquals("NewName", user.getName());
        assertEquals("new@model.com", user.getEmail());
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User(1, "Ana", "ana@test.com");
        User user2 = new User(1, "Ana", "ana@test.com"); // Objeto idêntico
        User user3 = new User(2, "Bob", "bob@test.com"); // Objeto diferente

        // 1. Testa a igualdade: Objetos idênticos devem ser iguais
        assertTrue(user1.equals(user2));
        assertEquals(user1.hashCode(), user2.hashCode());

        // 2. Testa a desigualdade: Objetos diferentes não devem ser iguais
        assertFalse(user1.equals(user3));

        // Se você quer manter a cobertura do toString, adicione:
        assertNotNull(user1.toString());
    }
}