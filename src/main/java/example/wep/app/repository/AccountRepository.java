package example.wep.app.repository;

import example.wep.app.entity.Account;
import example.wep.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long > {
    boolean existsByAccountNumber(Long accountNumber);
    Optional<Account> findByAccountNumber (Long accountNumber);
    List<Account> findByOwnerUsernameIgnoreCase(String username);
}
