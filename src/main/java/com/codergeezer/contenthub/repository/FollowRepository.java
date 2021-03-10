package com.codergeezer.contenthub.repository;

import com.codergeezer.contenthub.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author haidv
 * @version 1.0
 */
@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

}
