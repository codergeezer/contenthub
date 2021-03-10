package com.codergeezer.contenthub.service.impl;

import com.codergeezer.contenthub.entity.Post;
import com.codergeezer.contenthub.entity.Read;
import com.codergeezer.contenthub.enums.ErrorCode;
import com.codergeezer.contenthub.event.Event;
import com.codergeezer.contenthub.repository.PostRepository;
import com.codergeezer.contenthub.repository.ReadRepository;
import com.codergeezer.contenthub.service.PrincipalProvider;
import com.codergeezer.contenthub.service.ReadService;
import com.codergeezer.core.base.event.EventInfo;
import com.codergeezer.core.base.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author haidv
 * @version 1.0
 */
@Service
public class ReadServiceImpl extends PrincipalProvider implements ReadService {

    private final PostRepository postRepository;

    private final ReadRepository readRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public ReadServiceImpl(PostRepository postRepository,
                           ReadRepository readRepository,
                           ApplicationEventPublisher applicationEventPublisher) {
        this.postRepository = postRepository;
        this.readRepository = readRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void readPost(Long postId) {
        Post post = postRepository.findByPostIdAndDeletedFalse(postId)
                                  .orElseThrow(() -> new BaseException(ErrorCode.POST_NOT_FOUND));
        Read read = new Read();
        read.setRead(true);
        read.setPostId(postId);
        read.setReadTime(LocalDateTime.now());
        read.setAlbumId(post.getAlbumId());
        read.setUserId(super.getCurrentUserId());
        readRepository.save(read);
        applicationEventPublisher.publishEvent(new EventInfo(post, Event.UPDATE_TOTAL_READ));
    }
}
