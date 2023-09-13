package com.rpg.ad.d.repository;

import com.rpg.ad.d.entities.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattleRepository extends JpaRepository<Battle, Integer> {
}
