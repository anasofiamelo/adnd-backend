package com.rpg.ad.d.controllers;

import com.rpg.ad.d.dto.AttackRequestDTO;
import com.rpg.ad.d.dto.CalculateDamageRequestDTO;
import com.rpg.ad.d.dto.DefenseRequestDTO;
import com.rpg.ad.d.dto.StartBattleDTO;
import com.rpg.ad.d.services.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("battle")
public class BattleController {
    @Autowired
    BattleService battleService;
    @PostMapping("start")
    public ResponseEntity<?> startBattle(@RequestBody StartBattleDTO newBattle) {
        return battleService.startBattle(newBattle);
    }
    @GetMapping("{battleId}/firstAttacker")
    public ResponseEntity<?> decideFirstAttacker(@PathVariable Integer battleId) {
        return battleService.decideFirstAttacker(battleId);
    }
    @PostMapping("{battleId}/attack")
    public ResponseEntity<?> attack(@PathVariable Integer battleId, @RequestBody AttackRequestDTO attackDTO) {
        return battleService.attack(battleId, attackDTO);
    }
    @PostMapping("{battleId}/defense")
    public ResponseEntity<?> defend(@PathVariable Integer battleId, @RequestBody DefenseRequestDTO defenseDTO) {
        return battleService.defend(battleId, defenseDTO);
    }
    @PostMapping("{battleId}/calculateDamage")
    public ResponseEntity<?> calculateDamage(@PathVariable Integer battleId, @RequestBody CalculateDamageRequestDTO calculateDamageDTO) {
        return battleService.calculateDamage(battleId, calculateDamageDTO);
    }
}
