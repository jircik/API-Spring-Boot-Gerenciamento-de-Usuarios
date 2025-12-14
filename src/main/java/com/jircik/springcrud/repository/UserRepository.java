package com.jircik.springcrud.repository;

import com.jircik.springcrud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interface Repository para a entidade User.
 * Estende JpaRepository, fornecendo métodos CRUD (como findById, findAll, save, delete).
 * O primeiro tipo é a Entidade (User) e o segundo é o tipo da Chave Primária (Integer).
 */
public interface UserRepository extends JpaRepository<User,Integer> {

    /**
     * Método de consulta derivado personalizado para encontrar um User pelo nome.
     * Retorna um Optional<User> para lidar graciosamente com a ausência de um resultado.
     * @param name O nome a ser buscado.
     * @return Um Optional contendo o User, se encontrado.
     */
    Optional<User> findByName(String name);

    // Nota: O método findById(Integer id) já é herdado de JpaRepository.
}