# <center>Delivery Service</center>

Este repositório contém o **Delivery Service REST API**, uma aplicação REST para gestão de entregas em logística, organizada em módulos Maven. \
A solução utiliza MySQL em produção, H2 e Mockito em testes, migrações com Flyway, monitoramento com Actuator e documentação interativa via OpenAPI/Swagger. A containerização fica a cargo de Docker e Docker Compose, e os logs são gerenciados por SLF4J/Logback.

---

## Funcionalidades Principais

- Gestão de pedidos de entrega via endpoints RESTful
- Persistência de dados com Spring Data JPA e MySQL
- Versionamento de esquema de banco com Flyway
- Monitoramento e health checks expostos pelo Spring Boot Actuator
- Documentação automática e interativa com Springdoc OpenAPI/Swagger
- Arquitetura multi-módulo Maven (rest-api, business, repository, domain)
- Testes:
   - Repositório: `@DataJpaTest` com H2 em memória
   - Controller: Requisições e checagens via `MockMvc`
- Containerização com Docker e orquestração via Docker Compose
- Perfis de configuração (`default` , `test`)

---

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Data JPA
- MySQL 8
- H2 Database (testes de repositório)
- JUnit 5 & Mockito
- Maven
- Flyway
- HikariCP (pool de conexões)
- Spring Boot Actuator
- Springdoc OpenAPI/Swagger
- Docker & Docker Compose
- SLF4J + Logback

---

## Estrutura do Projeto

```
delivery-service
├── pom.xml                    # Parent POM com plugins e dependências comuns
├── docker-compose.yml         # Orquestração: app + MySQL
├── Dockerfile                 # Imagem da aplicação Delivery Service
├── delivery-service-rest-api
│   ├── pom.xml                # Módulo Web (controllers, DTOs, mappers, ExceptionHandler)
│   └── src
├── delivery-service-business
│   ├── pom.xml                # Regras de negócio (controle de entregas)
│   └── src
├── delivery-service-repository
│   ├── pom.xml                # Spring Data JPA (repositories)
│   └── src
├── delivery-service-domain
│   ├── pom.xml                # Entities e Exceptions
│   └── src
└── src
    └── docs
        └── db/migration       # Scripts Flyway
```

---

## Configuração e Execução

### 1. Clonar o repositório

```bash
git clone git@github.com:danieldoc/delivery-service.git
cd delivery-service
```
### 2. Executar com Docker Compose (recomendado)

```bash
docker compose up --build -d
```

- `--build` constrói a aplicação
- `-d` Executa em segundo plano

A API ficará disponível em `http://localhost:8080/api` e o Swagger em `http://localhost:8080/api/swagger-ui/index.html`.

---

### 3. Executar localmente (Maven)

```bash
mvn clean spring-boot:run -pl delivery-service-rest-api
```

O perfil `default` conecta ao MySQL (local ou container), aplica Flyway e expõe o Actuator em `/actuator`.

## Perfis de Configuração

- application.yml  
  Conexão com MySQL e habilita Flyway/Actuator.

- application-test.yml  
  H2 em memória, sem migrações
---

## Flyway

Migrações em `delivery-service-rest-api/src/main/resources/db/migration`

---

## Spring Boot Actuator

Para monitoramento de status do serviço:

- `/actuator/health`

---

### Pool de Conexões

A aplicação utiliza **HikariCP** como pool de conexões JDBC. O Hikari oferece alta performance, baixo consumo de recursos e configuração automática para a maioria dos cenários. Os parâmetros podem ser ajustados via `application.yml` conforme a necessidade de tuning.