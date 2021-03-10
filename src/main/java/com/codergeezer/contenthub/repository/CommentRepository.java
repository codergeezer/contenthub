package com.codergeezer.contenthub.repository;

import com.codergeezer.contenthub.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author haidv
 * @version 1.0
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    boolean existsByCommentIdAndDeletedFalse(Long commentId);

    Optional<Comment> findByCommentIdAndDeletedFalse(Long commentId);

    boolean existsByParentCommentIdAndDeletedFalse(Long commentId);

    Page<Comment> findByPostIdAndDeletedFalse(Long postId, Pageable pageable);
}
