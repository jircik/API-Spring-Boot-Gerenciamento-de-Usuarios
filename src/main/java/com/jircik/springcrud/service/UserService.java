package com.jircik.springcrud.service;

import com.jircik.springcrud.model.User;
import com.jircik.springcrud.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Camada de Serviço que contém a lógica de negócios para a entidade User.
 * Interage com o UserRepository para persistência de dados.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Construtor para injeção de dependência do UserRepository.
     * @param userRepository O repositório de dados para User.
     */
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // --- Operações READ (GET) ---

    /**
     * Retorna uma lista de todos os usuários.
     * @return Uma lista de objetos User.
     */
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Busca um usuário pelo ID.
     * @param id O ID do usuário a ser buscado.
     * @return O objeto User.
     * @throws IllegalStateException se o usuário com o ID especificado não for encontrado.
     */
    public User getUserById(Integer id) {
        // Usa findById que retorna Optional, e orElseThrow para lançar exceção se ausente.
        return userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User with ID " + id + " not found"));
    }

    /**
     * Busca um usuário pelo nome.
     * @param name O nome do usuário a ser buscado.
     * @return O objeto User.
     * @throws IllegalStateException se o usuário com o nome especificado não for encontrado.
     */
    public User getUserByName(String name) {
        return userRepository.findByName(name).orElseThrow(() -> new IllegalStateException(name + " not found"));
    }

    // --- Operação CREATE (INSERT) ---

    /**
     * Insere um novo usuário ou atualiza um existente (se o ID estiver preenchido).
     * @param user O objeto User a ser salvo.
     */
    public void insertUser(User user) {
        userRepository.save(user);
    }

    // --- Operação DELETE ---

    /**
     * Exclui um usuário pelo ID.
     * @param id O ID do usuário a ser excluído.
     * @throws IllegalStateException se o usuário com o ID não existir.
     */
    public void deleteById(Integer id) {
        // Verifica se o usuário existe antes de tentar deletar
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
        } else{
            throw new IllegalStateException("User with ID " + id + " does not exist and cannot be deleted.");
        }
    }

    /**
     * Exclui um usuário pelo nome.
     * @param name O nome do usuário a ser excluído.
     * @throws IllegalStateException se o usuário com o nome não existir.
     */
    public void deleteByName(String name) {
        // Primeiro encontra o usuário, o que lança a exceção se não for encontrado
        User userToDelete = userRepository.findByName(name).orElseThrow(() -> new IllegalStateException("User with name " + name + " does not exist and cannot be deleted."));

        // Em seguida, deleta a entidade encontrada
        userRepository.delete(userToDelete);
    }

    // --- Operação UPDATE (PUT) ---

    /**
     * Atualiza os campos de um usuário existente.
     * @param updatedUser O objeto User com o ID e os novos dados.
     * @throws IllegalStateException se o usuário a ser atualizado não for encontrado.
     */
    public void updateUser(User updatedUser) {
        // 1. Encontra o usuário existente pelo ID
        User existingUser = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new IllegalStateException(
                        "User with ID " + updatedUser.getId() + " does not exist and cannot be updated."
                ));

        // 2. Modifica o objeto existente com os novos dados, se fornecidos e diferentes

        // Verifica o nome
        if (updatedUser.getName() != null &&
                !updatedUser.getName().isEmpty() &&
                !existingUser.getName().equals(updatedUser.getName())) {

            existingUser.setName(updatedUser.getName());
        }

        // Verifica o email
        if (updatedUser.getEmail() != null &&
                !updatedUser.getEmail().isEmpty() &&
                !existingUser.getEmail().equals(updatedUser.getEmail())) {

            // TODO: Adicionar verificação de email único aqui, se for uma restrição de negócio.
            existingUser.setEmail(updatedUser.getEmail());
        }

        // 3. Salva a entidade modificada (o Spring Data JPA executa o SQL UPDATE)
        userRepository.save(existingUser);
    }
}
