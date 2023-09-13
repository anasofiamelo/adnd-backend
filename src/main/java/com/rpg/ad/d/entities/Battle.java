package com.rpg.ad.d.entities;

import com.rpg.ad.d.enums.Player;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class Battle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Character userCharacter;
    @ManyToOne
    private Character opponentCharacter;
    @Enumerated(EnumType.STRING)
    private Player firstAttacker;
    private Integer userLife;
    private Integer opponentLife;
    @OneToMany(mappedBy = "battle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Turn> turns;
    private Player winner;
}
