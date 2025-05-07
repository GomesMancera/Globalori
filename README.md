# ⚡ Globalori – API REST com Domain Driven Design

Este projeto implementa uma API REST em Java utilizando princípios de Domain Driven Design (DDD), focado em gerenciamento de consumo energético.

## 🎯 Objetivo

Desenvolver uma aplicação backend estruturada para simular e registrar o consumo energético de usuários e dispositivos, utilizando Java com arquitetura baseada em camadas e boas práticas de desenvolvimento.

---

## 🧱 Estrutura do Projeto

```
src/main/java/watteco
├── controladores      # Camada de entrada: expõe a API REST
├── entidades          # Camada de domínio: objetos de negócio
├── excecoes           # Exceções específicas e handler global
├── filter             # Filtros de requisição (ex: CORS)
├── infraestrutura     # Configurações técnicas (DB, Logger)
├── repositorios       # Interface com o banco (DAO)
├── servicos           # Regras de negócio (camada de serviço)
```

---

## 📦 Principais Componentes

### 🔹 Controladores (Resources)
- `UsuarioResource`, `DispositivoResource`, `ConsumoEnergeticoResource`, etc.
- Disponibilizam operações CRUD via endpoints REST.

### 🔹 Entidades
- `Usuario`, `Dispositivo`, `ConsumoEnergetico`, `Relatorio`, etc.
- Representam os objetos principais do domínio, com encapsulamento de lógica.

### 🔹 Serviços
- Contêm a **lógica de negócio**, como:
  - Cálculo de consumo energético diário/mensal.
  - Geração de relatórios.
  - Validações específicas por entidade.

### 🔹 Repositórios
- Interfaces para persistência no banco de dados.
- Extensíveis via `_RepositorioCrud`, seguindo o padrão DAO.

### 🔹 Exceções e Handler
- Exceções específicas como `ConsumoEnergeticoInvalidoException`, `UsuarioNaoEncontradoException`.
- Tratamento centralizado com `GlobalExceptionHandler`.

### 🔹 Infraestrutura
- Configuração de banco (`DatabaseConfig`) e logging (`Log4jLogger`).

---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot
- Maven
- JPA / Hibernate
- Log4j
- REST APIs
- PostgreSQL (ou outro RDBMS)

---

## 🔁 Exemplos de Endpoints

| Método | Rota                      | Descrição                          |
|--------|---------------------------|------------------------------------|
| GET    | `/usuarios`               | Lista todos os usuários            |
| POST   | `/consumo`                | Registra um consumo energético     |
| GET    | `/relatorio/{id}`         | Gera relatório por usuário         |
| PUT    | `/usuario/{id}`           | Atualiza dados do usuário          |
| DELETE | `/dispositivo/{id}`       | Remove um dispositivo              |

(Demais endpoints serão documentados em breve com Swagger)

---

## 📄 Entregáveis

- ✅ Código-fonte da API Java
- 📊 Diagrama de classes (em desenvolvimento)
- 📘 Documentação das rotas HTTP (parcial)

---

## ⚙️ Status do Projeto

> ✔️ Estrutura principal implementada  
> 🔌 API funcional com endpoints básicos  
> 🧪 Documentação e testes em desenvolvimento  
> 📦 Projeto com 1 commit inicial

---

## 📜 Licença

Este projeto é livre para fins educacionais e uso pessoal.
