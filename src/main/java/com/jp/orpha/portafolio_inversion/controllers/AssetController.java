package com.jp.orpha.portafolio_inversion.controllers;

import com.jp.orpha.portafolio_inversion.dtos.AssetDto;
import com.jp.orpha.portafolio_inversion.services.AssetService;
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
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @Operation(summary = "Get all assets", description = "Returns a list of all assets in the system.")
    @GetMapping
    public ResponseEntity<List<AssetDto>> getAllAssets() {
        return ResponseEntity.ok(assetService.getAllAssets());
    }

    @Operation(summary = "Get an asset by ID", description = "Returns the asset identified by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<AssetDto> getAssetById(@PathVariable Long id) {
        return ResponseEntity.ok(assetService.getAssetById(id));
    }

    @Operation(summary = "Create a new asset", description = "Creates and returns a new asset.")
    @PostMapping
    public ResponseEntity<AssetDto> createAsset(@RequestBody AssetDto assetDto) {
        return ResponseEntity.ok(assetService.createAsset(assetDto));
    }

    @Operation(summary = "Update an asset", description = "Updates the asset identified by its ID.")
    @PutMapping("/{id}")
    public ResponseEntity<AssetDto> updateAsset(@PathVariable Long id,
                                                @RequestBody AssetDto assetDto) {
        return ResponseEntity.ok(assetService.updateAsset(id, assetDto));
    }

    @Operation(summary = "Delete an asset", description = "Deletes the asset identified by its ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAsset(@PathVariable Long id) {
        return ResponseEntity.ok(assetService.deleteAssetById(id));
    }
}