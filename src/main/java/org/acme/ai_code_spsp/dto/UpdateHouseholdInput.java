package org.acme.ai_code_spsp.dto;

public record UpdateHouseholdInput(
        Integer numberOfOccupants,
        Integer maxNumberOfOccupants,
        Boolean ownerOccupied
) {}
