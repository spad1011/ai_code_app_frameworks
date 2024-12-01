package org.acme.ai_code_spsp;

import org.acme.ai_code_spsp.entity.Household;
import org.acme.ai_code_spsp.exception.BadDataException;
import org.acme.ai_code_spsp.repository.HouseholdRepository;
import org.acme.ai_code_spsp.service.HouseholdService;
import org.acme.ai_code_spsp.service.impl.HouseholdServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WithMockUser("ADMIN")
class HouseholdServiceTest {

    private final HouseholdRepository householdRepository = mock(HouseholdRepository.class);
    private final HouseholdService householdService = new HouseholdServiceImpl(householdRepository);

    @Test
    void shouldCreateHousehold() {
        Household household = new Household("D12AB01", 3, 5, true);
        when(householdRepository.save(household)).thenReturn(household);

        Household createdHousehold = householdService.createHousehold(household);
        assertEquals(household, createdHousehold);
        verify(householdRepository, times(1)).save(household);
    }

    @Test
    void shouldThrowBadDataExceptionForInvalidOccupantData() {
        Household household = new Household("D12AB01", 6, 5, true);

        BadDataException exception = assertThrows(BadDataException.class, () -> householdService.createHousehold(household));
        assertEquals("Number of occupants cannot exceed maximum number of occupants.", exception.getMessage());
        verify(householdRepository, never()).save(any());
    }

    @Test
    void shouldGetAllHouseholds() {
        List<Household> households = Arrays.asList(
                new Household("D12AB01", 3, 5, true),
                new Household("A45BC02", 1, 2, false)
        );

        when(householdRepository.findAll()).thenReturn(households);

        List<Household> result = householdService.getAllHouseholds();
        assertEquals(households, result);
        verify(householdRepository, times(1)).findAll();
    }

    @Test
    void shouldGetHouseholdStatistics() {
        // Explicitly create the list with the correct type
        List<Object[]> stats = new ArrayList<>();
        stats.add(new Object[]{2L, 3L}); // Example data: 2 empty houses, 3 full houses

        // Mock the repository call with the correct return type
        when(householdRepository.findHouseholdStatistics()).thenReturn(stats);

        // Call the service method
        List<Object[]> result = householdService.getHouseholdStatistics();

        // Validate the results
        assertNotNull(result);
        assertEquals(1, result.size());
        assertNotNull(result.getFirst()); // Ensure each item is an Object[]
        assertEquals(2L, result.getFirst()[0]); // Empty houses
        assertEquals(3L, result.getFirst()[1]); // Full houses

        // Verify the repository interaction
        verify(householdRepository, times(1)).findHouseholdStatistics();
    }

}
