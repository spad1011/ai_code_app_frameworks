package org.acme.ai_code_spsp.service.impl;

import org.acme.ai_code_spsp.entity.Household;
import org.acme.ai_code_spsp.exception.BadDataException;
import org.acme.ai_code_spsp.exception.NotFoundException;
import org.acme.ai_code_spsp.repository.HouseholdRepository;
import org.acme.ai_code_spsp.service.HouseholdService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseholdServiceImpl implements HouseholdService {
    private final HouseholdRepository householdRepository;

    public HouseholdServiceImpl(HouseholdRepository householdRepository) {
        this.householdRepository = householdRepository;
    }

    @Override
    public Household createHousehold(Household household) {
        if (household.getMaxNumberOfOccupants() < household.getNumberOfOccupants()) {
            throw new BadDataException("Number of occupants cannot exceed maximum number of occupants.");
        }
        return householdRepository.save(household);
    }

    @Override
    public List<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }

    @Override
    public Household getHouseholdByIdWithoutPets(String eircode) {
        return householdRepository.findById(eircode)
                .orElseThrow(() -> new NotFoundException("Household not found with eircode: " + eircode));
    }

    @Override
    public Household getHouseholdByIdWithPets(String eircode) {
        return householdRepository.findById(eircode)
                .orElseThrow(() -> new NotFoundException("Household not found with eircode: " + eircode));
    }

    @Override
    public Household updateHousehold(String eircode, Household updatedHousehold) {
        Household existingHousehold = getHouseholdByIdWithoutPets(eircode);

        if (updatedHousehold.getMaxNumberOfOccupants() < updatedHousehold.getNumberOfOccupants()) {
            throw new BadDataException("Number of occupants cannot exceed maximum number of occupants.");
        }

        existingHousehold.setNumberOfOccupants(updatedHousehold.getNumberOfOccupants());
        existingHousehold.setMaxNumberOfOccupants(updatedHousehold.getMaxNumberOfOccupants());
        existingHousehold.setOwnerOccupied(updatedHousehold.isOwnerOccupied());

        return householdRepository.save(existingHousehold);
    }

    @Override
    public void deleteHousehold(String eircode) {
        householdRepository.deleteById(eircode);
    }

    @Override
    public List<Household> findHouseholdsWithNoPets() {
        return householdRepository.findHouseholdsWithNoPets();
    }

    @Override
    public List<Household> findOwnerOccupiedHouseholds() {
        return householdRepository.findByOwnerOccupiedTrue();
    }

    @Override
    public List<Object[]> getHouseholdStatistics() {
        return householdRepository.findHouseholdStatistics();
    }
}
