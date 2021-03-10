package com.codergeezer.contenthub.repository;

import com.codergeezer.contenthub.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author haidv
 * @version 1.0
 */
@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    @Query("select lh from LoginHistory lh where lh.sessionId = ?1 and lh.activated = true " +
           "and lh.renewToken = true and lh.endAt is null")
    Optional<LoginHistory> findByRefreshTokenId(String refreshTokenId);

    boolean existsByActivatedTrueAndSessionIdAndRenewTokenFalse(String id);

    List<LoginHistory> findBySessionIdAndActivatedTrue(String id);

    boolean existsByActivatedTrueAndSessionIdAndSecurityCode(String id, String code);

    List<LoginHistory> findByUserIdAndDeletedFalseAndActivatedTrue(Long id);
}
