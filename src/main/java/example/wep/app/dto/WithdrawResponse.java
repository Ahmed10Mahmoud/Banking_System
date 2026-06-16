package example.wep.app.dto;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record WithdrawResponse(
        String message,
        BigDecimal newBalance
) {
}
