package com.jp.orpha.portafolio_inversion.controllers;

import com.jp.orpha.portafolio_inversion.dtos.QuantityDto;
import com.jp.orpha.portafolio_inversion.services.QuantityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
@RequiredArgsConstructor
public class QuantityController {

    private final QuantityService quantityService;

    @Operation(summary = "Get all quantities", description = "Returns a list of all quantities in the system.")
    @GetMapping
    public ResponseEntity<List<QuantityDto>> getAllQuantities() {
        return ResponseEntity.ok(quantityService.getAllQuantities());
    }

    @Operation(summary = "Get a quantity by ID", description = "Returns the quantity identified by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<QuantityDto> getQuantityById(@PathVariable Long id) {
        return ResponseEntity.ok(quantityService.getQuantityById(id));
    }

    @Operation(summary = "Create a new quantity", description = "Creates and returns a new quantity.")
    @PostMapping
    public ResponseEntity<QuantityDto> createQuantity(@RequestBody QuantityDto quantity) {
        return ResponseEntity.ok(quantityService.createQuantity(quantity));
    }

    @Operation(summary = "Update a quantity", description = "Updates the quantity identified by its ID.")
    @PutMapping("/{id}")
    public ResponseEntity<QuantityDto> updateQuantity(@PathVariable Long id,
                                                      @RequestBody QuantityDto quantity) {
        return ResponseEntity.ok(quantityService.updateQuantity(id, quantity));
    }

    @Operation(summary = "Delete a quantity", description = "Deletes the quantity identified by its ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteQuantityById(@PathVariable Long id) {
        return ResponseEntity.ok(quantityService.deleteQuantityById(id));
    }
}
