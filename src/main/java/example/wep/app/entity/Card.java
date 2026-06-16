package example.wep.app.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  cardId;

    @Column(nullable = false,unique = true)
    @NotBlank
    private String cardNumber;

    @NotBlank
    private String cardHolder;
    private BigDecimal balance;
    private LocalDate exp;
    private String cvv;
    private String pin;
    private String billingAddress;

    @CreationTimestamp
    private LocalDateTime iss;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;


}
