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

