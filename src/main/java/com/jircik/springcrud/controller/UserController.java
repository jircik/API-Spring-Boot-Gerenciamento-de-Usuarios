package com.jircik.springcrud.controller;

import com.jircik.springcrud.model.User;
import com.jircik.springcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller REST para gerenciar operações com a entidade User.
 * Mapeado para o caminho base "/api/v1/users".
 */
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * Construtor para injeção de dependência do UserService (melhor prática).
     * @param userService O serviço que contém a lógica de negócios para User.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // --- ENDPOINTS GET (READ) ---

    /**
     * Endpoint para buscar todos os usuários.
     * Mapeado para GET /api/v1/users
     * @return Uma lista de todos os usuários.
     */
    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    /**
     * Endpoint para buscar um usuário pelo ID.
     * Mapeado para GET /api/v1/users/{id}
     * @param id O ID do usuário (vindo do caminho da URL).
     * @return O objeto User encontrado.
     */
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    /**
     * Endpoint para buscar um usuário pelo nome.
     * Mapeado para GET /api/v1/users/name/{name}
     * @param name O nome do usuário (vindo do caminho da URL).
     * @return O objeto User encontrado.
     */
    @GetMapping("/name/{name}")
    public User getUserByName(@PathVariable String name){
        return userService.getUserByName(name);
    }

    // --- ENDPOINT POST (CREATE) ---

    /**
     * Endpoint para criar um novo usuário.
     * Mapeado para POST /api/v1/users
     * @param user O objeto User a ser criado, vindo do corpo da requisição.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Retorna o código de status HTTP 201 (Created)
    public void addNweUser(@RequestBody User user){
        userService.insertUser(user);
    }

    // --- ENDPOINT PUT (UPDATE) ---

    /**
     * Endpoint para atualizar um usuário existente.
     * Mapeado para PUT /api/v1/users
     * @param user O objeto User com os dados atualizados (incluindo o ID).
     */
    @PutMapping
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    // --- ENDPOINTS DELETE ---

    /**
     * Endpoint para excluir um usuário pelo ID.
     * Mapeado para DELETE /api/v1/users/{id}
     * @param id O ID do usuário a ser excluído.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna o código de status HTTP 204 (No Content)
    public void removeUserById(@PathVariable Integer id){
        userService.deleteById(id);
    }

    /**
     * Endpoint para excluir um usuário pelo nome.
     * Mapeado para DELETE /api/v1/users/name/{name}
     * @param name O nome do usuário a ser excluído.
     */
    @DeleteMapping("/name/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna o código de status HTTP 204 (No Content)
    public void removeUserByName(@PathVariable String name){
        userService.deleteByName(name);
    }

    // --- TRATAMENTO DE EXCEÇÕES ---

    /**
     * Manipulador de exceção para IllegalStateException (usada quando um recurso não é encontrado).
     * Garante que um erro 404 Not Found seja retornado em vez de um erro 500.
     * @param ex A exceção lançada.
     * @return A mensagem da exceção como corpo da resposta.
     */
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // Retorna o código de status HTTP 404 (Not Found)
    public String handleIllegalStateException(IllegalStateException ex) {
        return ex.getMessage();
    }
}