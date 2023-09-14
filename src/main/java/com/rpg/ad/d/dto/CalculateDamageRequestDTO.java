package com.rpg.ad.d.dto;

import com.rpg.ad.d.enums.Player;
import lombok.Data;

@Data
public class CalculateDamageRequestDTO {
    private Player attacker;
    private int turn;
}
