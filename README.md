# DongNate

Plataforma de conexão entre doadores e ONGs.

## Integrantes
- Thiago Henrique Ciriano Neves
- Alvaro Luandrey de Santana de Freitas
- Matheus de Oliveira Mota

## Disciplina
Padrões de Projeto - AV1

## Padrões Aplicados

### Repository Pattern
Cada entidade (User, Ong, Request, Interest) tem uma interface de repositório e uma implementação concreta que lida com persistência em JSON local. Isso separa a lógica de negócio do acesso a dados.

### Factory Method
A classe `RepositoryFactory` define o contrato para criação de repositórios. `LocalRepositoryFactory` implementa a criação concreta. `AppRepositories` é o ponto central de acesso usando a factory.

## Banco de Dados
JSON local simulando banco relacional (dongnate_db.json em filesDir). Sem dependências externas de banco — o arquivo é lido ao iniciar e salvo após cada operação.

## Autenticação
SHA-256 para hash de senha. Session object para manter usuário logado em memória.

## Como rodar
1. Abrir no Android Studio
2. Sync Gradle
3. Rodar em emulador ou dispositivo (minSdk 26)

## Fluxo
- Cadastro como Doador → acessa pedidos, manifesta interesse
- Cadastro como ONG → cria perfil da organização, publica pedidos
- Login → redireciona para Home
