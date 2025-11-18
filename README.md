# 💰 Projeto Final - Sistema Financeiro (Backend)

Backend desenvolvido como parte do **Projeto Final do programa PraTI**, com o objetivo de gerenciar finanças pessoais de forma simples e eficiente.  
Permite controle de **metas financeiras, receitas, despesas, autenticação de usuários.

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3+**
- **Spring Data JPA**
- **Spring Security (JWT)**
- **PostgreSQL**
- **Hibernate**
- **Maven**
- **Lombok**
- **Jakarta Validation**
- **Docker + docker-compose (configuração incluída)**

---

## 📂 Estrutura do Projeto

###### backend/
###### ├── src/
###### │ ├── main/
###### │ │ ├── java/com/maisfinanca/backend/
###### │ │ │ ├── controller/ # Controladores REST
###### │ │ │ ├── dto/ # Objetos de transferência de dados (Requests e Responses)
###### │ │ │ ├── entity/ # Entidades JPA
###### │ │ │ ├── repository/ # Interfaces JPA Repository
###### │ │ │ ├── service/ # Regras de negócio
###### │ │ │ ├── exception/ # Tratamento de erros personalizados
###### │ │ │ └── config/ # Configurações (CORS, Security, etc.)
###### │ │ └── resources/
###### │ │ ├── application.yml # Configuração do banco de dados e JWT
###### │ │ └── data.sql # Dados iniciais (opcional)
###### │ └── test/ # Testes unitários e de integração
###### └── pom.xml

# ⚙️ Configuração do Ambiente

## Pré-requisitos

- Docker & Docker Compose

- Java 21 instalado (caso não rode via Docker)

- PostgreSQL (local ou via Docker)

## Passos para rodar

- Clone o repositório:
```bash
git clone https://github.com/ProjetoFinal-praTI/backend.git
cd backend
git checkout develop
```

- Configure as credenciais do banco no application.yml (ex: dev profile).

- Usando Docker Compose (recomendado):
```bash
docker-compose up --build
```

A API ficará disponível em http://localhost:8080/api/...

(Opcional) Use Postman/Insomnia para testar endpoints.

# 📚 Endpoints Principais
## Usuários

- POST /api/users – criar usuário

- GET /api/users – listar usuários

- GET /api/users/{id} – buscar usuário por id

- PUT /api/users/{id} – atualizar usuário

- DELETE /api/users/{id} – remover usuário

## Transações

- POST /api/transactions – criar transação

- GET /api/transactions/{id} – buscar por id

- PUT /api/transactions/{id} – atualizar

- DELETE /api/transactions/{id} – deletar

- POST /api/transactions/income/add – adicionar receita

- POST /api/transactions/income/remove – remover receita

- GET /api/transactions/income/total/{userId} – total de receita por usuário

- GET /api/transactions/user/{userId} – listar todas as transações de um usuário

## Metas Financeiras

- POST /api/transactions/goals – criar meta

- GET /api/transactions/goals/{userId} – listar metas do usuário

- PUT /api/transactions/goals/{goalId} – atualizar progresso da meta

- DELETE /api/transactions/goals/{goalId} – remover meta


## 1. Banco de Dados (PostgreSQL)
Crie um banco de dados no PostgreSQL:

```sql
CREATE DATABASE maisfinanca;
```

## Atualize o arquivo application.yml com suas credenciais:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/maisfinanca
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
    show-sql: true
```

# 🔐 Autenticação e Autorização

O sistema utiliza JWT (JSON Web Token) para autenticação de usuários.
Após o login, o token é retornado e deve ser incluído no cabeçalho das requisições:

```makefile
Authorization: Bearer <seu_token_jwt>
```

# 📈 Principais Funcionalidades
### 👤 Usuários

- Cadastro de novos usuários

- Login com autenticação JWT

- Recuperação e redefinição de senha por e-mail

### 💸 Finanças

- Receitas: registro e listagem de entradas financeiras

- Despesas: controle de gastos e categorias

- Metas Financeiras: criação, atualização e acompanhamento do progresso

- Atualização de valor acumulado

- Definição automática da finalDate quando a meta é atingida

### 📊 Relatórios

- Total de receitas e despesas

- Progresso de metas

- Percentual de atingimento

# 🧪 Executando o Projeto
Via Maven

```bash
mvn spring-boot:run
```
Ou via IDE (IntelliJ / Eclipse)

Execute a classe principal *BackendApplication.java*

# 🧰 Endpoints Principais

## Autenticação
| Método | Endpoint         | Descrição              |
| ------ | ---------------- | ---------------------- |
| POST   | `/auth/login`    | Faz login e gera token |

## Metas Financeiras
| Método | Endpoint                 | Descrição                    |
| ------ | ------------------------ | ---------------------------- |
| POST   | `/goals`                 | Cria nova meta               |
| GET    | `/goals/user/{userId}`   | Lista metas do usuário       |
| PUT    | `/goals/{goalId}/update` | Atualiza o progresso da meta |

## Receitas e Despesas
| Método | Endpoint              | Descrição                    |
| ------ | --------------------- | ---------------------------- |
| POST   | `/incomes`            | Adiciona receita             |
| POST   | `/expenses`           | Registra despesa             |
| GET    | `/incomes/user/{id}`  | Lista receitas de um usuário |
| GET    | `/expenses/user/{id}` | Lista despesas de um usuário |

# 🧾 Exemplo de Requisição (Criação de Meta)
```json
POST /goals
{
  "userId": 1,
  "name": "Comprar notebook",
  "targetValue": 5000.00
}
```

# Resposta:
```json
{
    "data": {
        "goalId": 4,
        "name": "Comprar notebook",
        "targetValue": 5000.00,
        "currentValue": 0
    },
    "errorMessage": null,
    "success": true
}
```

# 🧠 Boas Práticas e Convenções

Uso de BigDecimal para valores monetários 💵

Validações com @NotNull, @Email, @Positive, etc

Tratamento centralizado de exceções com @ControllerAdvice

Transações seguras com @Transactional

# 🧑‍💻 Equipe do Projeto

Projeto desenvolvido como parte do programa +PraTI, com colaboração dos participantes da turma final.
Equipe: Backend

# 🤝 Contribuição

Fork o repositório.

Crie branch com feature/bugfix: git checkout -b feature/…”

Faça commit com bom histórico: git commit -m "feat: …"

Envie pull request para branch develop.

# 📜 Licença

Este projeto está licenciado sob a licença MIT.

Sinta-se livre para utilizar, estudar e contribuir!


