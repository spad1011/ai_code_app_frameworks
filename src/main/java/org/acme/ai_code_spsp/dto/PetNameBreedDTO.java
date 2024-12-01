package org.acme.ai_code_spsp.dto;

/**
 * A record to transfer only the name, animal type, and breed of a pet.
 */
public record PetNameBreedDTO(String name, String animalType, String breed) {}
