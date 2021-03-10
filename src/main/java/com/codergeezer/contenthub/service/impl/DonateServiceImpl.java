package com.codergeezer.contenthub.service.impl;

import com.codergeezer.contenthub.entity.*;
import com.codergeezer.contenthub.enums.ErrorCode;
import com.codergeezer.contenthub.event.Event;
import com.codergeezer.contenthub.repository.*;
import com.codergeezer.contenthub.service.DonateService;
import com.codergeezer.contenthub.service.PrincipalProvider;
import com.codergeezer.contenthub.utils.Constant;
import com.codergeezer.core.base.event.EventInfo;
import com.codergeezer.core.base.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author haidv
 * @version 1.0
 */
@Service
public class DonateServiceImpl extends PrincipalProvider implements DonateService {

    private final DonateRepository donateRepository;

    private final PostRepository postRepository;

    private final GroupRepository groupRepository;

    private final AlbumRepository albumRepository;

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public DonateServiceImpl(DonateRepository donateRepository,
                             PostRepository postRepository,
                             GroupRepository groupRepository,
                             AlbumRepository albumRepository,
                             UserRepository userRepository,
                             TransactionRepository transactionRepository,
                             ApplicationEventPublisher applicationEventPublisher) {
        this.donateRepository = donateRepository;
        this.postRepository = postRepository;
        this.groupRepository = groupRepository;
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    @Override
    public void donatePost(Long postId, Long cost) {
        Post post = postRepository.findByPostIdAndDeletedFalse(postId)
                                  .orElseThrow(() -> new BaseException(ErrorCode.POST_NOT_FOUND));
        Transaction transaction = createTransaction(cost);
        Donate donate = createDonate(post.getGroupId(), post.getGroupCode(), postId, post.getAlbumId(), cost);
        publicEventDonate(transaction.getSession(), donate);
    }

    @Transactional
    @Override
    public void donateAlbum(Long albumId, Long cost) {
        Album album = albumRepository.findByAlbumIdAndDeletedFalse(albumId)
                                     .orElseThrow(() -> new BaseException(ErrorCode.ALBUM_NOT_FOUND));
        Transaction transaction = createTransaction(cost);
        Donate donate = createDonate(album.getGroupId(), album.getGroupCode(), null, albumId, cost);
        publicEventDonate(transaction.getSession(), donate);
    }

    @Transactional
    @Override
    public void donateGroup(Long groupId, Long cost) {
        Group group = groupRepository.findByDeletedFalseAndGroupId(groupId)
                                     .orElseThrow(() -> new BaseException(ErrorCode.GROUP_NOT_FOUND));
        Transaction transaction = createTransaction(cost);
        Donate donate = createDonate(group.getGroupId(), group.getCode(), null, null, cost);
        publicEventDonate(transaction.getSession(), donate);
    }

    private Transaction createTransaction(Long cost) {
        if (!userRepository.existsByMoneyGreaterThanEqualAndUserIdAndDeletedFalse(cost.doubleValue(),
                                                                                  super.getCurrentUserId())) {
            throw new BaseException(ErrorCode.INSUFFICIENT_BALANCE);
        }
        String session = UUID.randomUUID().toString();
        Transaction transaction = new Transaction();
        transaction.setUserId(super.getCurrentUserId());
        transaction.setUserCode(super.getCurrentUserCode());
        transaction.setCost(cost.doubleValue());
        transaction.setType(Constant.DONATE_TYPE);
        transaction.setStatus(Constant.SUCCESS);
        transaction.setSession(session);
        transaction = transactionRepository.save(transaction);
        return transaction;
    }

    private Donate createDonate(Long groupId, String groupCode, Long postId, Long albumId, Long cost) {
        Donate donate = new Donate();
        donate.setGroupCode(groupCode);
        donate.setGroupId(groupId);
        donate.setCost(cost);
        donate.setUserId(super.getCurrentUserId());
        donate.setUserCode(super.getCurrentUserCode());
        donate.setAlbumId(albumId);
        donate.setPostId(postId);
        donate = donateRepository.save(donate);
        return donate;
    }

    private void publicEventDonate(String session, Donate donate) {
        Map<String, Object> map = new HashMap<>();
        map.put("SESSION", session);
        map.put("DONATE", donate);
        applicationEventPublisher.publishEvent(new EventInfo(map, Event.DONATE));
    }
}
