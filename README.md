🚀 Carteira Digital — API Bancária 🚀

API REST de uma Carteira Digital / Banco Simplificado, desenvolvida em Java + Spring Boot, com foco em back-end, regras de negócio e integridade financeira.

Projeto criado para estudo e portfólio, seguindo boas práticas de arquitetura, validação e transações.

Tecnologias utilizadas:
- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Validation
- Spring Security (configuração básica)
- PostgreSQL
- Flyway (migrations)
- Hibernate
- Maven

Arquitetura:
- O projeto segue uma arquitetura em camadas:
- Controller → recebe requisições HTTP
- Service → regras de negócio
- Repository → acesso ao banco
- DTOs → entrada e saída de dados
- Mapper → conversão Entity ↔ DTO
- GlobalExceptionHandler → padronização de erros
- Entidades nunca são expostas diretamente para fora da API.

Funcionalidades (MVP)
- 👤 Cliente
- Criar cliente
- Buscar cliente por ID
- Buscar cliente por CPF
- Validação de CPF e e-mail únicos

- 🏦 Conta
- Criar conta bancária
- Buscar conta por ID
- Listar contas de um cliente
- Validação de:
- cliente existente
- agência + número únicos
- status da conta

- 💰 Transações
- Depósito
- Saque (com validação de saldo)
- Transferência entre contas
- Buscar transação por ID
- Extrato bancário por conta
- Todas as operações financeiras são transacionais.

Modelagem de Entidades:
- Client
- id
- name
- cpf (único)
- email (único)
- endereco
- createdAt
- updatedAt

Account:
- id
- agencia
- numero
- tipo (CORRENTE / POUPANÇA)
- saldo
- status (ATIVA / BLOQUEADA / CANCELADA)
- owner (Client)
- version (Optimistic Lock)

Transaction:
- id
- account
- counterAccount (opcional)
- tipo (DEPOSITO, SAQUE, TRANSFERENCIA)
- valor
- status
- descricao
- createdAt
- processedAt

🔄 Fluxo das operações financeiras:
- Depósito
- Valida conta
- Verifica status ATIVA
- Incrementa saldo
- Registra transação

Saque:
- Valida conta
- Verifica saldo suficiente
- Decrementa saldo
- Registra transação

Transferência:
- Valida conta origem e destino
- Verifica saldo e status
- Atualiza saldo das duas contas
- Registra transação com contraConta

🌐 Endpoints principais
Cliente:
- POST /clients/create
- GET /clients/{id}
- GET /clients/cpf/{cpf}

Conta:
- POST /accounts
- GET /accounts/{id}
- GET /accounts/client/{clientId}

Transações:
- POST /transactions/deposit
- POST /transactions/withdraw
- POST /transactions/transfer
- GET /transactions/{id}
- GET /transactions/statement/{accountId}

⚠️ Tratamento de erros
- A API utiliza um GlobalExceptionHandler, retornando erros padronizados:
- 400 → dados inválidos
- 404 → recurso não encontrado
- 409 → conflito de negócio
Exemplo de resposta:
{
  "status": 400,
  "error": "Bad Request",
  "message": "Saldo insuficiente",
  "timestamp": "2025-12-29T16:10:00"
}
