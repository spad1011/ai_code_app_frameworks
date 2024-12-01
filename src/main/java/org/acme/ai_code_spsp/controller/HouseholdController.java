package org.acme.ai_code_spsp.controller;

import org.acme.ai_code_spsp.entity.Household;
import org.acme.ai_code_spsp.service.HouseholdService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/households")
public class HouseholdController {

    private final HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @PostMapping
    public ResponseEntity<Household> createHousehold(@RequestBody Household household) {
        return ResponseEntity.ok(householdService.createHousehold(household));
    }

    @GetMapping
    public ResponseEntity<List<Household>> getAllHouseholds() {
        return ResponseEntity.ok(householdService.getAllHouseholds());
    }

    @GetMapping("/{eircode}/without-pets")
    public ResponseEntity<Household> getHouseholdByIdWithoutPets(@PathVariable String eircode) {
        return ResponseEntity.ok(householdService.getHouseholdByIdWithoutPets(eircode));
    }

    @GetMapping("/{eircode}/with-pets")
    public ResponseEntity<Household> getHouseholdByIdWithPets(@PathVariable String eircode) {
        return ResponseEntity.ok(householdService.getHouseholdByIdWithPets(eircode));
    }

    @PutMapping("/{eircode}")
    public ResponseEntity<Household> updateHousehold(@PathVariable String eircode, @RequestBody Household updatedHousehold) {
        return ResponseEntity.ok(householdService.updateHousehold(eircode, updatedHousehold));
    }

    @DeleteMapping("/{eircode}")
    public ResponseEntity<Void> deleteHousehold(@PathVariable String eircode) {
        householdService.deleteHousehold(eircode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/no-pets")
    public ResponseEntity<List<Household>> findHouseholdsWithNoPets() {
        return ResponseEntity.ok(householdService.findHouseholdsWithNoPets());
    }

    @GetMapping("/owner-occupied")
    public ResponseEntity<List<Household>> findOwnerOccupiedHouseholds() {
        return ResponseEntity.ok(householdService.findOwnerOccupiedHouseholds());
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<Object[]>> getHouseholdStatistics() {
        return ResponseEntity.ok(householdService.getHouseholdStatistics());
    }
}
