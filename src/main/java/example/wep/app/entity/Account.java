package example.wep.app.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  accId;

    @Enumerated(EnumType.STRING)
    private AccountCurrency  currency;

    private String sympol;
    private String code;
    private String accountName;
    @Column(nullable = false,unique = true)
    private Long accountNumber;
    private BigDecimal balance;
    private String label;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "senderAccount")
    private List<Transactions> sentTransactions;

    @OneToMany(mappedBy = "receiverAccount")
    private List<Transactions> receivedTransactions;


}
