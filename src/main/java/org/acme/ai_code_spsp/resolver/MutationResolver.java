package org.acme.ai_code_spsp.resolver;

import lombok.AllArgsConstructor;
import org.acme.ai_code_spsp.dto.CreatePetInput;
import org.acme.ai_code_spsp.dto.UpdatePetInput;
import org.acme.ai_code_spsp.dto.CreateHouseholdInput;
import org.acme.ai_code_spsp.dto.UpdateHouseholdInput;
import org.acme.ai_code_spsp.entity.Household;
import org.acme.ai_code_spsp.entity.Pet;
import org.acme.ai_code_spsp.service.HouseholdService;
import org.acme.ai_code_spsp.service.PetService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor //was not included by AI. Used field injection
public class MutationResolver {

    private PetService petService;

    private HouseholdService householdService;

    // Pet Mutations
    @MutationMapping
    public Pet createPet(@Argument CreatePetInput input) {
        Pet pet = new Pet(
                input.name(),
                input.animalType(),
                input.breed(),
                input.age(),
                input.householdEircode() != null
                        ? householdService.getHouseholdByIdWithoutPets(input.householdEircode())
                        : null
        );
        return petService.createPet(pet);
    }

    @MutationMapping
    public Pet updatePet(@Argument int id, @Argument UpdatePetInput input) {
        Pet pet = new Pet(
                input.name(),
                input.animalType(),
                input.breed(),
                input.age() ,
                input.householdEircode() != null
                        ? householdService.getHouseholdByIdWithoutPets(input.householdEircode())
                        : null
        );
        return petService.updatePet(id, pet);
    }

    @MutationMapping
    public boolean deletePet(@Argument int id) {
        petService.deletePetById(id);
        return true;
    }

    // Household Mutations
    @MutationMapping
    public Household createHousehold(@Argument CreateHouseholdInput input) {
        Household household = new Household(
                input.eircode(),
                input.numberOfOccupants(),
                input.maxNumberOfOccupants(),
                input.ownerOccupied()
        );
        return householdService.createHousehold(household);
    }

    @MutationMapping
    public Household updateHousehold(@Argument String eircode, @Argument UpdateHouseholdInput input) {
        Household household = new Household(
                eircode,
                input.numberOfOccupants(),
                input.maxNumberOfOccupants(),
                input.ownerOccupied()
        );
        return householdService.updateHousehold(eircode, household);
    }

    @MutationMapping
    public boolean deleteHousehold(@Argument String eircode) {
        householdService.deleteHousehold(eircode);
        return true;
    }
}
