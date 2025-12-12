# Spring Boot CRUD API with PostgreSQL

Este projeto é um exemplo simples de uma **API REST CRUD** desenvolvida com:

* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **PostgreSQL**
* **Docker / Docker Compose**

O objetivo é praticar a criação de uma aplicação backend completa usando boas práticas de estruturação em camadas.

---

## Estrutura de Arquivos

A arquitetura segue o padrão recomendado pelo Spring Boot:

```
src/main/java/com.seuprojeto
├── controller      # Endpoints da API
├── service         # Regras de negócio
├── repository      # Acesso ao banco (JPA)
├── model           # Entidades JPA (tabelas)
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

## Configuração do `application.properties`

O projeto usa as seguintes variáveis de conexão (ajuste conforme seu ambiente):

* URL do banco
* Usuário
* Senha
* Dialect do PostgreSQL
* Configurações de JPA/Hibernate

---

POST /users – cria usuário
GET /users – lista usuários
GET /users/{id} – busca por ID
PUT /users/{id} – atualiza usuário
DELETE /users/{id} – remove usuário por ID
DELETE /users/{nome} - remove usuario por nome
---

## Testes dos Endpoints

Você pode testar usando:

* Intellij HTTP Client (`Requests.http`)
* Postman
* Insomnia
* curl

---

## Como rodar o projeto

1. Suba o container Docker do Postgres:

   ```bash
   docker-compose up -d
   ```
2. Abra o projeto no IntelliJ ou VS Code.
3. Rode a classe principal `Application.java`.
4. A API estará disponível em:

   ```
   http://localhost:8080](http://localhost:8080)
   ```

---

## Tecnologias Usadas
- Java 17+
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
