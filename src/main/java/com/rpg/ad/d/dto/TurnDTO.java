package com.rpg.ad.d.dto;

import lombok.Data;

@Data
public class TurnDTO {
    private Integer id;
    private Integer opponent_attack;
    private Integer opponent_damage;
    private Integer opponent_defense;
    private Integer turn_number;
    private Integer user_attack;
    private Integer user_damage;
    private Integer user_defense;
    private Integer battle_id;
}
