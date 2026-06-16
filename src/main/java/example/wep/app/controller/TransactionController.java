package example.wep.app.controller;

import example.wep.app.dto.*;
import example.wep.app.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<DepositResponse> deposit(
            @PathVariable Long  accountId,
            @RequestBody @Valid DepositRequest request){
        return ResponseEntity.ok(
                transactionService.deposit(
                        accountId,
                        request
                )
        );
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<WithdrawResponse>
    withdraw(
            @PathVariable Long accountId,
            @RequestBody @Valid
            WithdrawRequest request){

        return ResponseEntity.ok(
                transactionService.withdraw(
                        accountId,
                        request
                )
        );
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(
            @RequestBody @Valid
            TransferRequest request){

        return ResponseEntity.ok(
                transactionService.transfer(request)
        );
    }

    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<Page<TransactionResponse>> getTransactions(
            @PathVariable Long accountNumber,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "10")
            int size
    ){
        return ResponseEntity.ok(
                transactionService.getAccountTransactions(accountNumber,
                        page,
                        size)
        );
    }

}
