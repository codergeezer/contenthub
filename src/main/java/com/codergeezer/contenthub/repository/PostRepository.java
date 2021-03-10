package com.codergeezer.contenthub.repository;

import com.codergeezer.contenthub.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author haidv
 * @version 1.0
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    boolean existsByPostIdAndDeletedFalse(Long postId);

    Optional<Post> findByPostIdAndDeletedFalse(Long postId);

    @Transactional
    @Modifying
    @Query("update Post set totalLike = totalLike + 1 where postId = ?1")
    void plusTotalLike(Long postId);

    @Transactional
    @Modifying
    @Query("update Post set totalLike = totalLike - 1 where postId = ?1")
    void minusTotalLike(Long postId);

    @Transactional
    @Modifying
    @Query("update Post set totalRead = totalRead + 1 where postId = ?1")
    void plusTotalRead(Long postId);

    @Transactional
    @Modifying
    @Query("update Post set totalComment = totalComment + 1 where postId = ?1")
    void plusTotalComment(Long postId);

    @Transactional
    @Modifying
    @Query("update Post set totalComment = totalComment - 1 where postId = ?1")
    void minusTotalComment(Long postId);

    @Transactional
    @Modifying
    @Query("update Post set totalDonate = totalDonate + ?1 where postId = ?2")
    void plusTotalDonate(Long cost, Long postId);

    @Query("select p from Post p join Subscribe s on p.groupId = s.groupId left join Album a on a.albumId = p.albumId " +
           "left join UnlockPost up on p.postId = up.postId and up.userId = :userId where p.deleted = false " +
           "and p.publicDate <= :publicDate and s.userId = :userId and s.subscribe = true ")
    List<Post> findFeedSubscribe(@Param("publicDate") LocalDateTime dateTime, @Param("userId") Long userId);
}
