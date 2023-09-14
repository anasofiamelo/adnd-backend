package com.rpg.ad.d.dto;

import com.rpg.ad.d.enums.Player;
import lombok.Data;

@Data
public class AttackRequestDTO {
    private Player attacker;
    private Integer turn;
}
