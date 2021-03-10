package com.codergeezer.contenthub.service;

/**
 * @author haidv
 * @version 1.0
 */
public interface LikeService {

    void likePost(Long postId);

    void unLikePost(Long postId);
}
