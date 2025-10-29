package com.infoeste.codecash.dto;


import java.math.BigDecimal;
import java.util.UUID;

public record CreateTransactionInput(
        BigDecimal amount,
        UUID payerAccountId,
        UUID payeeAccountId
) {



}
