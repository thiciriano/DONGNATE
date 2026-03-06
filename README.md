# DONGNATE

Aplicativo mobile que conecta **ONGs que precisam de doações** com **pessoas que desejam ajudar**.

O sistema permite que organizações publiquem pedidos de itens necessários e que doadores encontrem essas necessidades e se comprometam a realizar doações.

O projeto foi desenvolvido como trabalho da disciplina **Padrões de Projeto**, integrando também conceitos de **UI/UX, desenvolvimento mobile e backend**.

---

# 🎯 Objetivo

Criar uma plataforma simples que facilite a conexão entre **ONGs e doadores**, permitindo que necessidades reais sejam atendidas de forma rápida e organizada.

A proposta do sistema é funcionar como um **mural digital de necessidades**, onde:

* ONGs publicam itens que precisam
* Doadores encontram essas necessidades
* Doadores podem se comprometer a realizar a doação

---

# 👥 Integrantes

* THIAGO HENRIQUE CIRIANO NEVES
* ALVARO LUANDREY DE SANTANA DE FREITAS
* MATHEUS DE OLIVEIRA MOTA

---

# 🧩 Funcionalidades Principais (MVP)

O projeto segue um **MVP minimalista**, contendo apenas as funcionalidades essenciais.

### Autenticação de usuários

* Cadastro e login
* Tipos de usuário:

  * ONG
  * Doador

### Mural de necessidades

* ONGs podem criar pedidos de doação
* Doadores podem visualizar pedidos

### Criação de pedidos

* Título
* Descrição
* Categoria do item
* ONG responsável

### Registro de doações

* Doadores podem clicar em **"Quero Doar"**
* O sistema registra o interesse na doação

---

# 📱 Telas do Aplicativo

O aplicativo possui **4 telas principais**:

1. **Login / Cadastro**
2. **Feed de necessidades (mural de pedidos)**
3. **Criar pedido de doação (ONG)**
4. **Minhas doações**

---

# 🏗 Arquitetura do Sistema

O projeto segue uma arquitetura baseada em **MVVM (Model-View-ViewModel)**.

Fluxo da aplicação:

UI (Jetpack Compose)
↓
ViewModel
↓
Repository
↓
Supabase Client
↓
Banco de Dados PostgreSQL

---

# 🧠 Padrões de Projeto Utilizados

Para atender aos requisitos da disciplina, foram utilizados **dois padrões de projeto da primeira unidade**.

---

## Singleton

O padrão **Singleton** foi utilizado para gerenciar a conexão com o backend (Supabase), garantindo que exista apenas **uma única instância do cliente de comunicação com a API** em todo o aplicativo.

Benefícios:

* evita múltiplas conexões com o backend
* centraliza a configuração da API
* melhora organização e reutilização do código

Exemplo de uso:

SupabaseManager responsável por fornecer uma única instância do cliente Supabase para toda a aplicação.

---

## Factory Method

O padrão **Factory Method** foi utilizado na criação de pedidos de doação.

Cada pedido pode pertencer a uma categoria diferente, como:

* Alimentos
* Roupas
* Higiene
* Móveis

A criação desses objetos é delegada a uma **Factory**, que decide qual tipo de pedido deve ser instanciado.

Benefícios:

* desacopla a criação de objetos
* facilita a adição de novas categorias
* evita repetição de código

---

# 🗄 Estrutura do Banco de Dados

O sistema utiliza **PostgreSQL (via Supabase)** com quatro tabelas principais.

### users

Armazena os usuários do sistema.

Campos principais:

* id
* name
* email
* role (ONG ou DOADOR)

---

### profiles

Informações adicionais do usuário.

Campos principais:

* id
* user_id
* phone
* organization_name

---

### requests

Pedidos de doação criados pelas ONGs.

Campos principais:

* id
* title
* description
* category
* ong_id

---

### donations

Registro de doadores interessados em ajudar.

Campos principais:

* id
* request_id
* donor_id
* status

---

# 🛠 Tecnologias Utilizadas

Frontend / Mobile

* Kotlin
* Jetpack Compose

Backend

* Supabase
* API REST

Banco de Dados

* PostgreSQL

Controle de Versão

* Git
* GitHub

---

# 🚀 Como Executar o Projeto

1. Clonar o repositório

git clone <link-do-repositorio>

2. Abrir o projeto no Android Studio

3. Configurar as credenciais do Supabase no arquivo de configuração

4. Executar o aplicativo em um emulador ou dispositivo físico

---

# 📊 Status do Projeto

Projeto em desenvolvimento para a **AV1 da disciplina de Padrões de Projeto**.

Nesta etapa foram implementadas as funcionalidades principais do sistema e os padrões de projeto exigidos.

---

# 📚 Disciplina

Padrões de Projeto
Professor: Victor Henrique dos Santos Oliveira

Curso: Análise e Desenvolvimento de Sistemas

