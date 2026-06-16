package example.wep.app.repository;

import example.wep.app.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, Long> {
}
