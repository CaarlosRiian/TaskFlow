# TaskFlow — Sistema de Gestão de Projetos e Equipes

O **TaskFlow** é uma API RESTful desenvolvida em **Spring Boot** para gerenciar projetos, tarefas, membros de equipe e comentários.  
O projeto tem como objetivo centralizar o controle das atividades, responsabilidades e prazos dentro de uma organização, facilitando o acompanhamento do progresso, o cumprimento de prazos e a distribuição de responsabilidades entre os colaboradores.  

Este projeto está sendo desenvolvido para a disciplina **Desenvolvimento de Sistemas Corporativos (DSC)**.

---

# 1. Objetivo do Sistema

### 1.1 O **TaskFlow** visa:

- Centralizar o gerenciamento de projetos, tarefas e equipes  
- Acompanhar o progresso dos projetos e o cumprimento de prazos  
- Distribuir responsabilidades entre os membros da equipe  
- Registrar comentários e atualizações das tarefas  

Tudo isso seguindo boas práticas corporativas de desenvolvimento e arquitetura em camadas.

# 1.2 Tema do Projeto e Justificativa

A proposta do TaskFlow é resolver problemas comuns de falta de organização e acompanhamento inadequado no gerenciamento de tarefas. Esses problemas podem gerar atrasos, retrabalhos e reduzir a produtividade da equipe. Com isso, o sistema surge como uma solução moderna para melhorar a comunicação, organização e acompanhamento de atividades, atendendo tanto pequenas equipes quanto grandes corporações.

---

# 1.3 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **Hibernate**
- **Lombok**
- **Docker e Docker Compose**
- **Spring Security & JWT: Autenticação e autorização robusta.**
- **Redis: Cache para alta performance.**
- **JUnit 5 & Mockito: Garantia de qualidade via testes.**
- **GitHub Actions: Pipeline de CI/CD.**

# 1.4 Segurança e Autenticação
A API utiliza Spring Security com autenticação via JWT (JSON Web Token).
- Login: Endpoint ```/auth/login``` gera o token de acesso.
- Autorização: Proteção de rotas baseada em roles de usuário.
- Swagger: Configurado com suporte a ```Bearer Token``` para testes diretos na interface.

# 1.5 Qualidade e Automação
- Testes Unitários: Focados na camada de ```Service``` (regras de negócio).
- Testes de Integração: Validam o fluxo completo (Controller -> Banco) utilizando banco de dados H2 em memória.
- CI/CD: Pipeline automatizada no GitHub Actions que executa o build e os testes em cada push.

# 1.6 Como Rodar o Projeto Localmente

### Pré-requisitos
- Java 21
- Maven
- PostgreSQL rodando localmente - (caso não use Docker)

### Clonar repositório
````bash
git clone https://github.com/CaarlosRiian/TaskFlow.git
cd taskflow
````

### Executar
````
mvn spring-boot:run
````
API disponível em:
```http://localhost:8080```

### Executar utilizando Docker

#### Build + Run
````docker-compose up --build````

#### Acessar aplicação

API: ```http://localhost:8080```

Banco PostgreSQL: porta ```5432```

# 2. Arquitetura da Aplicação

````src/main/java/com/taskflowproject/taskflow/````

