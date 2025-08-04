package com.jp.orpha.portafolio_inversion.controllers;

import com.jp.orpha.portafolio_inversion.dtos.TransactionDto;
import com.jp.orpha.portafolio_inversion.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Processes transaction", description = "Processes a buy or sell transaction for a portfolio")
    @PostMapping("/process")
    public ResponseEntity<Void> processTransaction(@RequestBody TransactionDto dto) {
        transactionService.processTransaction(dto);
        return ResponseEntity.ok().build();
    }
}
