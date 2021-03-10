package com.codergeezer.contenthub.controller;

import com.codergeezer.contenthub.dto.request.AlbumRequest;
import com.codergeezer.contenthub.dto.response.AlbumResponse;
import com.codergeezer.contenthub.dto.response.PostResponse;
import com.codergeezer.contenthub.service.AlbumService;
import com.codergeezer.contenthub.service.DonateService;
import com.codergeezer.contenthub.service.PostService;
import com.codergeezer.core.base.data.PagingParam;
import com.codergeezer.core.base.data.ResponseData;
import com.codergeezer.core.base.data.ResponsePage;
import com.codergeezer.core.base.data.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author haidv
 * @version 1.0
 */
@RestController
public class AlbumController {

    private final AlbumService albumService;

    private final DonateService donateService;

    private final PostService postService;

    @Autowired
    public AlbumController(AlbumService albumService, DonateService donateService,
                           PostService postService) {
        this.albumService = albumService;
        this.donateService = donateService;
        this.postService = postService;
    }

    @PreAuthorize("hasRole(T(com.codergeezer.contenthub.utils.Constant).GROUP_ADMIN)")
    @PostMapping("/api/v1/albums")
    public ResponseEntity<ResponseData<Object>> createAlbum(@RequestBody AlbumRequest albumRequest) {
        albumService.createAlbum(albumRequest);
        return ResponseUtils.created();
    }

    @GetMapping("/api/v1/albums")
    public ResponseEntity<ResponseData<ResponsePage<AlbumResponse>>> getAlbums(
            @ModelAttribute PagingParam pagingParam) {
        return ResponseUtils.success(albumService.getAlbums(pagingParam));
    }

    @PostMapping("/api/v1/albums/{albumId}/donates")
    public ResponseEntity<ResponseData<Object>> donateAlbum(@PathVariable Long albumId, @RequestParam Long cost) {
        donateService.donateAlbum(albumId, cost);
        return ResponseUtils.success();
    }

    @GetMapping("/api/v1/albums/{albumId}/posts")
    public ResponseEntity<ResponseData<ResponsePage<PostResponse>>> getPosts(@PathVariable Long albumId,
                                                                             @ModelAttribute PagingParam pagingParam) {
        return ResponseUtils.success(postService.getPosts(albumId, pagingParam));
    }
}