- **controller/** → **(Camada responsável pelos Endpoints REST)**
- **service/** → **(Camada de regras de Negócio)**
- **repository/** → **(Camada de Persistência (Spring Data JPA))**
- **model/** → **(Entidades e Mapeamentos JPA)**
- **dto/** → **(Objetos de Transferência de Dados (DTOs))**
- **TaskFlowApplication.java** → **(Classe principal da aplicação)**


#  3. Endpoints — TaskFlow

## User - Usuário 

| Método | Endpoint        | Descrição                     | Códigos de Resposta                                  |
|--------|------------------|-------------------------------|--------------------------------------------------------|
| **POST**   | `/users`         | Criar um usuário              | **201 Created**, 400 Bad Request, 409 Conflict         |
| **GET**    | `/users`         | Listar todos os usuários      | **200 OK**                                             |
| **GET**    | `/users/{id}`    | Detalhar um usuário específico| **200 OK**, 404 Not Found                              |
| **PUT**    | `/users/{id}`    | Atualizar dados de um usuário | **200 OK**, 400 Bad Request, 404 Not Found             |
| **PATCH**  | `/users/{id}`    | Desativar um usuário          | **200 OK**, 404 Not Found                              |
| **DELETE** | `/users/{id}`    | Excluir um usuário            | **204 No Content**, 404 Not Found                      |

---

## Project - Projeto

| Método | Endpoint           | Descrição                        | Códigos de Resposta                                  |
|--------|---------------------|----------------------------------|--------------------------------------------------------|
| **POST**   | `/projects`         | Criar um projeto                 | **201 Created**, 400 Bad Request                       |
| **GET**    | `/projects`         | Listar todos os projetos         | **200 OK**                                             |
| **GET**    | `/projects/{id}`    | Detalhar um projeto específico   | **200 OK**, 404 Not Found                              |
| **PUT**    | `/projects/{id}`    | Atualizar dados de um projeto    | **200 OK**, 400 Bad Request, 404 Not Found             |
| **DELETE** | `/projects/{id}`    | Excluir um projeto               | **204 No Content**, 404 Not Found                      |

---

## Task - Tarefas

| Método | Endpoint         | Descrição                         | Códigos de Resposta                                  |
|--------|-------------------|-----------------------------------|--------------------------------------------------------|
| **POST**   | `/tasks`           | Criar uma tarefa                  | **201 Created**, 400 Bad Request                       |
| **GET**    | `/tasks`           | Listar todas as tarefas           | **200 OK**                                             |
| **GET**    | `/tasks/{id}`      | Detalhar uma tarefa específica    | **200 OK**, 404 Not Found                              |
| **PUT**    | `/tasks/{id}`      | Atualizar dados de uma tarefa     | **200 OK**, 400 Bad Request, 404 Not Found             |
| **DELETE** | `/tasks/{id}`      | Excluir uma tarefa                | **204 No Content**, 404 Not Found                      |

---

## Comment - Comentários 

| Método | Endpoint             | Descrição                            | Códigos de Resposta                                              |
|--------|-----------------------|----------------------------------------|--------------------------------------------------------------------|
| **POST**   | `/comments`             | Criar um comentário                   | **201 Created**, 400 Bad Request, 404 Not Found *(Task/Autor)*      |
| **GET**    | `/comments`             | Listar todos os comentários           | **200 OK**                                                         |
| **GET**    | `/comments/{id}`        | Detalhar um comentário específico     | **200 OK**, 404 Not Found                                          |
| **PUT**    | `/comments/{id}`        | Atualizar um comentário               | **200 OK**, 400 Bad Request, 404 Not Found                         |
| **DELETE** | `/comments/{id}`        | Excluir um comentário                 | **204 No Content**, 404 Not Found                                  |

---

## Auth - Autenticação

| Método | Endpoint            | Descrição                            | Códigos de Resposta                                                                                    |
|--------|----------------------|----------------------------------------|--------------------------------------------------------------------------------------------------------|
| **POST**   | `/auth/login`             | Autentica usuário e retorna o Token JWT                   | **200** OK, **401** Unauthorized (Senha/Email inválidos), **400** Bad Request (Usuário não encontrado) |

# 4. Modelo Lógico

<img width="700" height="657" alt="Modelo-Logico-Primeira-Entrega" src="https://github.com/user-attachments/assets/96f772ed-f43f-467b-9e6a-d699f2a5fc60" />

---

### 4.1 Entidades Principais

- **User**  
  Armazena informações dos usuários do sistema. Cada usuário pode gerenciar projetos, ser responsável por tarefas e criar comentários.

- **Project**  
  Representa os projetos cadastrados. Cada projeto possui um gerente e uma lista de tarefas associadas.

- **Task**  
  Cada tarefa pertence a um projeto e pode ser atribuída a um usuário. Inclui título, descrição, status e prioridade.

- **Comment**  
  Comentários adicionados pelos usuários em tarefas, permitindo comunicação e registro de atualizações.

### 4.2 Relacionamentos Importantes

- Um **User ( Usuário )** pode gerenciar vários **Project ( Projetos )**.  
- Um **Project ( Projeto )** possui várias **Task ( Tarefas )**.  
- Uma **Task ( Tarefa )** pode ter vários **Comment ( Comentários )**.  
- Um **User ( Usuário )** pode ter várias **Task ( Tarefas )** atribuídas e vários **Comment ( Comentários )** feitos.

