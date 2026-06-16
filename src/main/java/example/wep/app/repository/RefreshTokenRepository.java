package example.wep.app.repository;

import example.wep.app.entity.RefreshToken;
import example.wep.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}
