package com.codergeezer.contenthub.event;

import com.codergeezer.contenthub.dto.response.DictionaryResponse;
import com.codergeezer.contenthub.entity.*;
import com.codergeezer.contenthub.repository.*;
import com.codergeezer.contenthub.service.DictionaryService;
import com.codergeezer.contenthub.utils.Constant;
import com.codergeezer.core.base.event.DrivenEventListener;
import com.codergeezer.core.base.event.EventInfo;
import com.codergeezer.core.base.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author haidv
 * @version 1.0
 */
@Component
public class ServiceDrivenEventListener extends DrivenEventListener {

    private final PostRepository postRepository;

    private final AlbumRepository albumRepository;

    private final UserRepository userRepository;

    private final TierRepository tierRepository;

    private final GroupRepository groupRepository;

    private final TransactionRepository transactionRepository;

    private final DictionaryService dictionaryService;

    private final EventFailedRepository eventFailedRepository;

    private final TaskExecutor taskExecutor;

    @Autowired
    public ServiceDrivenEventListener(PostRepository postRepository,
                                      AlbumRepository albumRepository,
                                      UserRepository userRepository,
                                      TierRepository tierRepository,
                                      GroupRepository groupRepository,
                                      TransactionRepository transactionRepository,
                                      DictionaryService dictionaryService,
                                      EventFailedRepository eventFailedRepository,
                                      @Qualifier("eventHandle") TaskExecutor taskExecutor) {
        this.postRepository = postRepository;
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
        this.tierRepository = tierRepository;
        this.groupRepository = groupRepository;
        this.transactionRepository = transactionRepository;
        this.dictionaryService = dictionaryService;
        this.eventFailedRepository = eventFailedRepository;
        this.taskExecutor = taskExecutor;
    }

    protected String className() {
        return this.getClass().getName();
    }

    @Override
    protected TaskExecutor handleEventExecutor() {
        return this.taskExecutor;
    }

    @Override
    protected void processLogHandleEventAsync(EventInfo eventInfo) {

    }

    @Override
    protected void processHandleErrorEventAsync(EventInfo eventInfo) {
        EventFailed eventFailed = new EventFailed();
        eventFailed.setEventId(eventInfo.getId());
        eventFailed.setEventData(JsonUtils.toJson(eventInfo.getWhat()));
        eventFailed.setSync(eventInfo.isSync());
        eventFailed.setTotalRetry((short) eventInfo.getRetry());
        eventFailed.setEventName(eventInfo.getEvent().getEventName());
        if (eventInfo.getEvent().getHandleEventClassName() == null) {
            eventFailed.setClassHandle(className());
        } else {
            eventFailed.setClassHandle(eventInfo.getEvent().getHandleEventClassName());
        }
        eventFailed.setFunctionHandle(eventInfo.getEvent().getHandleEventFunctionName());
        eventFailedRepository.save(eventFailed);
    }

    @SuppressWarnings({"unchecked", "unused"})
    public void handleEventUpdateTotalLike(Object data) {
        Map<String, Object> map = (Map<String, Object>) data;
        Post post = (Post) map.get("POST");
        if ((Boolean) map.get("LIKE")) {
            postRepository.plusTotalLike(post.getPostId());
            if (post.getAlbumId() != null) {
                albumRepository.plusTotalLike(post.getAlbumId());
            }
        } else {
            postRepository.minusTotalLike(post.getPostId());
            if (post.getAlbumId() != null) {
                albumRepository.minusTotalLike(post.getAlbumId());
            }
        }
    }

    @Transactional
    @SuppressWarnings({"unused"})
    public void handleEventUpdateTotalRead(Object data) {
        Post post = (Post) data;
        postRepository.plusTotalRead(post.getPostId());
        if (post.getAlbumId() != null) {
            albumRepository.plusTotalRead(post.getAlbumId());
        }
    }

