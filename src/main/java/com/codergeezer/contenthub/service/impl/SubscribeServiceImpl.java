package com.codergeezer.contenthub.service.impl;

import com.codergeezer.contenthub.dto.response.DictionaryResponse;
import com.codergeezer.contenthub.entity.Group;
import com.codergeezer.contenthub.entity.Subscribe;
import com.codergeezer.contenthub.enums.ErrorCode;
import com.codergeezer.contenthub.repository.GroupRepository;
import com.codergeezer.contenthub.repository.SubscribeRepository;
import com.codergeezer.contenthub.repository.TierRepository;
import com.codergeezer.contenthub.service.DictionaryService;
import com.codergeezer.contenthub.service.PrincipalProvider;
import com.codergeezer.contenthub.service.SubscribeService;
import com.codergeezer.contenthub.utils.Constant;
import com.codergeezer.core.base.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SubscribeServiceImpl extends PrincipalProvider implements SubscribeService {

    private final GroupRepository groupRepository;

    private final SubscribeRepository subscribeRepository;

    private final DictionaryService dictionaryService;

    private final TierRepository tierRepository;

    @Autowired
    public SubscribeServiceImpl(GroupRepository groupRepository,
                                SubscribeRepository subscribeRepository,
                                DictionaryService dictionaryService,
                                TierRepository tierRepository) {
        this.groupRepository = groupRepository;
        this.subscribeRepository = subscribeRepository;
        this.dictionaryService = dictionaryService;
        this.tierRepository = tierRepository;
    }

    @Override
    public void subscribeGroup(Long groupId) {
        Group group = groupRepository.findByDeletedFalseAndGroupId(groupId)
                                     .orElseThrow(() -> new BaseException(ErrorCode.GROUP_NOT_FOUND));
        subscribeRepository.findByGroupIdAndUserIdAndDeletedFalseAndSubscribeTrue(groupId, super.getCurrentUserId())
                           .orElseThrow(() -> new BaseException(ErrorCode.SUBSCRIBED_TO_GROUP));
        Subscribe subscribe = new Subscribe();
        subscribe.setGroupId(groupId);
        subscribe.setGroupCode(group.getCode());
        subscribe.setUserId(super.getCurrentUserId());
        subscribe.setUserCode(super.getCurrentUserCode());
        List<DictionaryResponse> dictionaryResponses = dictionaryService
                .findDictionariesByGroupCode(Constant.TIER_DEFAULT_DICTIONARY_GROUP_CODE);
        if (!CollectionUtils.isEmpty(dictionaryResponses)) {
            DictionaryResponse dictionary = dictionaryResponses.get(0);
            tierRepository.findByCodeAndDeletedFalse(dictionary.getValue())
                          .ifPresent(v -> {
                              subscribe.setTierId(v.getTierId());
                              subscribe.setTierCode(v.getCode());
                          });
        }
        subscribeRepository.save(subscribe);
    }

    @Override
    public void unsubscribeGroup(Long groupId) {
        groupRepository.findByDeletedFalseAndGroupId(groupId)
                       .orElseThrow(() -> new BaseException(ErrorCode.GROUP_NOT_FOUND));
        subscribeRepository.findByGroupIdAndUserIdAndDeletedFalseAndSubscribeTrue(groupId, super.getCurrentUserId())
                           .ifPresentOrElse(v -> {
                               v.setSubscribe(false);
                               subscribeRepository.save(v);
                           }, () -> {
                               throw new BaseException(ErrorCode.UNSUBSCRIBED_TO_GROUP);
                           });
    }
}
