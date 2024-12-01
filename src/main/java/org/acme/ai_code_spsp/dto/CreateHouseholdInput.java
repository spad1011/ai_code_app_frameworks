package org.acme.ai_code_spsp.dto;

public record CreateHouseholdInput(
        String eircode,
        int numberOfOccupants,
        int maxNumberOfOccupants,
        boolean ownerOccupied
) {}
