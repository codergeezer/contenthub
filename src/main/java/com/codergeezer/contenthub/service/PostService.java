package com.codergeezer.contenthub.service;

import com.codergeezer.contenthub.dto.request.PostRequest;
import com.codergeezer.contenthub.dto.response.PostResponse;
import com.codergeezer.core.base.data.PagingParam;
import com.codergeezer.core.base.data.ResponsePage;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * @author haidv
 * @version 1.0
 */
public interface PostService {

    void createPost(PostRequest postRequest) throws SchedulerException;

    ResponsePage<PostResponse> getPosts(PagingParam pagingParam);

    default List<PostResponse> getFeeds(boolean filter, PagingParam pagingParam) {
        return filter ? getFeedsSubscribe(pagingParam) : getFeedsAll(pagingParam);
    }

    List<PostResponse> getFeedsSubscribe(PagingParam pagingParam);

    List<PostResponse> getFeedsAll(PagingParam pagingParam);

    void updatePost(PostRequest postRequest);

    ResponsePage<PostResponse> getPosts(Long albumId, PagingParam pagingParam);
}
