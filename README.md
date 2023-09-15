# RPG Battle API
Bem-vindo (a) à RPG Battle API (AD&D), um sistema que permite aos jogadores participar de 
batalhas épicas em estilo de RPG (Role PLaying Game); Este projeto foi desenvolvido em 
Java com Spring Boot e utiliza banco de dados Postgres para armazenar informações sobre 
personagens e batalhas. 

## Funcionalidades

- Escolha o seu personagem favorito (herói ou monstro) para duelar em turnos.
- Escolha seu oponente (deve ser um monstro) ou, se preferir, o jogo escolherá por você.
- Realize batalhas com iniciativas aleatórias.
- Calcule o dano causado com base na força, defesa e agilidade do personagem.
- Acompanhe o histórico de batalhas e detalhes de cada turno.

## Requisitos

- Java 17
- Spring Boot
- PostgresSQL

## Configurações do Banco de Dados

- Certifique-se de ter um banco de dados Postgres configurado e atualize as informações
de conexão no arquivo `application.properties` seguindo as instruções do arquivo de exemplo do projeto.

## Executando a Aplicação
 
Você pode executar a aplicação de duas maneiras:

### 1. Usando o Maven (linha de comando): `mvn spring-boot:run` 

### 2. Importanto o projeto em sua IDE

Importe o projeto em sua IDE preferida (por exemplo, IntelliJ ou Eclipse) e execute-o
a partir daí.

## Endpoints da API

### Character
- `POST /character`: Adiciona um novo personagem ao jogo.
~~~
Recebe os dados do novo personagem.
obs: A propriedade "type" só pode ser preenchida como "HERO" ou "MONSTER".

body: {
    "name": "Katarina",
    "health": 20,
    "strength": 15,
    "defense": 10,
    "agility": 10,
    "diceAmount": 1,
    "diceFaces": 12,
    "type": "HERO"
}
~~~

- `GET /character`: Retorna uma lista dos personagens existentes no jogo. 

- `GET /character/{characterId}`: Retorna um personagem específico.

- `PATCH /character/{characterId}`: Atualiza um personagem específico com as informações a serem atualizadas.

- `DELETE /character/{characterId}`: Deleta um personagem específico.

### Battle

- `POST /battle/start`: Inicia uma nova batalha entre o usuário e o oponente.
~~~
Recebe os ID's dos personagens do usuário e do oponente.
obs: Não é obrigatório passar o id do personagem do oponente. Se for o caso, um monstro 
aleatório será escolhido para o mesmo.

body: {
    "userCharacterId": 1,
    "opponentCharacterId": 3
}
~~~

- `POST /battle/{battleId}/attack`: Realiza um ataque em uma batalha.
~~~
Recebe o Player que está atacando e o número do turno.

body: {
    "attacker": "USER",
    "turn": 1
}
~~~
- `POST /battle/{battleId}/defense`: Realiza uma defesa em uma batalha.
~~~
Recebe o Player que está defendendo e o número do turno.

body: {
    "defender": "USER",
    "turn": 1
}
~~~
- `POST /battle/{battleId}/calculateDamage`: Cálcula o dano final depois das duas ações de um turno de uma batalha.
~~~
Recebe o Player que está atacando e o número do turno.

body: {
    "attacker": "USER",
    "turn": 1
}
~~~
- `GET /battle/{battleId}/history`: Retorna o histórico de uma batalha específica.

## Exemplo de uso

Você pode utilizar um cliente HTTP (por exemplo, Postman ou cURL) para acessar os 
endpoints da API e testar as funcionalidades.

## Contribuição

Sinta-se à vontade para contribuir com melhorias ou correções para este projeto. Basta fazer um fork e enviar uma pull request.

## Sobre o Projeto

Este projeto foi desenvolvido por alguém que está aprendendo Java e programação orientada a objetos pela primeira vez. Como resultado, pode haver áreas do código que não seguem as melhores práticas ou que poderiam ser otimizadas de maneira mais eficiente. No entanto, estou comprometido em aprender e aprimorar minhas habilidades à medida que avanço no desenvolvimento deste projeto.

Se você tiver sugestões, correções ou feedback construtivo, fique à vontade para contribuir ou entrar em contato. Estou ansioso para melhorar e expandir minhas habilidades de programação por meio deste projeto!