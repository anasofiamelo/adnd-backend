package com.rpg.ad.d.entities;

import com.rpg.ad.d.enums.CharacterType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "health is required")
    private Integer health;
    @NotNull(message = "strength is required")
    private Integer strength;
    @NotNull(message = "defense is required")
    private Integer defense;
    @NotNull(message = "agility is required")
    private Integer agility;
    @NotNull(message = "type must be HERO or MONSTER")
    @Enumerated(EnumType.STRING)
    private CharacterType type;
    @NotNull(message = "diceAmount is required")
    private Integer diceAmount;
    @NotNull(message = "diceFaces is required")
    private Integer diceFaces;
}
