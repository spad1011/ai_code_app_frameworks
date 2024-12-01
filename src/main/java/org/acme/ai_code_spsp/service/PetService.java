package org.acme.ai_code_spsp.service;

import org.acme.ai_code_spsp.dto.PetNameBreedDTO;
import org.acme.ai_code_spsp.entity.Pet;

import java.util.List;

public interface PetService {
    Pet createPet(Pet pet);

    List<Pet> getAllPets();

    Pet getPetById(int id);

    Pet updatePet(int id, Pet updatedPet);

    void deletePetById(int id);

    void deletePetsByName(String name);

    List<Pet> findPetsByAnimalType(String animalType);

    List<Pet> findPetsByBreed(String breed);

    List<PetNameBreedDTO> getPetSummaries();

    List<Object[]> getPetStatistics();
}
