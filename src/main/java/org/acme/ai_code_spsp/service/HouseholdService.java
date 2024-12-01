package org.acme.ai_code_spsp.service;

import org.acme.ai_code_spsp.entity.Household;

import java.util.List;

public interface HouseholdService {
    Household createHousehold(Household household);

    List<Household> getAllHouseholds();

    Household getHouseholdByIdWithoutPets(String eircode);

    Household getHouseholdByIdWithPets(String eircode);

    Household updateHousehold(String eircode, Household updatedHousehold);

    void deleteHousehold(String eircode);

    List<Household> findHouseholdsWithNoPets();

    List<Household> findOwnerOccupiedHouseholds();

    List<Object[]> getHouseholdStatistics();
}
