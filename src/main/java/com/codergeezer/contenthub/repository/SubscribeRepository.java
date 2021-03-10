package com.codergeezer.contenthub.repository;

import com.codergeezer.contenthub.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author haidv
 * @version 1.0
 */
@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    Optional<Subscribe> findByGroupIdAndUserIdAndDeletedFalseAndSubscribeTrue(Long groupId, Long userId);

    Optional<Subscribe> findByGroupIdAndUserIdAndDeletedFalse(Long groupId, Long userId);
}
