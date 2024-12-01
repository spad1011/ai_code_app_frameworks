package org.acme.ai_code_spsp;

import org.acme.ai_code_spsp.entity.Household;
import org.acme.ai_code_spsp.entity.Pet;
import org.acme.ai_code_spsp.exception.BadDataException;
import org.acme.ai_code_spsp.exception.NotFoundException;
import org.acme.ai_code_spsp.repository.PetRepository;
import org.acme.ai_code_spsp.service.PetService;
import org.acme.ai_code_spsp.service.impl.PetServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WithMockUser("ADMIN")
class PetServiceTest {

    private final PetRepository petRepository = mock(PetRepository.class);
    private final PetService petService = new PetServiceImpl(petRepository);

    @Test
    void shouldCreatePet() {
        Household household = new Household("D12AB01", 3, 5, true);
        Pet pet = new Pet("Bella", "Cat", "Siamese", 2, household);

        when(petRepository.save(pet)).thenReturn(pet);

        Pet createdPet = petService.createPet(pet);
        assertEquals(pet, createdPet);
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void shouldThrowBadDataExceptionWhenCreatingPetWithoutHousehold() {
        Pet pet = new Pet("Bella", "Cat", "Siamese", 2, null);

        BadDataException exception = assertThrows(BadDataException.class, () -> petService.createPet(pet));
        assertEquals("A pet must belong to a household.", exception.getMessage());
        verify(petRepository, never()).save(any());
    }

    @Test
    void shouldGetAllPets() {
        List<Pet> pets = Arrays.asList(
                new Pet("Bella", "Cat", "Siamese", 2, null),
                new Pet("Max", "Dog", "Labrador", 4, null)
        );

        when(petRepository.findAll()).thenReturn(pets);

        List<Pet> retrievedPets = petService.getAllPets();
        assertEquals(pets, retrievedPets);
        verify(petRepository, times(1)).findAll();
    }

    @Test
    void shouldGetPetById() {
        Pet pet = new Pet("Bella", "Cat", "Siamese", 2, null);
        when(petRepository.findById(1)).thenReturn(Optional.of(pet));

        Pet retrievedPet = petService.getPetById(1);
        assertEquals(pet, retrievedPet);
        verify(petRepository, times(1)).findById(1);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenPetByIdDoesNotExist() {
        when(petRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> petService.getPetById(1));
        assertEquals("Pet not found with ID: 1", exception.getMessage());
        verify(petRepository, times(1)).findById(1);
    }

    @Test
    void shouldUpdatePetDetails() {
        Household household = new Household("D12AB01", 3, 5, true);
        Pet existingPet = new Pet("Bella", "Cat", "Siamese", 2, household);
        Pet updatedPet = new Pet("Bella", "Cat", "Sphynx", 3, household);

        when(petRepository.findById(1)).thenReturn(Optional.of(existingPet));
        when(petRepository.save(existingPet)).thenReturn(existingPet);

        Pet result = petService.updatePet(1, updatedPet);
        assertEquals(updatedPet.getBreed(), result.getBreed());
        assertEquals(updatedPet.getAge(), result.getAge());
        verify(petRepository, times(1)).save(existingPet);
    }

    @Test
    void shouldDeletePetById() {
        petService.deletePetById(1);
        verify(petRepository, times(1)).deleteById(1);
    }

    @Test
    void shouldGetPetStatistics() {
        // Explicitly create the list with the correct type
        List<Object[]> stats = new ArrayList<>();
        stats.add(new Object[]{3.5, 10, 20L}); // Example data: avg age 3.5, max age 10, total count 20

        // Mock the repository call with the correct return type
        when(petRepository.findPetStatistics()).thenReturn(stats);

        // Call the service method
        List<Object[]> result = petService.getPetStatistics();

        // Validate the results
        assertNotNull(result);
        assertEquals(1, result.size());
        assertNotNull(result.getFirst()); // Ensure each item is an Object[]
        assertEquals(3.5, result.getFirst()[0]); // Average age
        assertEquals(10, result.getFirst()[1]); // Maximum age
        assertEquals(20L, result.getFirst()[2]); // Total count

        // Verify the repository interaction
        verify(petRepository, times(1)).findPetStatistics();
    }

}
