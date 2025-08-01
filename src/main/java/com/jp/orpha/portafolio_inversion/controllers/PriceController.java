package com.jp.orpha.portafolio_inversion.controllers;

import com.jp.orpha.portafolio_inversion.dtos.PriceDto;
import com.jp.orpha.portafolio_inversion.services.PriceService;
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
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @Operation(summary = "Get all prices", description = "Returns a list of all price in the system.")
    @GetMapping
    public ResponseEntity<List<PriceDto>> getAllPrices() {
        return ResponseEntity.ok(priceService.getAllPrices());
    }

    @Operation(summary = "Get a price by ID", description = "Returns the price identified by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<PriceDto> getPriceById(@PathVariable Long id) {
        return ResponseEntity.ok(priceService.getPriceById(id));
    }

    @Operation(summary = "Create a new price", description = "Creates and returns a new price.")
    @PostMapping
    public ResponseEntity<PriceDto> createPrice(@RequestBody PriceDto price) {
        return ResponseEntity.ok(priceService.createPrice(price));
    }

    @Operation(summary = "Update a price", description = "Updates the price identified by its ID.")
    @PutMapping("/{id}")
    public ResponseEntity<PriceDto> updatePrice(@PathVariable Long id,
                                                @RequestBody PriceDto price) {
        return ResponseEntity.ok(priceService.updatePrice(id, price));
    }

    @Operation(summary = "Delete a price", description = "Deletes the price identified by its ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePrice(@PathVariable Long id) {
        return ResponseEntity.ok(priceService.deletePriceById(id));
    }
}
