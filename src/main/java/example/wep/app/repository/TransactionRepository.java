package example.wep.app.repository;

import example.wep.app.entity.Account;
import example.wep.app.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {
    List<Transactions>
    findBySenderAccountOrReceiverAccount(
            Account senderAccount,
            Account receiverAccount
    );
}
