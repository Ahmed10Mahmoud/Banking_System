package example.wep.app.dto;

import lombok.Builder;

@Builder
public record LoginResponse(
        String message,
        String username
) {
}
