# Projeto Pizzaria - Backend Simples com Spring Boot

## ⚡ Pré-requisitos

- Java 17 instalado
- Maven instalado
- Docker e Docker Compose instalados

---

## 🔧 Tecnologias

- Java 17
- Spring Boot
- Maven
- Docker + Docker Compose
- MySQL

---

## 🚀 Como rodar o projeto localmente (sem Docker)

1. Configure o banco de dados MySQL localmente (ou ajuste as variáveis em `src/main/resources/application.properties`).
2. Execute o comando:

```bash
./mvnw spring-boot:run
```

A API estará disponível em [http://localhost:8080/pizza](http://localhost:8080/pizza)

---

## 🚀 Como subir o projeto com Docker Compose

### 1. Build do projeto

Certifique-se de que o projeto esteja compilado:

```bash
./mvnw clean package
```

### 2. Subir os containers

```bash
docker-compose up -d
```

> Isso irá subir o banco de dados MySQL e o backend na porta 8080.

### 3. Verificar se a API está rodando

Acesse: [http://localhost:8080/pizza](http://localhost:8080/pizza)  caso use uma VM a porta será 8099

---

## 📁 Endpoints disponíveis

- `GET /pizza`: lista todas as pizzas cadastradas
- `POST /pizza`: cadastra uma nova pizza
- `PUT /pizza/{id}`: atualiza uma pizza existente
- `DELETE /pizza/{id}`: remove uma pizza por ID

### Exemplo de payload para POST/PUT

```json
{
  "nome": "Calabresa",
  "preco": 39.90,
  "ingredientes": ["calabresa", "queijo", "molho de tomate"]
}
```

> Obs: não há autenticação ou segurança implementadas nesta versão. Todos os endpoints estão abertos para testes.

---

## 📖 Documentação Swagger

Acesse a documentação interativa em: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## ⚙️ Configuração do banco de dados

As configurações do banco podem ser ajustadas em `src/main/resources/application.properties` ou via variáveis de ambiente no Docker Compose.

---

## 🛠️ Problemas comuns e soluções

### ❌ Docker Compose travou ou container ficou corrompido

Execute:

```bash
docker-compose down
```

### ❌ Limpar tudo e recomeçar

```bash
docker system prune -af --volumes
```

> ⚠️ Isso removerá **todas as imagens, containers e volumes** não usados. Use com cuidado!

---

## 🧪 Testes com cURL

### Linux/macOS

```bash
curl http://localhost:8080/pizza
```

### Windows PowerShell

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/pizza"
```

---

## 👨‍🏫 Objetivo

Este projeto foi elaborado com fins **educacionais**, para demonstrar a construção de uma API REST simples com Java e Spring Boot, utilizando um banco de dados relacional e boas práticas na estrutura do projeto.

