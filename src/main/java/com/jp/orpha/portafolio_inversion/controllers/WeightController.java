package com.jp.orpha.portafolio_inversion.controllers;

import com.jp.orpha.portafolio_inversion.dtos.WeightDto;
import com.jp.orpha.portafolio_inversion.services.WeightService;
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
@RequestMapping("/api/weights")
@RequiredArgsConstructor
public class WeightController {

    private final WeightService weightService;

    @Operation(summary = "Get all weights", description = "Returns a list of all weights in the system.")
    @GetMapping
    public ResponseEntity<List<WeightDto>> getAllWeights() {
        return ResponseEntity.ok(weightService.getAllWeights());
    }

    @Operation(summary = "Get a weight by ID", description = "Returns the weight identified by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<WeightDto> getWeightById(@PathVariable Long id) {
        return ResponseEntity.ok(weightService.getWeightById(id));
    }

    @Operation(summary = "Create a new weight", description = "Creates and returns a new weight.")
    @PostMapping
    public ResponseEntity<WeightDto> createWeight(@RequestBody WeightDto weightDto) {
        return ResponseEntity.ok(weightService.createWeight(weightDto));
    }

    @Operation(summary = "Update a weight", description = "Updates the weight identified by its ID.")
    @PutMapping("/{id}")
    public ResponseEntity<WeightDto> updateWeight(@PathVariable Long id,
                                                  @RequestBody WeightDto weightDto) {
        return ResponseEntity.ok(weightService.updateWeight(id, weightDto));
    }

    @Operation(summary = "Delete a weight", description = "Deletes the weight identified by its ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteWeight(@PathVariable Long id) {
        return ResponseEntity.ok(weightService.deleteWeight(id));
    }
}

