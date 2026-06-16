package example.wep.app.service;
import example.wep.app.dto.AccountResponse;
import example.wep.app.dto.CreateAccountRequest;
import example.wep.app.entity.Account;
import example.wep.app.entity.User;
import example.wep.app.exception.UserNotFoundException;
import example.wep.app.repository.AccountRepository;
import example.wep.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public AccountResponse createAccount(CreateAccountRequest request) {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User owner = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));
        Account account = Account.builder()
                .owner(owner)
                .accountName(request.accountName())
                .currency(request.currency())
                .balance(BigDecimal.ZERO)
                .accountNumber(generateAccountNumber())
                .build();

        Account saved =
                accountRepository.save(account);

        return AccountResponse.builder()
                .accountId(saved.getAccId())
                .accountNumber(saved.getAccountNumber())
                .accountName(saved.getAccountName())
                .currency(saved.getCurrency())
                .balance(saved.getBalance())
                .build();
    }

    private Long generateAccountNumber() {

        Long accountNumber;

        do {
            accountNumber =
                    ThreadLocalRandom.current()
                            .nextLong(
                                    1000000000L,
                                    9999999999L
                            );
        }
        while(accountRepository
                .existsByAccountNumber(accountNumber));

        return accountNumber;
    }

    public List<AccountResponse> getMyAccounts(){
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String username = authentication.getName();

        List<Account> accounts =
                accountRepository
                        .findByOwnerUsernameIgnoreCase(username);

        return accounts.stream()
                .map(account -> AccountResponse.builder()
                        .accountNumber(account.getAccountNumber())
                        .accountName(account.getAccountName())
                        .balance(account.getBalance())
                        .currency(account.getCurrency())
                        .build())
                .toList();
    }
}

