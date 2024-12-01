package org.acme.ai_code_spsp;

import org.acme.ai_code_spsp.controller.PetController;
import org.acme.ai_code_spsp.dto.PetNameBreedDTO;
import org.acme.ai_code_spsp.entity.Pet;
import org.acme.ai_code_spsp.service.PetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//tests never imported correctly. Classes were never imported
@WebMvcTest(PetController.class)
@WithMockUser("ADMIN")
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    @Test
    void shouldCreatePet() throws Exception {
        Pet pet = new Pet(1, "Bella", "Cat", "Siamese", 2, null);

        when(petService.createPet(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name": "Bella",
                            "animalType": "Cat",
                            "breed": "Siamese",
                            "age": 2
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bella"));

        verify(petService, times(1)).createPet(any(Pet.class));
    }

    @Test
    void shouldGetAllPets() throws Exception {
        List<Pet> pets = List.of(new Pet(1, "Bella", "Cat", "Siamese", 2, null));

        when(petService.getAllPets()).thenReturn(pets);

        mockMvc.perform(get("/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bella"));

        verify(petService, times(1)).getAllPets();
    }

    @Test
    void shouldGetPetById() throws Exception {
        Pet pet = new Pet(1, "Bella", "Cat", "Siamese", 2, null);

        when(petService.getPetById(1)).thenReturn(pet);

        mockMvc.perform(get("/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bella"));

        verify(petService, times(1)).getPetById(1);
    }

    //used wrong url-template(/pets/name-breed). really can't work with the petSummary method
    @Test
    void shouldGetNameAndBreed() throws Exception {
        List<PetNameBreedDTO> pets = List.of(new PetNameBreedDTO("Bella", "Cat", "Siamese"));
        //wrong named input method again.
        when(petService.getPetSummaries()).thenReturn(pets);

        mockMvc.perform(get("/pets/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bella"));

        verify(petService, times(1)).getPetSummaries();
    }

    @Test
    void shouldGetPetStatistics() throws Exception {
        List<Object[]> stats = new ArrayList<>();
        stats.add(new Object[]{3.5, 10, 20L});

        when(petService.getPetStatistics()).thenReturn(stats);

        mockMvc.perform(get("/pets/statistics"))
                .andExpect(status().isOk());

        verify(petService, times(1)).getPetStatistics();
    }
}
