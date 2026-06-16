package example.wep.app.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransferResponse(
        String message,
        BigDecimal senderBalance,
        BigDecimal receiverBalance
) {
}
