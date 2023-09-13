package com.rpg.ad.d.repository;

import com.rpg.ad.d.entities.Battle;
import com.rpg.ad.d.entities.Turn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TurnRepository extends JpaRepository<Turn, Integer> {

    Optional<Turn> findByBattleAndTurnNumber(@Param("battle") Battle battle, @Param("turnNumber") int turnNumber);
}
