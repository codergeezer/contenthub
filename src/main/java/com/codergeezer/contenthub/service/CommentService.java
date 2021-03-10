package com.codergeezer.contenthub.service;

import com.codergeezer.contenthub.dto.request.CommentRequest;
import com.codergeezer.contenthub.dto.response.CommentResponse;
import com.codergeezer.core.base.data.PagingParam;
import com.codergeezer.core.base.data.ResponsePage;

/**
 * @author haidv
 * @version 1.0
 */
public interface CommentService {

    void createComment(Long id, CommentRequest commentRequest);

    ResponsePage<CommentResponse> getComments(Long id, PagingParam pagingParam);

    void deleteComment(Long postId, Long commentId);

    void editComment(Long postId, Long commentId, CommentRequest commentRequest);
}
