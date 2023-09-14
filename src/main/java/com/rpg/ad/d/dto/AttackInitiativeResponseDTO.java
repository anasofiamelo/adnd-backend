package com.rpg.ad.d.dto;

import com.rpg.ad.d.enums.Player;
import lombok.Data;

@Data
public class AttackInitiativeResponseDTO {
    private Player firstAttacker;
    private int userInitiative;
    private int opponentInitiative;
}
