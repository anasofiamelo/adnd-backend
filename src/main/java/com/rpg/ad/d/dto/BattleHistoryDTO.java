package com.rpg.ad.d.dto;

import com.rpg.ad.d.enums.Player;
import lombok.Data;

import java.util.List;

@Data
public class BattleHistoryDTO {
    private Integer id;
    private Integer userId;
    private Integer opponentId;
    private String winner;
    private TurnDTO[] turnData;
}
