package org.acme.ai_code_spsp.dto;

public record UpdatePetInput(
        String name,
        String animalType,
        String breed,
        Integer age,
        String householdEircode
) {}
