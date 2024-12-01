package org.acme.ai_code_spsp.controller;

import org.acme.ai_code_spsp.dto.PetNameBreedDTO;
import org.acme.ai_code_spsp.entity.Pet;
import org.acme.ai_code_spsp.service.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<Pet> createPet(@RequestBody Pet pet) {
        return ResponseEntity.ok(petService.createPet(pet));
    }

    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable int id) {
        return ResponseEntity.ok(petService.getPetById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable int id, @RequestBody Pet pet) {
        return ResponseEntity.ok(petService.updatePet(id, pet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetById(@PathVariable int id) {
        petService.deletePetById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<Void> deletePetsByName(@PathVariable String name) {
        petService.deletePetsByName(name);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/animalType/{type}")
    public ResponseEntity<List<Pet>> findPetsByAnimalType(@PathVariable String type) {
        return ResponseEntity.ok(petService.findPetsByAnimalType(type));
    }

    @GetMapping("/breed/{breed}")
    public ResponseEntity<List<Pet>> findPetsByBreed(@PathVariable String breed) {
        return ResponseEntity.ok(petService.findPetsByBreed(breed));
    }

    @GetMapping("/summary")
    public ResponseEntity<List<PetNameBreedDTO>> getPetNameAndBreed() {
        return ResponseEntity.ok(petService.getPetSummaries()); //used wrong method again. just like in service when building on top of repository
    }

    @GetMapping("/statistics")
    public ResponseEntity<String> getPetStatistics() {
        return ResponseEntity.ok(petService.getPetStatistics().toString());
    }
}
