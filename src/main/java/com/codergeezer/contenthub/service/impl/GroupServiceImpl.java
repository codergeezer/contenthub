package com.codergeezer.contenthub.service.impl;

import com.codergeezer.contenthub.dto.request.GroupRequest;
import com.codergeezer.contenthub.dto.response.GroupResponse;
import com.codergeezer.contenthub.entity.Group;
import com.codergeezer.contenthub.enums.ErrorCode;
import com.codergeezer.contenthub.repository.GroupRepository;
import com.codergeezer.contenthub.repository.UserRepository;
import com.codergeezer.contenthub.service.GroupService;
import com.codergeezer.contenthub.service.PrincipalProvider;
import com.codergeezer.core.base.data.PagingParam;
import com.codergeezer.core.base.data.ResponsePage;
import com.codergeezer.core.base.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author haidv
 * @version 1.0
 */
@Service
public class GroupServiceImpl extends PrincipalProvider implements GroupService {

    private final GroupRepository groupRepository;

    private final UserRepository userRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository,
                            UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void createGroup(GroupRequest groupRequest) {
        userRepository.findByDeletedFalseAndUserId(super.getCurrentUserId())
                      .ifPresentOrElse(v -> {
                          if (v.isBelongGroup()) {
                              throw new BaseException(ErrorCode.USER_HAS_GROUP);
                          }
                          v.setBelongGroup(true);
                          userRepository.save(v);
                      }, () -> {
                          throw new BaseException(ErrorCode.USER_NOT_FOUND);
                      });
        Group group = new Group();
        group.setGroupName(groupRequest.getGroupName());
        group.setAdminCode(super.getCurrentUserCode());
        group.setAdminId(super.getCurrentUserId());
        group.setDescription(groupRequest.getDescription());
        group.setBanner(groupRequest.getBanner());
        group.setAvatar(groupRequest.getAvatar());
        groupRepository.save(group);
    }

    @Override
    public GroupResponse getGroupInfo() {
        userRepository.findByDeletedFalseAndUserId(super.getCurrentUserId())
                      .orElseThrow(() -> new BaseException(ErrorCode.USER_HAS_NOT_GROUP));
        Group group = groupRepository.findByDeletedFalseAndAdminId(super.getCurrentUserId())
                                     .orElseThrow(() -> new BaseException(ErrorCode.GROUP_NOT_FOUND));
        return super.modelMapper.convertToGroupResponse(group);
    }

    @Override
    public ResponsePage<GroupResponse> getGroups(PagingParam pagingParam) {
        Page<Group> groupPage = groupRepository.findByDeletedFalse(pagingParam.pageable());
        return new ResponsePage<>(groupPage, super.modelMapper.convertToGroupResponses(groupPage.getContent()));
    }

    @Override
    public GroupResponse getGroup(Long groupId) {
        Group group = groupRepository.findByDeletedFalseAndGroupId(groupId)
                                     .orElseThrow(() -> new BaseException(ErrorCode.GROUP_NOT_FOUND));
        return super.modelMapper.convertToGroupResponse(group);
    }

    @Override
    public void updateGroup(Long groupId, GroupRequest groupRequest) {

    }
}
