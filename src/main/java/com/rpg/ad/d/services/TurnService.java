package com.rpg.ad.d.services;

import com.rpg.ad.d.entities.Battle;
import com.rpg.ad.d.entities.Turn;
import com.rpg.ad.d.repository.TurnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurnService {
    @Autowired
    TurnRepository turnRepository;
    public boolean isTurnComplete(Turn turn) {
        return turn.getUserAttack() != null && turn.getUserDefense() != null &&turn.getUserDamage() != null &&
                turn.getOpponentAttack() != null && turn.getOpponentDefense() != null && turn.getOpponentDamage() != null;
    }
    public Turn createTurn(Battle battle, Turn lastTurn ) {
        Turn newTurn = new Turn();
        int turnNumber;
        if(lastTurn != null) {
            turnNumber = lastTurn.getTurnNumber() + 1;
        } else {
            turnNumber = 1;
        }
        newTurn.setTurnNumber(turnNumber);
        newTurn.setBattle(battle);
        return turnRepository.save(newTurn);
    }
}
