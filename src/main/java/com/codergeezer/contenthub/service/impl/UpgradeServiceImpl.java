package com.codergeezer.contenthub.service.impl;

import com.codergeezer.contenthub.entity.Transaction;
import com.codergeezer.contenthub.entity.Upgrade;
import com.codergeezer.contenthub.enums.ErrorCode;
import com.codergeezer.contenthub.event.Event;
import com.codergeezer.contenthub.repository.*;
import com.codergeezer.contenthub.service.PrincipalProvider;
import com.codergeezer.contenthub.service.UpgradeService;
import com.codergeezer.contenthub.utils.Constant;
import com.codergeezer.core.base.event.EventInfo;
import com.codergeezer.core.base.exception.BaseException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UpgradeServiceImpl extends PrincipalProvider implements UpgradeService {

    private final GroupRepository groupRepository;

    private final SubscribeRepository subscribeRepository;

    private final TierRepository tierRepository;

    private final UpgradeRepository upgradeRepository;

    private final TransactionRepository transactionRepository;

    private final UserRepository userRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public UpgradeServiceImpl(GroupRepository groupRepository,
                              SubscribeRepository subscribeRepository,
                              TierRepository tierRepository,
                              UpgradeRepository upgradeRepository,
                              TransactionRepository transactionRepository,
                              UserRepository userRepository,
                              ApplicationEventPublisher applicationEventPublisher) {
        this.groupRepository = groupRepository;
        this.subscribeRepository = subscribeRepository;
        this.tierRepository = tierRepository;
        this.upgradeRepository = upgradeRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void upgradeTier(Long groupId, Long tierId) {
        Upgrade upgrade = new Upgrade();
        String session = UUID.randomUUID().toString();
        groupRepository.findByDeletedFalseAndGroupId(groupId)
                       .orElseThrow(() -> new BaseException(ErrorCode.GROUP_NOT_FOUND));
        tierRepository.findByTierIdAndGroupIdAndDeletedFalse(tierId, groupId)
                      .ifPresentOrElse(v -> {
                          if (v.getStartDate().isAfter(LocalDateTime.now()) ||
                              v.getEndDate().isBefore(LocalDateTime.now())) {
                              throw new BaseException(ErrorCode.TIER_INVALID);
                          }
                          if (!userRepository.existsByMoneyGreaterThanEqualAndUserIdAndDeletedFalse(v.getPrice()
                                                                                                     .doubleValue(),
                                                                                                    super.getCurrentUserId())) {
                              throw new BaseException(ErrorCode.INSUFFICIENT_BALANCE);
                          }
                          subscribeRepository.findByGroupIdAndUserIdAndDeletedFalseAndSubscribeTrue(groupId,
                                                                                                    super.getCurrentUserId())
                                             .ifPresentOrElse(v2 -> {
                                                 Transaction transaction = new Transaction();
                                                 transaction.setCost(-v.getPrice().doubleValue());
                                                 transaction.setUserId(super.getCurrentUserId());
                                                 transaction.setUserCode(super.getCurrentUserCode());
                                                 transaction.setType(Constant.UPGRADE_TYPE);
                                                 transaction.setSession(session);
                                                 transaction.setStatus(Constant.SUCCESS);
                                                 transaction = transactionRepository.save(transaction);
                                                 v2.setTierId(v.getTierId());
                                                 v2.setTierCode(v.getCode());
                                                 subscribeRepository.save(v2);
                                                 upgrade.setTierId(v.getTierId());
                                                 upgrade.setTierCode(v.getCode());
                                                 upgrade.setEndDate(LocalDateTime.now().plusHours(v.getDuration()));
                                                 upgrade.setStartDate(LocalDateTime.now());
                                                 upgrade.setGroupId(groupId);
                                                 upgrade.setGroupCode(v.getGroupCode());
                                                 upgrade.setUserId(super.getCurrentUserId());
                                                 upgrade.setUserCode(super.getCurrentUserCode());
                                                 upgrade.setTransaction(transaction.getTransactionId());
                                                 upgradeRepository.save(upgrade);
                                             }, () -> {
                                                 throw new BaseException(ErrorCode.UPGRADE_FAILED);
                                             });
                      }, () -> {
                          throw new BaseException(ErrorCode.TIER_NOT_FOUND);
                      });
        Map<String, Object> map = new HashMap<>();
        map.put("SESSION", session);
        map.put("UPGRADE", upgrade);
        applicationEventPublisher.publishEvent(new EventInfo(map, Event.UPGRADE_TIER));
    }
}
