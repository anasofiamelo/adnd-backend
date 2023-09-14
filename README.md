# RPG Battle API
Bem-vindo (a) à RPG Battle API (AD&D), um sistema que permite aos jogadores participar de 
batalhas épicas em estilo de RPG (Role PLaying Game); Este projeto foi desenvolvido em 
Java com Spring Boot e utiliza banco de dados Postgres para armazenar informações sobre 
personagens e batalhas. 

## Funcionalidades

- Escolha o seu personagem favorito (herói ou monstro) para duelar em turnos.
- Realize batalhas com iniciativas aleatórias.
- Calcule o dano causado com base na força, defesa e agilidade do personagem.
- Acompanhe o histórico de batalhas e detalhes de cada turno.

## Requisitos

- Java 8 ou superior
- PostgresSQL

## Configurações do Banco de Dados

- Certifique-se de ter um banco de dados Postgres configurado e atualize as informações
de conexão no arquivo `application.properties` do projeto.

## Executando a Aplicação
 
Você pode executar a aplicação de duas maneiras:

### 1. Usando o Maven (linha de comando): `mvn spring-boot:run` 

### 2. Importanto o projeto em sua IDE

Importe o projeto em sua IDE preferida (por exemplo, IntelliJ ou Eclipse) e execute-o
a partir daí.

## Endpoints da API

- `/characters`: CRUD (Create, Read, Update, Delete) para personagens.
- `/battles/start`: Inicia uma nova batalha entre o usuário e o oponente.
- `/battles/{battleId}/attack`: Realiza um ataque em uma batalha.
- `/battles/{battleId}/defense`: Realiza uma defesa em uma batalha.
- `/battles/{battleId}/calculateDamage`: Cálcula o dano final depois das duas ações de um turno de uma batalha.
- `/battles/{battleId}/history`: Retorna o histórico de uma batalha específica.

## Exemplo de uso

Você pode utilizar um cliente HTTP (por exemplo, Postman ou cURL) para acessar os 
endpoints da API e testar as funcionalidades.

## Exemplo de requisições para iniciar uma batalha

`POST /battles/start
{
"userCharacterId": 1,
"opponentCharacterId": 2
}
`

## Estrutura de Dados

A aplicação utiliza uma estrutura de dados simples para representar personagens, batalhas e turnos. Certifique-se de entender a estrutura antes de usar a API.

## Contribuição

Sinta-se à vontade para contribuir com melhorias ou correções para este projeto. Basta fazer um fork e enviar uma pull request.

