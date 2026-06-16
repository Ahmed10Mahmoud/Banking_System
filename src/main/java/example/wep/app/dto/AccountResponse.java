package example.wep.app.dto;

import example.wep.app.entity.AccountCurrency;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record AccountResponse(
        Long accountId,
         Long accountNumber,
         String accountName,
         AccountCurrency currency,
        BigDecimal balance
) {
}
