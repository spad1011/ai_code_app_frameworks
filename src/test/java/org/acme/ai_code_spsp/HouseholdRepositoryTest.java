package org.acme.ai_code_spsp;

import lombok.extern.slf4j.Slf4j;
import org.acme.ai_code_spsp.entity.Household;
import org.acme.ai_code_spsp.entity.Pet;
import org.acme.ai_code_spsp.repository.HouseholdRepository;
import org.acme.ai_code_spsp.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@WithMockUser("ADMIN")
class HouseholdRepositoryTest {

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private PetRepository petRepository;

    @BeforeEach
    void setUp() {
        // Set up household
        Household household = new Household("A12345", 3, 5, true);
        householdRepository.save(household);

        // Add pets to the household
        Pet pet = new Pet("Max", "Dog", "Golden Retriever", 4, household);
        petRepository.save(pet);
    }

    @Test
    void shouldFindHouseholdsWithNoPets() {
        Household householdWithoutPets = new Household("B54321", 1, 3, false);
        householdRepository.save(householdWithoutPets);

        List<Household> households = householdRepository.findByPetsIsEmpty();
        assertThat(households).hasSize(1);
        assertThat(households.get(0).getEircode()).isEqualTo("B54321");
    }

    @Test
    void shouldFindOwnerOccupiedHouseholds() {
        List<Household> households = householdRepository.findByOwnerOccupied(true);
        assertThat(households).hasSize(1);
        assertThat(households.get(0).getEircode()).isEqualTo("A12345");
    }

    @Test
    void shouldFindHouseholdByIdWithPets() {
        Household foundHousehold = householdRepository.findById("A12345").orElse(null);
        log.info("found household: {}", foundHousehold);
        assertThat(foundHousehold).isNotNull();
        assertThat(foundHousehold.getPets()).hasSize(1);
    }

    @Test
    void shouldReturnHouseholdStatistics() {
        List<Object[]> stats = householdRepository.findHouseholdStatistics();
        assertThat(stats).hasSize(1);
        Object[] statsArray = stats.get(0);
        assertThat(statsArray[0]).isEqualTo(0L); // No empty houses
        assertThat(statsArray[1]).isEqualTo(0L); // No full houses
    }
}
