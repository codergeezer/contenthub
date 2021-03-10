package com.codergeezer.contenthub.controller;

import com.codergeezer.contenthub.dto.request.CommentRequest;
import com.codergeezer.contenthub.dto.request.PostRequest;
import com.codergeezer.contenthub.dto.response.CommentResponse;
import com.codergeezer.contenthub.dto.response.PostResponse;
import com.codergeezer.contenthub.service.*;
import com.codergeezer.core.base.data.PagingParam;
import com.codergeezer.core.base.data.ResponseData;
import com.codergeezer.core.base.data.ResponsePage;
import com.codergeezer.core.base.data.ResponseUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author haidv
 * @version 1.0
 */
@RestController
public class PostController {

    private final PostService postService;

    private final LikeService likeService;

    private final CommentService commentService;

    private final DonateService donateService;

    private final ReadService readService;

    private final UnlockPostService unlockPostService;

    @Autowired
    public PostController(PostService postService, LikeService likeService,
                          CommentService commentService, DonateService donateService,
                          ReadService readService,
                          UnlockPostService unlockPostService) {
        this.postService = postService;
        this.likeService = likeService;
        this.commentService = commentService;
        this.donateService = donateService;
        this.readService = readService;
        this.unlockPostService = unlockPostService;
    }

    @GetMapping("/api/v1/feeds")
    public ResponseEntity<ResponseData<List<PostResponse>>> getFeeds(@RequestParam boolean filter,
                                                                     @ModelAttribute PagingParam pagingParam) {
        return ResponseUtils.success(postService.getFeeds(filter, pagingParam));
    }

    @PreAuthorize("hasRole(T(com.codergeezer.contenthub.utils.Constant).GROUP_ADMIN)")
    @PostMapping("/api/v1/posts")
    public ResponseEntity<ResponseData<Object>> createPost(@RequestBody PostRequest postRequest)
            throws SchedulerException {
        postService.createPost(postRequest);
        return ResponseUtils.created();
    }

    @PreAuthorize("hasRole(T(com.codergeezer.contenthub.utils.Constant).GROUP_ADMIN)")
    @PutMapping("/api/v1/posts")
    public ResponseEntity<ResponseData<Object>> updatePost(@RequestBody PostRequest postRequest) {
        postService.updatePost(postRequest);
        return ResponseUtils.success();
    }

    @GetMapping("/api/v1/posts")
    public ResponseEntity<ResponseData<ResponsePage<PostResponse>>> getPosts(@ModelAttribute PagingParam pagingParam) {
        return ResponseUtils.success(postService.getPosts(pagingParam));
    }

    @PostMapping("/api/v1/posts/{postId}/like")
    public ResponseEntity<ResponseData<Object>> likePost(@PathVariable Long postId) {
        likeService.likePost(postId);
        return ResponseUtils.success();
    }

    @PutMapping("/api/v1/posts/{postId}/unlike")
    public ResponseEntity<ResponseData<Object>> unLikePost(@PathVariable Long postId) {
        likeService.unLikePost(postId);
        return ResponseUtils.success();
    }

    @PutMapping("/api/v1/posts/{postId}/read")
    public ResponseEntity<ResponseData<Object>> readPost(@PathVariable Long postId) {
        readService.readPost(postId);
        return ResponseUtils.success();
    }

    @PostMapping("/api/v1/posts/{postId}/donate")
    public ResponseEntity<ResponseData<Object>> donatePost(@PathVariable Long postId, @RequestParam Long cost) {
        donateService.donatePost(postId, cost);
        return ResponseUtils.success();
    }

    @PostMapping("/api/v1/posts/{postId}/unlock")
    public ResponseEntity<ResponseData<Object>> unlockPost(@PathVariable Long postId) {
        unlockPostService.unlockPost(postId);
        return ResponseUtils.success();
    }

    @PostMapping("/api/v1/posts/{postId}/comments")
    public ResponseEntity<ResponseData<Object>> createComment(@PathVariable Long postId,
                                                              @RequestBody CommentRequest commentRequest) {
        commentService.createComment(postId, commentRequest);
        return ResponseUtils.success();
    }

    @GetMapping("/api/v1/posts/{postId}/comments")
    public ResponseEntity<ResponseData<ResponsePage<CommentResponse>>> getComments(@PathVariable Long postId,
                                                                                   @ModelAttribute PagingParam pagingParam) {
        return ResponseUtils.success(commentService.getComments(postId, pagingParam));
    }

    @PutMapping("/api/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ResponseData<Object>> editComment(@PathVariable Long postId,
                                                            @PathVariable Long commentId,
                                                            @RequestBody CommentRequest commentRequest) {
        commentService.editComment(postId, commentId, commentRequest);
        return ResponseUtils.success();
    }

    @DeleteMapping("/api/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ResponseData<Object>> deleteComment(@PathVariable Long postId,
                                                              @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseUtils.success();
    }
}
