# DONGNATE

Aplicativo mobile que conecta ONGs a doadores por meio de um sistema de cadastro e correspondência de itens.

## Integrantes
- THIAGO HENRIQUE CIRIANO NEVES
- ALVARO LUANDREY DE SANTANA DE FREITAS
- MATHEUS DE OLIVEIRA MOTA

## Tecnologias
- Kotlin
- Jetpack Compose
- Ktor
- PostgreSQL

## Padrões Aplicados (AV1)
- Factory Method
- Facade

# ✅ CHECKLIST AV1 — ORDEM CORRETA DE EXECUÇÃO

---

## 🟢 FASE 1 — Definição (1–2 dias)

### 1️⃣ Definir escopo mínimo (congelar escopo)

* [ ] Nome do projeto
* [ ] Funcionalidades principais:
  * [ ] Cadastro de usuário (ONG / Doador)
  * [ ] Cadastro de item
  * [ ] Listagem de itens
  * [ ] Sistema simples de match manual
* [ ] Escolher oficialmente os 2 padrões:
  * [ ] Factory Method
  * [ ] Facade

---

## 🟢 FASE 2 — Banco de Dados (BASE DE TUDO)


### 2️⃣ Modelar o banco 

Entidades mínimas:

* [ ] users

  * id
  * name
  * email
  * password
  * role (ONG / DONOR)

* [ ] items

  * id
  * name
  * quantity
  * urgency
  * user_id (FK)

* [ ] donations

  * id
  * donor_id
  * ong_id
  * item_id
  * quantity

---

### 3️⃣ Criar no Supabase

* [ ] Criar projeto
* [ ] Criar tabelas
* [ ] Criar FKs
* [ ] Testar inserção manual no SQL Editor
* [ ] Ativar Row Level Security depois (se der tempo)

---

## 🟢 FASE 3 — Backend (Kotlin + Ktor ou Spring)

### 4️⃣ Criar projeto backend

* [ ] Configurar conexão com Supabase (Postgres)
* [ ] Criar camadas:

  * controller
  * service
  * repository
  * factory
  * facade

---

### 5️⃣ Implementar PRIMEIRA funcionalidade completa (Vertical)

🔵 Funcionalidade 1: Cadastro de Usuário

* [ ] Criar entidade User
* [ ] Implementar Factory Method
* [ ] Criar endpoint POST /users
* [ ] Testar no Postman
* [ ] Criar GET /users

---

### 6️⃣ Funcionalidade 2: Cadastro de Item

* [ ] Criar entidade Item
* [ ] POST /items
* [ ] GET /items
* [ ] Vincular ao user_id

---

### 7️⃣ Funcionalidade 3: Criar Doação

Aqui entra o Facade:

* [ ] Criar DonationFacade
* [ ] Criar endpoint POST /donations
* [ ] Atualizar estoque
* [ ] Validar regras básicas

✔ Backend pronto = 70% da AV1 feita.

---

## 🟢 FASE 4 — Frontend (Jetpack Compose)

---

### 8️⃣ Estrutura base do app

* [ ] Criar projeto Compose
* [ ] Configurar Retrofit
* [ ] Criar ViewModel base

---

### 9️⃣ Telas mínimas

* [ ] Tela de Cadastro/Login
* [ ] Tela de Listagem de Itens
* [ ] Tela de Cadastro de Item
* [ ] Tela de Doação

---

## 🟢 FASE 5 — Integração Final

* [ ] Testar fluxo completo:

  * ONG cadastra item
  * Doador visualiza
  * Doador cria doação
* [ ] Corrigir bugs
* [ ] Ajustar README
* [ ] Preparar explicação dos padrões

---

Divisão por funcionalidades completas:

---

### 👤 Matheus

Responsável por:

* Cadastro de Usuário (back + front + banco)

---

### 👤 Alvaro

Responsável por:

* Cadastro/Listagem de Itens (back + front + banco)

---

### 👤 Thiago

Responsável por:

* Sistema de Doação + Facade

---
