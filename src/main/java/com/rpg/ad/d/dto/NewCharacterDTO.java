package com.rpg.ad.d.dto;

import com.rpg.ad.d.enums.CharacterType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewCharacterDTO {
    @Valid

    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "name is required")
    private int health;
    @NotNull(message = "name is required")
    private int strength;
    @NotNull(message = "name is required")
    private int defense;
    @NotNull(message = "name is required")
    private int agility;
    @NotNull(message = "name is required")
    private CharacterType type;
    @NotNull(message = "name is required")
    private int diceAmount;
    @NotNull(message = "name is required")
    private int diceFaces;
}
