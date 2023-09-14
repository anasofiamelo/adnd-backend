package com.rpg.ad.d.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpg.ad.d.dto.*;
import com.rpg.ad.d.entities.Battle;
import com.rpg.ad.d.entities.Character;
import com.rpg.ad.d.entities.Turn;
import com.rpg.ad.d.enums.Player;
import com.rpg.ad.d.repository.BattleRepository;
import com.rpg.ad.d.repository.CharacterRepository;
import com.rpg.ad.d.repository.TurnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Service
public class BattleService {
    @Autowired
    BattleRepository battleRepository;
    @Autowired
    CharacterRepository characterRepository;
    @Autowired
    TurnRepository turnRepository;
    @Autowired
    TurnService turnService;
    @Autowired
    ObjectMapper objectMapper;
    public ResponseEntity<?> startBattle(StartBattleDTO battleDTO) {
        Integer userCharacterId = battleDTO.userCharacterId();
        Integer opponentCharacterId = battleDTO.opponentCharacterId();

        if (userCharacterId == null || opponentCharacterId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Character's ID was not provided");
        }

        var userCharacter = characterRepository.findById(userCharacterId);
        if(userCharacter.isEmpty()) {
            var errorMessage = "Character with ID of " + userCharacterId + " was not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        var opponentCharacter = characterRepository.findById(opponentCharacterId);
        if(opponentCharacter.isEmpty()) {
            var errorMessage = "Character with ID of " + opponentCharacterId + " was not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        var userCharacterData = userCharacter.get();
        var opponentCharacterData = opponentCharacter.get();

        Battle battle = new Battle();
        battle.setUserCharacter(userCharacterData);
        battle.setOpponentCharacter(opponentCharacterData);
        battle.setUserLife(userCharacterData.getHealth());
        battle.setOpponentLife(opponentCharacterData.getHealth());

        var newBattle = battleRepository.save(battle);

        Turn turn = new Turn();
        turn.setBattle(newBattle);
        turn.setTurnNumber(1);
        turnRepository.save(turn);

        return ResponseEntity.ok(newBattle);
    }
    public ResponseEntity<?> decideFirstAttacker(Integer battleId) {
        var battleData = battleRepository.findById(battleId);
        if(battleData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Battle not found");
        }

        Battle battle = battleData.get();
        if(battle.getFirstAttacker() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("First attacker already determined");
        }

        var userIniciative = this.rollDice(1, 20);
        var opponentIniciative = this.rollDice(1, 20);

        Player firstAttacker;

        if(userIniciative > opponentIniciative) {
            firstAttacker = Player.USER;
        } else {
            firstAttacker = Player.OPPONENT;
        }

        battle.setFirstAttacker(firstAttacker);
        battleRepository.save(battle);

        AttackInitiativeResponseDTO responseDTO = new AttackInitiativeResponseDTO();
        responseDTO.setFirstAttacker(firstAttacker);
        responseDTO.setUserInitiative(userIniciative);
        responseDTO.setOpponentInitiative(opponentIniciative);

        return ResponseEntity.ok(responseDTO);
    }
    public ResponseEntity<?> attack(Integer battleId, AttackRequestDTO attackDTO) {
        var battleData = battleRepository.findById(battleId);
        if(battleData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Battle not found");
        }
        Battle battle = battleData.get();
        if(battle.getWinner() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This battle has already ended!");
        }

        Optional<Turn> existingTurn = turnRepository.findByBattleAndTurnNumber(battle, attackDTO.getTurn());
        if(existingTurn.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This turn has not started yet.");
        }
        Turn turn = existingTurn.get();
        if(turnService.isTurnComplete(turn)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This turn has already ended!");
        }

        Character characterData;
        var attacker = attackDTO.getAttacker();
        if(attacker == Player.USER) {
            characterData = battle.getUserCharacter();
        } else {
            characterData = battle.getOpponentCharacter();
        }

        int attackValue = this.rollDice(characterData.getDiceAmount(), characterData.getDiceFaces());
        int attackDamage = attackValue + characterData.getAgility() + characterData.getStrength();

        var conflictErrorResponse = ResponseEntity.status(HttpStatus.CONFLICT).body("Wait for your opponent's turn!");

        if(attacker == battle.getFirstAttacker()) {
            if(attacker == Player.USER && turn.getUserAttack() == null) {
                turn.setUserAttack(attackDamage);
            } else if (attacker == Player.OPPONENT && turn.getOpponentAttack() == null) {
                turn.setOpponentAttack(attackDamage);
            } else {
                return conflictErrorResponse;
            }
        } else {
            if(attacker == Player.USER && turn.getOpponentAttack() != null && turn.getUserAttack() == null) {
                turn.setUserAttack(attackDamage);
            } else if (attacker == Player.OPPONENT && turn.getUserAttack() != null && turn.getOpponentAttack() == null) {
                turn.setOpponentAttack((attackDamage));
            } else {
                return conflictErrorResponse;
            }
        }

        turnRepository.save(turn);

        AttackResponseDTO responseDTO = new AttackResponseDTO();
        responseDTO.setAttackDamage(attackDamage);
        return ResponseEntity.ok(responseDTO);
    }
    public ResponseEntity<?> defend(Integer battleId, DefenseRequestDTO defendDTO) {
        var battleData = battleRepository.findById(battleId);
        if(battleData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Battle not found");
        }
        Battle battle = battleData.get();
        if(battle.getWinner() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This battle has already ended!");
        }

        Optional<Turn> existingTurn = turnRepository.findByBattleAndTurnNumber(battle, defendDTO.getTurn());
        if(existingTurn.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This turn has not started yet.");
        }
        Turn turn = existingTurn.get();
        if(turnService.isTurnComplete(turn)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This turn has already ended!");
        }

        Character characterData;
        var defender = defendDTO.getDefenser();
        if(defender == Player.USER) {
            characterData = battle.getUserCharacter();
        } else {
            characterData = battle.getOpponentCharacter();
        }

        int defenseValue = this.rollDice(characterData.getDiceAmount(), characterData.getDiceFaces());
        int defenseFinalValue = defenseValue + characterData.getAgility() + characterData.getStrength();

        var conflictErrorResponse = ResponseEntity.status(HttpStatus.CONFLICT).body("Wait for your opponent's turn!");
        var isFirstDefender = defender != battle.getFirstAttacker();

        if(isFirstDefender) {
            if(defender == Player.USER && turn.getUserDefense() == null) {
                turn.setUserDefense(defenseFinalValue);
            } else if (defender == Player.OPPONENT && turn.getOpponentDefense() == null) {
                turn.setOpponentDefense(defenseFinalValue);
            } else {
                return conflictErrorResponse;
            }
        } else {
            if(defender == Player.USER && turn.getOpponentDefense() != null && turn.getUserDefense() == null) {
                turn.setUserDefense(defenseFinalValue);
            } else if (defender == Player.OPPONENT && turn.getUserDefense() != null && turn.getOpponentDefense() == null) {
                turn.setOpponentDefense((defenseFinalValue));
            } else {
                return conflictErrorResponse;
            }
        }

        turnRepository.save(turn);

        DefenseResponseDTO responseDTO = new DefenseResponseDTO();
        responseDTO.setDefenseValue(defenseFinalValue);
        return ResponseEntity.ok(responseDTO);
    }
    public ResponseEntity<?> calculateDamage(Integer battleId, CalculateDamageRequestDTO calculateDamageDTO) {
        var battleData = battleRepository.findById(battleId);
        if(battleData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Battle not found");
        }
        Battle battle = battleData.get();
        if(battle.getWinner() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This battle has already ended!");
        }

        Optional<Turn> existingTurn = turnRepository.findByBattleAndTurnNumber(battle, calculateDamageDTO.getTurn());
        if (existingTurn.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This turn has not started yet");
        }
        Turn turn = existingTurn.get();
        if(turnService.isTurnComplete(turn)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This turn has already ended!");
        }

        var attacker = calculateDamageDTO.getAttacker();
        int damage;
        if(attacker == Player.USER && turn.getUserAttack() != null && turn.getOpponentDefense() != null) {
            int total = turn.getUserAttack() - turn.getOpponentDefense();
            damage = Math.max(total, 0);
            turn.setUserDamage(damage);

            battle.setOpponentLife(battle.getOpponentLife() - damage);
        } else if (attacker == Player.OPPONENT && turn.getOpponentAttack() != null && turn.getUserDefense() != null) {
            int total = turn.getOpponentAttack() - turn.getUserDefense();
            damage = Math.max(total, 0);
            turn.setOpponentDamage(damage);

            battle.setUserLife(battle.getUserLife() - damage);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing player's action");
        }

        if(turnService.isTurnComplete(turn)) {
            Turn newTurn = turnService.createTurn(battle, turn);
            turnRepository.save(newTurn);
        }

        if(battle.getUserLife() <= 0 ) {
            battle.setWinner(Player.OPPONENT);
        }

        if (battle.getOpponentLife() <= 0) {
            battle.setWinner(Player.USER);
        }

        battleRepository.save(battle);
        turnRepository.save(turn);

        CalculateDamageResponseDTO responseDTO = new CalculateDamageResponseDTO();
        responseDTO.setDamageDealt(damage);
        if(battle.getWinner() != null) {
            responseDTO.setWinner(battle.getWinner());
        }
        return ResponseEntity.ok(responseDTO);
    }
    public ResponseEntity<?> getHistory(Integer battleId) {
        var battleData = battleRepository.findById(battleId);
        if(battleData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Battle not found");
        }

        var rawBattleResults = battleRepository.getBattleHistory(battleId);
        var processedResults = new ArrayList<>();

        for(Object[] rawResult : rawBattleResults) {
            BattleHistoryDTO battleHistoryDTO = new BattleHistoryDTO();
            Integer id = (Integer) rawResult[0];
            Integer userId = (Integer) rawResult[1];
            Integer opponentId = (Integer) rawResult[2];
            String winner = (String) rawResult[3];
            String turnsDataJson = (String) rawResult[4];

            try {
                TurnDTO[] turnsData = objectMapper.readValue(turnsDataJson, TurnDTO[].class);
                battleHistoryDTO.setId(id);
                battleHistoryDTO.setUserId(userId);
                battleHistoryDTO.setOpponentId(opponentId);
                battleHistoryDTO.setWinner(winner);
                battleHistoryDTO.setTurnData(turnsData);

                processedResults.add(battleHistoryDTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok(processedResults);
    }
    public int rollDice(int numberOfDice, int numberOfFaces) {
        Random random = new Random();
        int total = 0;
        for (int i = 0; i < numberOfDice; i++) {
            total += random.nextInt(numberOfFaces) + 1;
        }
        return total;
    }
}
