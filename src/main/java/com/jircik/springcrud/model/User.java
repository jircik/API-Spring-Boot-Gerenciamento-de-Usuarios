package com.jircik.springcrud.model;

import jakarta.persistence.*; // Importa anotações JPA
import java.util.Objects;

/**
 * Entidade de domínio que representa um usuário no sistema.
 * Mapeada para a tabela "app_user" no banco de dados.
 */
@Entity
@Table(name = "app_user")
public class User {

    // Chave primária
    @Id
    // Estratégia de geração de valor automático (geralmente usada para chaves primárias auto-incrementáveis)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Outros campos da entidade
    private String name;
    private String email;

    /**
     * Construtor padrão exigido pelo JPA.
     */
    public User(){

    }

    /**
     * Construtor para inicializar todos os campos.
     * @param id O ID do usuário.
     * @param name O nome do usuário.
     * @param email O email do usuário.
     */
    public User(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // --- Getters e Setters ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // --- Métodos de Conveniência (equals e hashCode) ---

    /**
     * Verifica se dois objetos User são iguais.
     * Baseado nos campos id, name e email.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        // Verifica se o objeto é nulo ou se as classes são diferentes
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        // Compara todos os campos relevantes usando Objects.equals para lidar com nulos
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email);
    }

    /**
     * Gera um código hash para o objeto User.
     * Baseado nos campos id, name e email.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}