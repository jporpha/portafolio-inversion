package com.jp.orpha.portafolio_inversion.controllers;

import com.jp.orpha.portafolio_inversion.services.HoldingService;
import com.jp.orpha.portafolio_inversion.dtos.HoldingDto;
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
@RequestMapping("/api/holdings")
@RequiredArgsConstructor
public class HoldingController {

    private final HoldingService holdingService;

    @Operation(summary = "Get all holdings", description = "Returns a list of all holdings in the system.")
    @GetMapping
    public ResponseEntity<List<HoldingDto>> getAllHoldings() {
        return ResponseEntity.ok(holdingService.getAllHoldings());
    }

    @Operation(summary = "Get a holding by ID", description = "Returns the holding identified by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<HoldingDto> getHoldingById(@PathVariable Long id) {
        return ResponseEntity.ok(holdingService.getHoldingById(id));
    }

    @Operation(summary = "Create a new holding", description = "Creates and returns a new holding.")
    @PostMapping
    public ResponseEntity<HoldingDto> createHolding(@RequestBody HoldingDto holdingDto) {
        return ResponseEntity.ok(holdingService.createHolding(holdingDto));
    }

    @Operation(summary = "Update a holding", description = "Updates the holding identified by its ID.")
    @PutMapping("/{id}")
    public ResponseEntity<HoldingDto> updateHolding(@PathVariable Long id,
                                                    @RequestBody HoldingDto holdingDto) {
        return ResponseEntity.ok(holdingService.updateHolding(id, holdingDto));
    }

    @Operation(summary = "Delete a holding", description = "Deletes the holding identified by its ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteHolding(@PathVariable Long id) {
        return ResponseEntity.ok(holdingService.deleteHoldingById(id));
    }
}

