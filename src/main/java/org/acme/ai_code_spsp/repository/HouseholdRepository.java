package org.acme.ai_code_spsp.repository;

import org.acme.ai_code_spsp.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HouseholdRepository extends JpaRepository<Household, String> {

    // 8. Find Households with no pets
    @Query("SELECT h FROM Household h WHERE h.pets IS EMPTY")
    List<Household> findHouseholdsWithNoPets();

    // 9. Find Households that are owner-occupied
    List<Household> findByOwnerOccupiedTrue();

    // 10. Get Household Statistics
    @Query("""
           SELECT 
               SUM(CASE WHEN h.numberOfOccupants = 0 THEN 1 ELSE 0 END) AS emptyHouses,
               SUM(CASE WHEN h.numberOfOccupants = h.maxNumberOfOccupants THEN 1 ELSE 0 END) AS fullHouses
           FROM Household h
           """)
    List<Object[]> findHouseholdStatistics();

    List<Household> findByPetsIsEmpty();

    List<Household> findByOwnerOccupied(boolean b);
}
