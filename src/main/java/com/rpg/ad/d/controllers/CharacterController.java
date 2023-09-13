package com.rpg.ad.d.controllers;

import com.rpg.ad.d.dto.NewCharacterDTO;
import com.rpg.ad.d.entities.Character;
import com.rpg.ad.d.services.CharacterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("character")
@Validated
public class CharacterController {
    @Autowired
    CharacterService characterService;
    @GetMapping("")
    public ResponseEntity<List<Character>> getAllCharacters() {
        return characterService.getAllCharacters();
    }
    @GetMapping("{characterId}")
    public ResponseEntity<Character> getCharacter(@PathVariable Integer characterId) {
        return characterService.getOneCharacter(characterId);
    }
    @PostMapping("")
    public ResponseEntity<Character> addCharacter(@Valid @RequestBody Character character) {
        return characterService.addCharacter(character);
    }
    @DeleteMapping("{characterId}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Integer characterId) {
        return characterService.deleteCharacter(characterId);
    }
    @PatchMapping("{characterId}")
    public ResponseEntity<Character> editCharacter(@PathVariable Integer characterId, @RequestBody Character character) {
        return characterService.updateCharacter(characterId, character);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
