package com.rpg.ad.d.dto;

import com.rpg.ad.d.enums.Player;
import lombok.Data;

@Data
public class DefenseRequestDTO {
    private Player defenser;
    private Integer turn;
}
