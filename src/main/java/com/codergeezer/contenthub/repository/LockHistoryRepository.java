package com.codergeezer.contenthub.repository;

import com.codergeezer.contenthub.entity.LockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author haidv
 * @version 1.0
 */
@Repository
public interface LockHistoryRepository extends JpaRepository<LockHistory, Long> {

    Optional<LockHistory> findByUserIdAndLockTrueAndDeletedFalse(Long userId);
}
