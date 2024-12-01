package org.acme.ai_code_spsp;

import org.acme.ai_code_spsp.entity.Household;
import org.acme.ai_code_spsp.entity.Pet;
import org.acme.ai_code_spsp.resolver.MutationResolver;
import org.acme.ai_code_spsp.resolver.QueryResolver;
import org.acme.ai_code_spsp.service.HouseholdService;
import org.acme.ai_code_spsp.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.mockito.Mockito.*;

@GraphQlTest({MutationResolver.class, QueryResolver.class})
class GraphQLTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private PetService petService;

    @MockBean
    private HouseholdService householdService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Mutation Tests

    @Test
    void createPetTest() {
        Pet pet = new Pet("Max", "Dog", "Labrador", 5, null);
        when(petService.createPet(any(Pet.class))).thenReturn(pet);

        String mutation = """
                mutation {
                    createPet(input: {
                        name: "Max",
                        animalType: "Dog",
                        breed: "Labrador",
                        age: 5
                    }) {
                        name
                        animalType
                        breed
                        age
                    }
                }
                """;

        graphQlTester.document(mutation)
                .execute()
                .path("createPet.name").entity(String.class).isEqualTo("Max")
                .path("createPet.animalType").entity(String.class).isEqualTo("Dog")
                .path("createPet.breed").entity(String.class).isEqualTo("Labrador")
                .path("createPet.age").entity(Integer.class).isEqualTo(5);

        verify(petService, times(1)).createPet(any(Pet.class));
    }

    @Test
    void updatePetTest() {
        Pet pet = new Pet("Bella", "Cat", "Siamese", 4, null);
        when(petService.updatePet(eq(1), any(Pet.class))).thenReturn(pet);

        String mutation = """
                mutation {
                    updatePet(id: 1, input: {
                        name: "Bella",
                        animalType: "Cat",
                        breed: "Siamese",
                        age: 4
                    }) {
                        name
                        animalType
                        breed
                        age
                    }
                }
                """;

        graphQlTester.document(mutation)
                .execute()
                .path("updatePet.name").entity(String.class).isEqualTo("Bella")
                .path("updatePet.animalType").entity(String.class).isEqualTo("Cat")
                .path("updatePet.breed").entity(String.class).isEqualTo("Siamese")
                .path("updatePet.age").entity(Integer.class).isEqualTo(4);

        verify(petService, times(1)).updatePet(eq(1), any(Pet.class));
    }

    @Test
    void deletePetTest() {
        doNothing().when(petService).deletePetById(1);

        String mutation = """
                mutation {
                    deletePet(id: 1)
                }
                """;

        graphQlTester.document(mutation)
                .execute()
                .path("deletePet").entity(Boolean.class).isEqualTo(true);

        verify(petService, times(1)).deletePetById(1);
    }

    @Test
    void createHouseholdTest() {
        Household household = new Household("E123", 2, 4, true);
        when(householdService.createHousehold(any(Household.class))).thenReturn(household);

        String mutation = """
                mutation {
                    createHousehold(input: {
                        eircode: "E123",
                        numberOfOccupants: 2,
                        maxNumberOfOccupants: 4,
                        ownerOccupied: true
                    }) {
                        eircode
                        numberOfOccupants
                        maxNumberOfOccupants
                        ownerOccupied
                    }
                }
                """;

        graphQlTester.document(mutation)
                .execute()
                .path("createHousehold.eircode").entity(String.class).isEqualTo("E123")
                .path("createHousehold.numberOfOccupants").entity(Integer.class).isEqualTo(2)
                .path("createHousehold.maxNumberOfOccupants").entity(Integer.class).isEqualTo(4)
                .path("createHousehold.ownerOccupied").entity(Boolean.class).isEqualTo(true);

        verify(householdService, times(1)).createHousehold(any(Household.class));
    }

    // Add similar tests for other household mutations and queries...
}
