package com.rpg.ad.d.services;

import com.rpg.ad.d.repository.CharacterRepository;
import com.rpg.ad.d.entities.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {
    @Autowired
    CharacterRepository characterRepository;
    public ResponseEntity<Character> getOneCharacter(Integer characterId) {
        Optional<Character> character = characterRepository.findById(characterId);
        if(character.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(character.get());
    }
    public ResponseEntity<List<Character>> getAllCharacters() {
        List<Character> characters = characterRepository.findAll();
        return ResponseEntity.ok(characters);
    }
    public ResponseEntity<Character> addCharacter(Character character) {
        characterRepository.save(character);
        return ResponseEntity.ok(character);
    }
    public ResponseEntity<Void> deleteCharacter(Integer characterId) {
        characterRepository.deleteById(characterId);
        return ResponseEntity.ok().build();
    }
    public ResponseEntity<Character> updateCharacter(Integer characterId, Character character) {
        Optional<Character> existingCharacter = characterRepository.findById(characterId);

        if(existingCharacter.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var characterToUpdate = existingCharacter.get();

        characterToUpdate.setName(character.getName());
        characterToUpdate.setHealth(character.getHealth());
        characterToUpdate.setStrength(character.getStrength());
        characterToUpdate.setDefense(character.getDefense());
        characterToUpdate.setAgility(character.getAgility());
        characterToUpdate.setDiceAmount(character.getDiceAmount());
        characterToUpdate.setDiceFaces(character.getDiceFaces());

        Character updated = characterRepository.save(characterToUpdate);

        return ResponseEntity.ok(updated);
    }
}
