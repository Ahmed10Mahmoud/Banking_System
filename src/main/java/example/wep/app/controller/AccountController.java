package example.wep.app.controller;


import example.wep.app.dto.AccountResponse;
import example.wep.app.dto.CreateAccountRequest;
import example.wep.app.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse>
    createAccount(
            @RequestBody @Valid
            CreateAccountRequest request){

        return ResponseEntity.status(
                        HttpStatus.CREATED)
                .body(
                        accountService
                                .createAccount(request)
                );
    }

    @GetMapping("/my-accounts")
    public ResponseEntity<List<AccountResponse>>
        getMyAccounts() {

            return ResponseEntity.ok(
                    accountService.getMyAccounts()
            );
        }
}
