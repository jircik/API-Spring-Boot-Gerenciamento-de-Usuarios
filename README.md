# API CRUD com Spring Boot e PostgreSQL

Este projeto é um exemplo simples de uma **API REST CRUD** desenvolvida com:

* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **PostgreSQL**
* **Docker / Docker Compose**

O objetivo é praticar a criação de uma aplicação backend completa usando boas práticas de estruturação em camadas.

## Testes e Qualidade de Código

O projeto possui uma suíte de testes automatizados cobrindo as principais camadas da aplicação, garantindo qualidade e confiabilidade do código.

### Cobertura de Testes

* **Cobertura total:** **93%**
* Relatório gerado com **JaCoCo**
* Análise de cobertura por classes, métodos e linhas

### Tecnologias e Ferramentas de Teste

* **JUnit 5** – framework de testes
* **Mockito** – mocks e isolamento de dependências
* **Spring Boot Test** – suporte a testes no ecossistema Spring
* **MockMvc** – testes de controllers REST
* **JaCoCo** – análise de cobertura de código

---

## Estrutura de Pastas

```
src/main/java/com.springcrud
├── controller      # Endpoints da API
├── service         # Regras de negócio
├── repository      # Acesso ao banco (JPA)
├── model           # Entidades JPA
└── Application.java # Classe principal
```

---

## Executando o Postgres com Docker

O projeto utiliza um banco Postgres rodando em Docker. O arquivo `docker-compose.yml` sobe o banco automaticamente.

### **Subir o container:**

```
docker-compose up -d
```

### **Parar o container:**

```
docker-compose down
```

O banco irá rodar na porta configurada no `docker-compose.yml`.

---

## Endpoints da API

### **POST /users** – cria usuário

### **GET /users** – lista usuários

### **GET /users/{id}** – busca por ID

### **PUT /users/{id}** – atualiza usuário

### **DELETE /users/{id}** – remove usuário

---

## Tecnologias Usadas
- Java 21+
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Docker/Docker Compose

---

## Objetivo do Projeto
Praticar:
- Arquitetura em camadas
- Uso de Controllers, Services e Repositories
- Conexão com PostgreSQL usando Docker
- Criação de endpoints REST
- Estruturação de um CRUD completo

---

