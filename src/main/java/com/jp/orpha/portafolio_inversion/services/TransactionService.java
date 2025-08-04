package com.jp.orpha.portafolio_inversion.services;

import com.jp.orpha.portafolio_inversion.dtos.TransactionDto;

public interface TransactionService {

    void processTransaction(TransactionDto transactionDto);

}
