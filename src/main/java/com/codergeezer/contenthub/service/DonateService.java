package com.codergeezer.contenthub.service;

/**
 * @author haidv
 * @version 1.0
 */
public interface DonateService {

    void donatePost(Long postId, Long cost);

    void donateAlbum(Long albumId, Long cost);

    void donateGroup(Long groupId, Long cost);
}