    @Transactional
    @SuppressWarnings({"unchecked", "unused"})
    public void handleEventUpdateTotalComment(Object data) {
        Map<String, Object> map = (Map<String, Object>) data;
        Post post = (Post) map.get("POST");
        if ("ADD".equals(map.get("ACTION"))) {
            postRepository.plusTotalComment(post.getPostId());
            if (post.getAlbumId() != null) {
                albumRepository.plusTotalComment(post.getAlbumId());
            }
        } else {
            postRepository.minusTotalComment(post.getPostId());
            if (post.getAlbumId() != null) {
                albumRepository.minusTotalComment(post.getAlbumId());
            }
        }
    }

    @Transactional
    @SuppressWarnings({"unchecked", "unused"})
    public void handleEventUpgradeTier(Object data) {
        Map<String, Object> map = (Map<String, Object>) data;
        Upgrade upgrade = (Upgrade) map.get("UPGRADE");
        String session = (String) map.get("SESSION");
        double finalFee = getServiceFee();
        tierRepository.findByCodeAndDeletedFalse(upgrade.getTierCode())
                      .ifPresent(v -> {
                          userRepository.updateAccountBalance(-v.getPrice().doubleValue(), upgrade.getUserId());
                          groupRepository.findByDeletedFalseAndGroupId(upgrade.getGroupId())
                                         .ifPresent(v2 -> {
                                             Long cost = v.getPrice();
                                             double rate = (double) Math.round((cost - cost * finalFee) * 100) / 100;
                                             saveTransaction(v2, session, Constant.RECEIVE_UPGRADE_TYPE, rate);
                                         });
                      });
    }

    @Transactional
    @SuppressWarnings({"unchecked", "unused"})
    public void handleEventDonate(Object data) {
        Map<String, Object> map = (Map<String, Object>) data;
        Donate donate = (Donate) map.get("DONATE");
        String session = (String) map.get("SESSION");
        userRepository.updateAccountBalance(-donate.getCost().doubleValue(), donate.getUserId());
        groupRepository.findByDeletedFalseAndGroupId(donate.getGroupId())
                       .ifPresent(v2 -> saveTransaction(v2, session, Constant.RECEIVE_DONATE_TYPE,
                                                        donate.getCost().doubleValue()));
        if (donate.getPostId() != null) {
            postRepository.plusTotalDonate(donate.getCost(), donate.getPostId());
        }
        if (donate.getAlbumId() != null) {
            albumRepository.plusTotalDonate(donate.getCost(), donate.getAlbumId());
        }
    }

    @Transactional
    @SuppressWarnings({"unchecked", "unused"})
    public void handleEventUnlockPost(Object data) {
        Map<String, Object> map = (Map<String, Object>) data;
        UnlockPost unlockPost = (UnlockPost) map.get("UNLOCK_POST");
        String session = (String) map.get("SESSION");
        double finalFee = getServiceFee();
        userRepository.updateAccountBalance(-unlockPost.getCost().doubleValue(), unlockPost.getUserId());
        groupRepository.findByDeletedFalseAndGroupId(unlockPost.getGroupId())
                       .ifPresent(v -> {
                           Long cost = unlockPost.getCost();
                           double rate = (double) Math.round((cost - cost * finalFee) * 100) / 100;
                           saveTransaction(v, session, Constant.RECEIVE_UNLOCK_POST_TYPE, rate);
                       });
    }

    private void saveTransaction(Group v, String session, Integer type, double cost) {
        userRepository.updateAccountBalance(cost, v.getAdminId());
        Transaction transaction = new Transaction();
        transaction.setCost(+cost);
        transaction.setUserId(v.getAdminId());
        transaction.setUserCode(v.getAdminCode());
        transaction.setType(type);
        transaction.setSession(session);
        transaction.setStatus(Constant.SUCCESS);
        transactionRepository.save(transaction);
    }

    private double getServiceFee() {
        double fee = 0;
        List<DictionaryResponse> dictionaryResponses = dictionaryService
                .findDictionariesByGroupCode(Constant.SERVICE_FEE_DICTIONARY_GROUP_CODE);
        if (!CollectionUtils.isEmpty(dictionaryResponses)) {
            fee = Double.parseDouble(dictionaryResponses.get(0).getValue());
        }
        return fee;
    }
}
