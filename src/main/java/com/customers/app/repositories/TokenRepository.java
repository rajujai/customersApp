package com.customers.app.repositories;

import com.customers.app.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

    @Query(value = "SELECT t FROM Token t INNER JOIN AuthUser u ON t.user.id = u.id WHERE u.id = :id AND (t.expired = false OR t.revoked = false)")
    List<Token> findAllValidTokenByUser(String id);

    Optional<Token> findByToken(String token);

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT t FROM Token t WHERE t.revoked = false AND t.expired = false AND t.token = :token) THEN 1 ELSE 0 END")
    Optional<Boolean> isValidToken(String token);
}
