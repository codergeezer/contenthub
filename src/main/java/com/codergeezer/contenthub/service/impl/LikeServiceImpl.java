package com.codergeezer.contenthub.service.impl;

import com.codergeezer.contenthub.entity.Like;
import com.codergeezer.contenthub.entity.Post;
import com.codergeezer.contenthub.enums.ErrorCode;
import com.codergeezer.contenthub.event.Event;
import com.codergeezer.contenthub.repository.LikeRepository;
import com.codergeezer.contenthub.repository.PostRepository;
import com.codergeezer.contenthub.service.LikeService;
import com.codergeezer.contenthub.service.PrincipalProvider;
import com.codergeezer.core.base.event.EventInfo;
import com.codergeezer.core.base.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author haidv
 * @version 1.0
 */
@Service
public class LikeServiceImpl extends PrincipalProvider implements LikeService {

    private final LikeRepository likeRepository;

    private final PostRepository postRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository,
                           PostRepository postRepository,
                           ApplicationEventPublisher applicationEventPublisher) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void likePost(Long postId) {
        Post post = postRepository.findByPostIdAndDeletedFalse(postId)
                                  .orElseThrow(() -> new BaseException(ErrorCode.POST_NOT_FOUND));
        likeRepository.findByUserIdAndPostId(super.getCurrentUserId(), postId)
                      .ifPresentOrElse(v -> {
                          v.setLike(true);
                          v.setLikeTime(LocalDateTime.now());
                          likeRepository.save(v);
                      }, () -> {
                          Like like = new Like();
                          like.setLikeTime(LocalDateTime.now());
                          like.setPostId(postId);
                          like.setUserId(super.getCurrentUserId());
                          like.setLike(true);
                          likeRepository.save(like);
                      });
        publicEventUpdateLike(post, true);
    }


    @Override
    public void unLikePost(Long postId) {
        Post post = postRepository.findByPostIdAndDeletedFalse(postId)
                                  .orElseThrow(() -> new BaseException(ErrorCode.POST_NOT_FOUND));
        likeRepository.findByUserIdAndPostId(super.getCurrentUserId(), postId)
                      .ifPresentOrElse(v -> {
                          v.setLike(false);
                          v.setUnlikeTime(LocalDateTime.now());
                          likeRepository.save(v);
                      }, () -> { throw new BaseException(ErrorCode.EMAIL_TEMPLATE_EXISTED); });
        publicEventUpdateLike(post, false);
    }

    private void publicEventUpdateLike(Post post, boolean like) {
        Map<String, Object> map = new HashMap<>();
        map.put("POST", post);
        map.put("LIKE", like);
        applicationEventPublisher.publishEvent(new EventInfo(map, Event.UPDATE_TOTAL_LIKE));
    }
}
