package org.acme.ai_code_spsp.dto;

public record CreatePetInput(
        String name,
        String animalType,
        String breed,
        int age,
        String householdEircode
) {}
