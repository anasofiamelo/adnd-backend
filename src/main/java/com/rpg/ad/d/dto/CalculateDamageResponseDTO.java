package com.rpg.ad.d.dto;

import com.rpg.ad.d.enums.Player;
import lombok.Data;

@Data
public class CalculateDamageResponseDTO {
    private int damageDealt;
    private Player winner;
}
