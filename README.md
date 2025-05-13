# Projeto Pizzaria - Backend com JWT

Este é um projeto simples de API REST usando **Spring Boot** com **autenticação JWT**, voltado para o gerenciamento de pizzas. Apenas a rota `/pizza` é protegida por token. As demais estão públicas ou são de login.

---

## 🔧 Tecnologias

- Java 17
- Spring Boot
- JWT (JJWT)
- Maven
- Docker + Docker Compose
- MySQL

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

Acesse: [http://localhost:8080/pizza](http://localhost:8080/pizza)

---

## 🧪 Testes com JWT

### 1. Autenticar e obter token

`POST /auth/login`

```json
{
  "username": "admin",
  "password": "senha123"
}
```

A resposta será:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 2. Acessar rota protegida

Use o token no cabeçalho:

```
Authorization: Bearer SEU_TOKEN_AQUI
```

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

## 📁 Estrutura de autenticação

- `/auth/login`: login básico com usuário fixo
- `/pizza`: rota protegida por JWT
- `JwtFilter.java`: verifica o token em cada requisição
- `JwtUtil.java`: gera e valida tokens
- `WebConfig.java`: registra o filtro somente para `/pizza/**`

---

## 🧪 Testes com cURL

### Linux/macOS

```bash
TOKEN=$(curl -s -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d '{"username":"admin","password":"senha123"}' | jq -r '.token')
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/pizza
```

### Windows PowerShell

```powershell
$response = Invoke-RestMethod -Uri "http://localhost:8080/auth/login" -Method POST -Body '{"username":"admin","password":"senha123"}' -ContentType "application/json"
$token = $response.token
Invoke-RestMethod -Uri "http://localhost:8080/pizza" -Headers @{ Authorization = "Bearer $token" }
```

---

## 📦 Postman Collection

Você pode importar o arquivo `postman_collection_jwt_pizza.json` para testar os endpoints no Postman.

---

## 👨‍🏫 Autor e objetivo

Este projeto foi preparado de forma **didática**, para fins de ensino de segurança com tokens em APIs REST usando Spring Boot.
