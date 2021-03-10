package com.codergeezer.contenthub.controller;

import com.codergeezer.contenthub.dto.request.GroupRequest;
import com.codergeezer.contenthub.dto.response.AlbumResponse;
import com.codergeezer.contenthub.dto.response.GroupResponse;
import com.codergeezer.contenthub.service.*;
import com.codergeezer.core.base.data.PagingParam;
import com.codergeezer.core.base.data.ResponseData;
import com.codergeezer.core.base.data.ResponsePage;
import com.codergeezer.core.base.data.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author haidv
 * @version 1.0
 */
@RestController
public class GroupController {

    private final GroupService groupService;

    private final SubscribeService subscribeService;

    private final UpgradeService upgradeService;

    private final DonateService donateService;

    private final AlbumService albumService;

    @Autowired
    public GroupController(GroupService groupService,
                           SubscribeService subscribeService,
                           UpgradeService upgradeService,
                           DonateService donateService, AlbumService albumService) {
        this.groupService = groupService;
        this.subscribeService = subscribeService;
        this.upgradeService = upgradeService;
        this.donateService = donateService;
        this.albumService = albumService;
    }

    @GetMapping("/api/v1/groups")
    public ResponseEntity<ResponseData<ResponsePage<GroupResponse>>> getGroups(
            @ModelAttribute PagingParam pagingParam) {
        return ResponseUtils.success(groupService.getGroups(pagingParam));
    }

    @GetMapping("/api/v1/groups/{groupId}")
    public ResponseEntity<ResponseData<GroupResponse>> getGroup(Long groupId) {
        return ResponseUtils.success(groupService.getGroup(groupId));
    }

    @PostMapping("/api/v1/groups")
    public ResponseEntity<ResponseData<Object>> createGroup(@RequestBody GroupRequest groupRequest) {
        groupService.createGroup(groupRequest);
        return ResponseUtils.created();
    }

    @PutMapping("/api/v1/groups/{groupId}")
    public ResponseEntity<ResponseData<Object>> updateGroup(@PathVariable Long groupId,
                                                            @RequestBody GroupRequest groupRequest) {
        groupService.updateGroup(groupId, groupRequest);
        return ResponseUtils.success();
    }

    @PostMapping("/api/v1/groups/{groupId}/subscribe")
    public ResponseEntity<ResponseData<Object>> subscribeGroup(@PathVariable Long groupId) {
        subscribeService.subscribeGroup(groupId);
        return ResponseUtils.success();
    }

    @PostMapping("/api/v1/groups/{groupId}/unsubscribe")
    public ResponseEntity<ResponseData<Object>> unsubscribeGroup(@PathVariable Long groupId) {
        subscribeService.unsubscribeGroup(groupId);
        return ResponseUtils.success();
    }

    @PostMapping("/api/v1/groups/{groupId}/upgrade/{tierId}")
    public ResponseEntity<ResponseData<Object>> upgradeTier(@PathVariable Long groupId, @PathVariable Long tierId) {
        upgradeService.upgradeTier(groupId, tierId);
        return ResponseUtils.success();
    }

    @PostMapping("/api/v1/groups/{groupId}/donate")
    public ResponseEntity<ResponseData<Object>> donateGroup(@PathVariable Long groupId, @RequestParam Long cost) {
        donateService.donateGroup(groupId, cost);
        return ResponseUtils.success();
    }

    @GetMapping("/api/v1/groups/{groupId}/albums")
    public ResponseEntity<ResponseData<ResponsePage<AlbumResponse>>> getAlbums(@PathVariable Long groupId,
                                                                               @ModelAttribute PagingParam pagingParam) {
        return ResponseUtils.success(albumService.getAlbums(groupId, pagingParam));
    }
}
