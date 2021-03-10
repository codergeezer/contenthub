package com.codergeezer.contenthub.repository;

import com.codergeezer.contenthub.entity.Tier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author haidv
 * @version 1.0
 */
@Repository
public interface TierRepository extends JpaRepository<Tier, Long> {

    Optional<Tier> findByTierIdAndGroupIdAndDeletedFalse(Long tierId, Long groupId);

    Optional<Tier> findByCodeAndDeletedFalse(String code);
}
