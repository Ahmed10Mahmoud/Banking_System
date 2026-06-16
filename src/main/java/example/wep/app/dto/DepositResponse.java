package example.wep.app.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record DepositResponse(
        String message,
        BigDecimal newBalance
) {
}
