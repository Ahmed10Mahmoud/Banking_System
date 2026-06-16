package example.wep.app.service;

import example.wep.app.dto.*;
import example.wep.app.entity.Account;
import example.wep.app.entity.Status;
import example.wep.app.entity.Transactions;
import example.wep.app.entity.Type;
import example.wep.app.exception.AccountNotFoundException;
import example.wep.app.exception.InsufficientBalanceException;
import example.wep.app.exception.UnauthorizedAccountAccessException;
import example.wep.app.repository.AccountRepository;
import example.wep.app.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;


    @Transactional
    public DepositResponse deposit(
            Long   accountId,
            DepositRequest request){
        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found"));

        account.setBalance(
                account.getBalance()
                        .add(request.amount())
        );
        Transactions transaction =
                Transactions.builder()
                        .type(Type.DEPOSIT)
                        .status(Status.SUCCESS)
                        .amount(BigDecimal.ZERO)
                        .description("Cash Deposit")
                        .receiverAccount(account)
                        .build();

        transactionRepository.save(transaction);
        accountRepository.save(account);
        return DepositResponse.builder()
                .message("Deposit successful")
                .newBalance(account.getBalance())
                .build();
    }

    @Transactional
    public WithdrawResponse withdraw(
            Long accountId,
            WithdrawRequest request){

        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found"));

        if(account.getBalance()
                .compareTo(request.amount()) < 0){

            throw new InsufficientBalanceException(
                    "Insufficient balance"
            );
        }
        account.setBalance(
                account.getBalance()
                        .subtract(
                                request.amount()
                        )
        );

        Transactions transaction =
                Transactions.builder()
                        .type(Type.WITHDRAW)
                        .status(Status.SUCCESS)
                        .amount(request.amount())
                        .description("Cash Withdraw")
                        .senderAccount(account)
                        .build();

        transactionRepository.save(transaction);

        accountRepository.save(account);

        return WithdrawResponse.builder()
                .message("Withdraw successful")
                .newBalance(account.getBalance())
                .build();
    }

    @Transactional
    public TransferResponse transfer(
            TransferRequest request){

        if(request.senderAccountNumber()
                .equals(request.receiverAccountNumber())){

            throw new IllegalArgumentException(
                    "Cannot transfer to same account"
            );
        }
        Account sender =
                accountRepository
                        .findByAccountNumber(
                                request.senderAccountNumber())
                        .orElseThrow(
                                () -> new AccountNotFoundException(
                                        "Sender account not found"
                                )
                        );

        Account receiver =
                accountRepository
                        .findByAccountNumber(
                                request.receiverAccountNumber())
                        .orElseThrow(
                                () -> new AccountNotFoundException(
                                        "Receiver account not found"
                                )
                        );

        if(sender.getBalance()
                .compareTo(request.amount()) < 0){

            throw new InsufficientBalanceException(
                    "Insufficient balance"
            );
        }

        sender.setBalance(
                sender.getBalance()
                        .subtract(request.amount())
        );

        receiver.setBalance(
                receiver.getBalance()
                        .add(request.amount())
        );

        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transactions transaction =
                Transactions.builder()
                        .type(Type.TRANSFER)
                        .status(Status.SUCCESS)
                        .amount(request.amount())
                        .sender(sender.getAccountNumber().toString())
                        .reciever(receiver.getAccountNumber().toString())
                        .description("Money Transfer")
                        .senderAccount(sender)
                        .receiverAccount(receiver)
                        .build();

        transactionRepository.save(transaction);

        return TransferResponse.builder()
                .message("Transfer successful")
                .senderBalance(sender.getBalance())
                .receiverBalance(receiver.getBalance())
                .build();

    }

    public List<TransactionResponse>
    getAccountTransactions(Long accountNumber){

        Account account =
                accountRepository
                        .findByAccountNumber(accountNumber)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Account not found"
                                )
                        );
        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        if (!account.getOwner()
                .getUsername()
                .equalsIgnoreCase(username)) {

            throw new UnauthorizedAccountAccessException(
                    "You don't own this account"
            );
        }

        List<Transactions> transactions =
                transactionRepository
                        .findBySenderAccountOrReceiverAccount(
                                account,
                                account
                        );

        return transactions.stream()
                .map(tx -> TransactionResponse.builder()
                        .txId(tx.getTxId())
                        .type(tx.getType())
                        .amount(tx.getAmount())
                        .status(tx.getStatus())
                        .sender(tx.getSender())
                        .receiver(tx.getReciever())
                        .createdAt(tx.getCreatedAt())
                        .build()
                )
                .toList();
    }
}
