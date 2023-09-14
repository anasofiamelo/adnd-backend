package com.rpg.ad.d.repository;

import com.rpg.ad.d.entities.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BattleRepository extends JpaRepository<Battle, Integer> {
    @Query(value = "SELECT battle.id, battle.user_character_id, battle.opponent_character_id, battle.winner, json_agg(turn.*) AS turns_data " +
            "FROM battle " +
            "LEFT JOIN turn ON battle.id = turn.battle_id " +
            "WHERE battle.id = :battleId " +
            "GROUP BY battle.id", nativeQuery = true)
    List<Object[]> getBattleHistory(int battleId);
}
