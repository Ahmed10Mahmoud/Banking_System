package example.wep.app.dto;

import lombok.Builder;

@Builder
public record AuthResponseRefresh(
        String accessToken,
        String refreshToken
) {
}
