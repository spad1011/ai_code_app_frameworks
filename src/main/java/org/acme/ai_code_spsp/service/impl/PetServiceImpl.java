package org.acme.ai_code_spsp.service.impl;

import org.acme.ai_code_spsp.dto.PetNameBreedDTO;
import org.acme.ai_code_spsp.entity.Pet;
import org.acme.ai_code_spsp.exception.BadDataException;
import org.acme.ai_code_spsp.exception.NotFoundException;
import org.acme.ai_code_spsp.repository.PetRepository;
import org.acme.ai_code_spsp.service.PetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet createPet(Pet pet) {
        if (pet.getHousehold() == null) {
            throw new BadDataException("A pet must belong to a household.");
        }
        return petRepository.save(pet);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Pet getPetById(int id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet not found with ID: " + id));
    }

    @Override
    public Pet updatePet(int id, Pet updatedPet) {
        Pet existingPet = getPetById(id);

        if (updatedPet.getName() == null || updatedPet.getAnimalType() == null || updatedPet.getBreed() == null) {
            throw new BadDataException("Pet details cannot be null.");
        }

        existingPet.setName(updatedPet.getName());
        existingPet.setAnimalType(updatedPet.getAnimalType());
        existingPet.setBreed(updatedPet.getBreed());
        existingPet.setAge(updatedPet.getAge());

        return petRepository.save(existingPet);
    }

    @Override
    public void deletePetById(int id) {
        petRepository.deleteById(id);
    }

    @Override
    public void deletePetsByName(String name) {
        petRepository.deleteByNameIgnoreCase(name);
    }

    @Override
    public List<Pet> findPetsByAnimalType(String animalType) {
        return petRepository.findByAnimalTypeIgnoreCase(animalType);
    }

    @Override
    public List<Pet> findPetsByBreed(String breed) {
        return petRepository.findByBreedOrderByAge(breed);
    }

    @Override
    public List<PetNameBreedDTO> getPetSummaries() {
        return petRepository.findAllNameAndBreed();
    }

    @Override
    public List<Object[]> getPetStatistics() {
        return petRepository.findPetStatistics();
    }
}
