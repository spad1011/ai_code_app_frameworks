package org.acme.ai_code_spsp;

import org.acme.ai_code_spsp.dto.PetNameBreedDTO;
import org.acme.ai_code_spsp.entity.Pet;
import org.acme.ai_code_spsp.entity.Household;
import org.acme.ai_code_spsp.repository.HouseholdRepository;
import org.acme.ai_code_spsp.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@WithMockUser("ADMIN")
@DataJpaTest
class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private HouseholdRepository householdRepository;

    @BeforeEach
    void setUp() {
        // Prepare a household for testing
        Household household = new Household("A12345", 3, 5, true);
        householdRepository.save(household);

        // Create pets associated with the household
        Pet pet1 = new Pet("Max", "Dog", "Golden Retriever", 4, household);
        Pet pet2 = new Pet("Bella", "Cat", "Siamese", 2, household);
        petRepository.save(pet1);
        petRepository.save(pet2);
    }

    @Test
    void shouldFindPetsByAnimalType() {
        List<Pet> dogs = petRepository.findByAnimalTypeIgnoreCase("Dog");
        assertThat(dogs).hasSize(1);
        assertThat(dogs.getFirst().getName()).isEqualTo("Max");
    }

    @Test
    void shouldFindPetsByBreedOrderByAge() {
        List<Pet> pets = petRepository.findByBreedOrderByAge("Golden Retriever");
        assertThat(pets).hasSize(1);
        assertThat(pets.getFirst().getName()).isEqualTo("Max");
    }

    @Test
    void shouldDeletePetsByNameIgnoreCase() {
        petRepository.deleteByNameIgnoreCase("bella");

        List<Pet> pets = petRepository.findAll();
        assertThat(pets).hasSize(1);
        assertThat(pets.getFirst().getName()).isEqualTo("Max");
    }

    @Test
    void shouldReturnPetNameAndBreedOnly() {
        List<PetNameBreedDTO> petNameBreedDTOs = petRepository.findAllNameAndBreed();
        assertThat(petNameBreedDTOs).hasSize(2);
        assertThat(petNameBreedDTOs.get(0).name()).isEqualTo("Max");
        assertThat(petNameBreedDTOs.get(1).breed()).isEqualTo("Siamese");
    }

    @Test
    void shouldReturnPetStatistics() {
        List<Object[]> stats = petRepository.findPetStatistics();
        assertThat(stats).hasSize(1);
        Object[] statsArray = stats.getFirst();
        assertThat(statsArray[0]).isEqualTo(3.0); // Average age
        assertThat(statsArray[1]).isEqualTo(4);   // Max age
        assertThat(statsArray[2]).isEqualTo(2L);   // Total count
    }
}
