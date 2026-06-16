package example.wep.app.dto;


import example.wep.app.entity.AccountCurrency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateAccountRequest(
        @NotBlank
        String accountName,
        @NotNull
        AccountCurrency currency
) {}

