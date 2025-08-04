package com.jp.orpha.portafolio_inversion.controllers.etl;

import com.jp.orpha.portafolio_inversion.services.EtlExcelService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/etl")
@RequiredArgsConstructor
public class ExcelEtlController {

    private final EtlExcelService etlExcelService;

    @Operation(summary = "Load Excel.", description = "Load and process the Excel file with prices and initial weights.The baseDate is extracted from the Excel file, but the initial portfolio value is fixed at 1,000,000,000.")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") @NotNull MultipartFile file) {
        etlExcelService.processExcelFile(file);
        return ResponseEntity.ok("File processed correctly.");
    }
}

