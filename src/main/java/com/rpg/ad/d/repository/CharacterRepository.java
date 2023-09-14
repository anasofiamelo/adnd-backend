package com.rpg.ad.d.repository;

import com.rpg.ad.d.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer> {
    @Query(value = "SELECT * FROM character WHERE type = 'monstro' ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Character findRandomMonster();
}
