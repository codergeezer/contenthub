package com.codergeezer.contenthub.repository;

import com.codergeezer.contenthub.entity.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface AlbumRepository extends JpaRepository<Album, Long> {

    boolean existsByAlbumIdAndGroupIdAndDeletedFalse(Long albumId, Long groupId);

    Optional<Album> findByAlbumIdAndDeletedFalse(Long albumId);

    @Transactional
    @Modifying
    @Query("update Album set totalLike = totalLike + 1 where albumId = ?1")
    void plusTotalLike(Long albumId);

    @Transactional
    @Modifying
    @Query("update Album set totalLike = totalLike - 1 where albumId = ?1")
    void minusTotalLike(Long albumId);

    @Transactional
    @Modifying
    @Query("update Album set totalRead = totalRead + 1 where albumId = ?1")
    void plusTotalRead(Long albumId);

    @Transactional
    @Modifying
    @Query("update Album set totalComment = totalComment + 1 where albumId = ?1")
    void plusTotalComment(Long albumId);

    @Transactional
    @Modifying
    @Query("update Album set totalComment = totalComment - 1 where albumId = ?1")
    void minusTotalComment(Long albumId);

    @Transactional
    @Modifying
    @Query("update Album set totalDonate = totalDonate + ?1 where albumId = ?2")
    void plusTotalDonate(Long cost, Long albumId);

    Page<Album> findByDeletedFalseAndGroupId(Long groupId, Pageable pageable);
}
