package com.anilozmen.blogjwt.repository;

import com.anilozmen.blogjwt.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findTokensByUserIdAndExpiredFalseAndRevokedFalse(Long userId);

    Optional<Token> findByRefreshTokenAndExpiredFalseAndRevokedFalse(String refreshToken);

    Optional<Token> findByToken(String token);
}
