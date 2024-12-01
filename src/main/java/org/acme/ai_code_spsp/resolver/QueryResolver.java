package org.acme.ai_code_spsp.resolver;

import org.acme.ai_code_spsp.dto.PetNameBreedDTO;
import org.acme.ai_code_spsp.entity.Household;
import org.acme.ai_code_spsp.entity.Pet;
import org.acme.ai_code_spsp.service.HouseholdService;
import org.acme.ai_code_spsp.service.PetService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
//DID NOT INCLUDE ANNOTATIONS FOR MAPPING AND ARGUMENT
@Controller
public class QueryResolver {

    private final PetService petService;
    private final HouseholdService householdService;

    public QueryResolver(PetService petService, HouseholdService householdService) {
        this.petService = petService;
        this.householdService = householdService;
    }

    // Pet Queries
    @QueryMapping
    public List<Pet> allPets() {
        return petService.getAllPets();
    }

    @QueryMapping
    public Pet petById(@Argument int id) {
        return petService.getPetById(id);
    }

    @QueryMapping
    public List<Pet> petsByAnimalType(@Argument String animalType) {
        return petService.findPetsByAnimalType(animalType);
    }

    @QueryMapping
    public List<Pet> petsByBreedOrderedByAge(@Argument String breed) {
        return petService.findPetsByBreed(breed);
    }

    //wrong return type: Map<String, Integer>
    @QueryMapping
    public List<Object[]> petStatistics() {
        return petService.getPetStatistics();
    }

    //wrong name still
    @QueryMapping
    public List<PetNameBreedDTO> petNameAndBreed() {
        return petService.getPetSummaries();
    }

    // Household Queries
    @QueryMapping
    public List<Household> allHouseholds() {
        return householdService.getAllHouseholds();
    }

    @QueryMapping
    public Household householdByIdWithoutPets(@Argument String eircode) {
        return householdService.getHouseholdByIdWithoutPets(eircode);
    }

    @QueryMapping
    public Household householdByIdWithPets(@Argument String eircode) {
        return householdService.getHouseholdByIdWithPets(eircode);
    }

    @QueryMapping
    public List<Household> householdsWithNoPets() {
        return householdService.findHouseholdsWithNoPets();
    }

    @QueryMapping
    public List<Household> ownerOccupiedHouseholds() {
        return householdService.findOwnerOccupiedHouseholds();
    }

    //wrong return type: Map<String, Integer>
    @QueryMapping
    public List<Object[]> householdStatistics() {
        return householdService.getHouseholdStatistics();
    }
}
