package example.wep.app.dto;

import lombok.Builder;


@Builder
public record UserResponse(
        Long uid,
        String firstName,
        String lastName,
        String username
) {
}
