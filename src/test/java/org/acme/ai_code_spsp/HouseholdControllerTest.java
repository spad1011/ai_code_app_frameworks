package org.acme.ai_code_spsp;

import org.acme.ai_code_spsp.controller.HouseholdController;
import org.acme.ai_code_spsp.entity.Household;
import org.acme.ai_code_spsp.service.HouseholdService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HouseholdController.class)
class HouseholdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HouseholdService householdService;


    @Test
    @WithMockUser(roles = "ADMIN") // Simulates a user with the ADMIN role
    void shouldCreateHousehold() throws Exception {
        Household household = new Household("E123", 4, 5, true);

        when(householdService.createHousehold(any(Household.class))).thenReturn(household);

        mockMvc.perform(post("/households")
                        .with(csrf()) // Add CSRF token
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "eircode": "E123",
                        "numberOfOccupants": 4,
                        "maxNumberOfOccupants": 5,
                        "ownerOccupied": true
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eircode").value("E123"));

        verify(householdService, times(1)).createHousehold(any(Household.class));
    }


    @Test
    @WithMockUser(roles = "USER") // Simulates a user with the USER role
    void shouldGetHouseholdStatistics() throws Exception {
        List<Object[]> stats = new ArrayList<>();
        stats.add(new Object[]{3.5, 10, 20L});

        when(householdService.getHouseholdStatistics()).thenReturn(stats);

        mockMvc.perform(get("/households/statistics"))
                .andExpect(status().isOk());

        verify(householdService, times(1)).getHouseholdStatistics();
    }

    @Test
    @WithMockUser(roles = "USER") // Simulates a user with the USER role
    void shouldFindHouseholdsWithNoPets() throws Exception {
        List<Household> households = List.of(new Household("E123", 0, 5, true));

        when(householdService.findHouseholdsWithNoPets()).thenReturn(households);

        mockMvc.perform(get("/households/no-pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eircode").value("E123"));

        verify(householdService, times(1)).findHouseholdsWithNoPets();
    }
}
