package com.codergeezer.contenthub.repository;

import com.codergeezer.contenthub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author haidv
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByDeletedFalseAndUserId(Long userId);

    @Query(value = "select nextval('user_code_seq')", nativeQuery = true)
    long findUserCodeSequenceNextVal();

    @Transactional
    @Modifying
    @Query("update User set money = money + ?1 where userId = ?2")
    void updateAccountBalance(Double cost, Long userId);

    boolean existsByMoneyGreaterThanEqualAndUserIdAndDeletedFalse(Double cost, Long userId);
}
