package com.rpg.ad.d.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Turn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer turnNumber;
    @ManyToOne
    private Battle battle;
    private Integer userAttack;
    private Integer opponentDefense;
    private Integer userDamage;
    private Integer opponentAttack;
    private Integer userDefense;
    private Integer opponentDamage;
}
