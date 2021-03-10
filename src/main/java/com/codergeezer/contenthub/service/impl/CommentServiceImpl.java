package com.codergeezer.contenthub.service.impl;

import com.codergeezer.contenthub.dto.request.CommentRequest;
import com.codergeezer.contenthub.dto.response.CommentResponse;
import com.codergeezer.contenthub.entity.Comment;
import com.codergeezer.contenthub.entity.Post;
import com.codergeezer.contenthub.enums.ErrorCode;
import com.codergeezer.contenthub.event.Event;
import com.codergeezer.contenthub.repository.CommentRepository;
import com.codergeezer.contenthub.repository.PostRepository;
import com.codergeezer.contenthub.service.CommentService;
import com.codergeezer.contenthub.service.PrincipalProvider;
import com.codergeezer.core.base.data.PagingParam;
import com.codergeezer.core.base.data.ResponsePage;
import com.codergeezer.core.base.event.EventInfo;
import com.codergeezer.core.base.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author haidv
 * @version 1.0
 */
@Service
public class CommentServiceImpl extends PrincipalProvider implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              PostRepository postRepository,
                              ApplicationEventPublisher applicationEventPublisher) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void createComment(Long postId, CommentRequest commentRequest) {
        Post post = postRepository.findByPostIdAndDeletedFalse(postId)
                                  .orElseThrow(() -> new BaseException(ErrorCode.POST_NOT_FOUND));
        if (commentRequest.getParentCommentId() != null &&
            !commentRepository.existsByCommentIdAndDeletedFalse(commentRequest.getParentCommentId())) {
            throw new BaseException(ErrorCode.COMMENT_NOT_FOUND);
        }
        Comment comment = super.modelMapper.convertToComment(commentRequest);
        comment.setUserId(super.getCurrentUserId());
        comment.setPostId(postId);
        commentRepository.save(comment);
        publicEventUpdateComment(post, "ADD");
    }

    @Override
    public ResponsePage<CommentResponse> getComments(Long postId, PagingParam pagingParam) {
        Page<Comment> commentPage = commentRepository.findByPostIdAndDeletedFalse(postId, pagingParam.pageable());
        return new ResponsePage<>(commentPage, super.modelMapper.convertToCommentResponses(commentPage.getContent()));
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findByPostIdAndDeletedFalse(postId)
                                  .orElseThrow(() -> new BaseException(ErrorCode.POST_NOT_FOUND));
        if (commentRepository.existsByParentCommentIdAndDeletedFalse(commentId)) {
            throw new BaseException(ErrorCode.PARENT_COMMENT_NOT_DELETE);
        }
        commentRepository.findByCommentIdAndDeletedFalse(commentId)
                         .ifPresentOrElse(v -> {
                             v.setDeleted(false);
                             commentRepository.save(v);
                         }, () -> {
                             throw new BaseException(ErrorCode.COMMENT_NOT_FOUND);
                         });
        publicEventUpdateComment(post, "DELETE");
    }

    @Override
    public void editComment(Long postId, Long commentId, CommentRequest commentRequest) {
        commentRepository.findByCommentIdAndDeletedFalse(commentId)
                         .ifPresentOrElse(v -> {
                             if (!v.getPostId().equals(postId)) {
                                 throw new BaseException(ErrorCode.COMMENT_NOT_FOUND);
                             }
                             v.setMessage(commentRequest.getMessage());
                             commentRepository.save(v);
                         }, () -> {
                             throw new BaseException(ErrorCode.COMMENT_NOT_FOUND);
                         });
    }

    private void publicEventUpdateComment(Post post, String action) {
        Map<String, Object> map = new HashMap<>();
        map.put("POST", post);
        map.put("ACTION", action);
        applicationEventPublisher.publishEvent(new EventInfo(map, Event.UPDATE_TOTAL_COMMENT));
    }
}
