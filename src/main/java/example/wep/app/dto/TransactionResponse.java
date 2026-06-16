package example.wep.app.dto;

import example.wep.app.entity.Status;
import example.wep.app.entity.Type;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransactionResponse(
         Long txId,
        Type type,
        Status status,
        BigDecimal amount,
         String sender,
         String receiver,
        LocalDateTime createdAt
) {
}
