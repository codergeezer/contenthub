package com.codergeezer.contenthub.service;

import com.codergeezer.contenthub.dto.request.GroupRequest;
import com.codergeezer.contenthub.dto.response.GroupResponse;
import com.codergeezer.core.base.data.PagingParam;
import com.codergeezer.core.base.data.ResponsePage;

/**
 * @author haidv
 * @version 1.0
 */
public interface GroupService {

    void createGroup(GroupRequest groupRequest);

    GroupResponse getGroupInfo();

    ResponsePage<GroupResponse> getGroups(PagingParam pagingParam);

    GroupResponse getGroup(Long groupId);

    void updateGroup(Long groupId, GroupRequest groupRequest);
}
