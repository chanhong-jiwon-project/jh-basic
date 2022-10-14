package com.jhs.crud.auth.repository;

import com.jhs.crud.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByUuid(String Uuid);
}
