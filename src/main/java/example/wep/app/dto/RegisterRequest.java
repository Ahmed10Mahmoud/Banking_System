package example.wep.app.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String username,
        @NotBlank
        @Size(min = 8)
        String password,
        @NotBlank
        String gender
) {
}
