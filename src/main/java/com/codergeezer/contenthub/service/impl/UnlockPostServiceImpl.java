package com.codergeezer.contenthub.service.impl;

import com.codergeezer.contenthub.entity.Post;
import com.codergeezer.contenthub.entity.Transaction;
import com.codergeezer.contenthub.entity.UnlockPost;
import com.codergeezer.contenthub.enums.ErrorCode;
import com.codergeezer.contenthub.event.Event;
import com.codergeezer.contenthub.repository.PostRepository;
import com.codergeezer.contenthub.repository.TransactionRepository;
import com.codergeezer.contenthub.repository.UnlockPostRepository;
import com.codergeezer.contenthub.repository.UserRepository;
import com.codergeezer.contenthub.service.PrincipalProvider;
import com.codergeezer.contenthub.service.UnlockPostService;
import com.codergeezer.contenthub.utils.Constant;
import com.codergeezer.core.base.event.EventInfo;
import com.codergeezer.core.base.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author haidv
 * @version 1.0
 */
@Service
public class UnlockPostServiceImpl extends PrincipalProvider implements UnlockPostService {

    private final UnlockPostRepository unlockPostRepository;

    private final PostRepository postRepository;

    private final TransactionRepository transactionRepository;

    private final UserRepository userRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public UnlockPostServiceImpl(UnlockPostRepository unlockPostRepository,
                                 PostRepository postRepository,
                                 TransactionRepository transactionRepository,
                                 UserRepository userRepository,
                                 ApplicationEventPublisher applicationEventPublisher) {
        this.unlockPostRepository = unlockPostRepository;
        this.postRepository = postRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void unlockPost(Long postId) {
        Post post = postRepository.findByPostIdAndDeletedFalse(postId)
                                  .orElseThrow(() -> new BaseException(ErrorCode.POST_NOT_FOUND));
        if (!post.getLock() || post.getLockTime().isAfter(LocalDateTime.now())) {
            throw new BaseException(ErrorCode.POST_NOT_LOCK);
        }
        if (!userRepository.existsByMoneyGreaterThanEqualAndUserIdAndDeletedFalse(post.getUnlockFee().doubleValue(),
                                                                                  super.getCurrentUserId())) {
            throw new BaseException(ErrorCode.INSUFFICIENT_BALANCE);
        }
        String session = UUID.randomUUID().toString();
        Transaction transaction = new Transaction();
        transaction.setCost(post.getUnlockFee().doubleValue());
        transaction.setUserId(super.getCurrentUserId());
        transaction.setUserCode(super.getCurrentUserCode());
        transaction.setType(Constant.UNLOCK_POST_TYPE);
        transaction.setSession(session);
        transaction.setStatus(Constant.SUCCESS);
        transaction = transactionRepository.save(transaction);
        UnlockPost unlockPost = new UnlockPost();
        unlockPost.setPostId(postId);
        unlockPost.setCost(post.getUnlockFee());
        unlockPost.setGroupId(post.getGroupId());
        unlockPost.setGroupCode(post.getGroupCode());
        unlockPost.setUserId(super.getCurrentUserId());
        unlockPost.setUserCode(super.getCurrentUserCode());
        unlockPost.setUnlockTime(LocalDateTime.now());
        unlockPost.setTransaction(transaction.getTransactionId());
        unlockPost = unlockPostRepository.save(unlockPost);
        Map<String, Object> map = new HashMap<>();
        map.put("SESSION", session);
        map.put("UNLOCK_POST", unlockPost);
        applicationEventPublisher.publishEvent(new EventInfo(map, Event.UNLOCK_POST));
    }
}
