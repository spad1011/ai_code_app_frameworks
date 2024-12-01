    package org.acme.ai_code_spsp.repository;

import org.acme.ai_code_spsp.dto.PetNameBreedDTO;
import org.acme.ai_code_spsp.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Integer> {

    // Delete Pets by Name
    void deleteByNameIgnoreCase(String name);

    // Find Pets by Animal Type
    List<Pet> findByAnimalTypeIgnoreCase(String animalType);

    // Find Pets by Breed ordered by age
    List<Pet> findByBreedOrderByAge(String breed);

    // Get Name and Breed Only (Point 9)
    @Query("SELECT new org.acme.ai_code_spsp.dto.PetNameBreedDTO(p.name, p.animalType, p.breed) FROM Pet p")
    List<PetNameBreedDTO> findAllNameAndBreed();

    // Get Pet Statistics
    @Query("SELECT AVG(p.age), MAX(p.age), COUNT(p) FROM Pet p")
    List<Object[]> findPetStatistics();
}
