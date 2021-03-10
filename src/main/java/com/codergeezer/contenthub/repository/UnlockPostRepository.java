package com.codergeezer.contenthub.repository;

import com.codergeezer.contenthub.entity.UnlockPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author haidv
 * @version 1.0
 */
@Repository
public interface UnlockPostRepository extends JpaRepository<UnlockPost, Long> {

}
