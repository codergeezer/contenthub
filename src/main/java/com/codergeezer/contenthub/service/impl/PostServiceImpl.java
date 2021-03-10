package com.codergeezer.contenthub.service.impl;

import com.codergeezer.contenthub.dto.request.PostRequest;
import com.codergeezer.contenthub.dto.response.PostResponse;
import com.codergeezer.contenthub.entity.Post;
import com.codergeezer.contenthub.entity.Tier;
import com.codergeezer.contenthub.enums.ErrorCode;
import com.codergeezer.contenthub.quartz.JobScheduleCreator;
import com.codergeezer.contenthub.quartz.job.PublicPostJob;
import com.codergeezer.contenthub.repository.AlbumRepository;
import com.codergeezer.contenthub.repository.PostRepository;
import com.codergeezer.contenthub.repository.TierRepository;
import com.codergeezer.contenthub.service.PostService;
import com.codergeezer.contenthub.service.PrincipalProvider;
import com.codergeezer.core.base.data.PagingParam;
import com.codergeezer.core.base.data.ResponsePage;
import com.codergeezer.core.base.exception.BaseException;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @author haidv
 * @version 1.0
 */
@Service
public class PostServiceImpl extends PrincipalProvider implements PostService {

    private final PostRepository postRepository;

    private final TierRepository tierRepository;

    private final AlbumRepository albumRepository;

    private final JobScheduleCreator jobScheduleCreator;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           TierRepository tierRepository,
                           AlbumRepository albumRepository,
                           JobScheduleCreator jobScheduleCreator) {
        this.postRepository = postRepository;
        this.tierRepository = tierRepository;
        this.albumRepository = albumRepository;
        this.jobScheduleCreator = jobScheduleCreator;
    }

    @Override
    public void createPost(PostRequest postRequest) throws SchedulerException {
        if (postRequest.getAlbumId() != null &&
            albumRepository.existsByAlbumIdAndGroupIdAndDeletedFalse(postRequest.getAlbumId(),
                                                                     super.getCurrentGroupId())) {
            throw new BaseException(ErrorCode.ALBUM_NOT_FOUND);
        }
        if (postRequest.getTierId() != null) {
            Tier tier = tierRepository.findByTierIdAndGroupIdAndDeletedFalse(postRequest.getTierId(),
                                                                             super.getCurrentGroupId())
                                      .orElseThrow(() -> new BaseException(ErrorCode.TIER_NOT_FOUND));
            if (tier.getStartDate().isBefore(LocalDateTime.now()) || tier.getEndDate().isAfter(LocalDateTime.now())) {
                throw new BaseException(ErrorCode.TIER_INVALID);
            }
        }
        Post post = super.modelMapper.convertToPost(postRequest);
        post.setGroupCode(super.getCurrentGroupCode());
        post.setGroupId(super.getCurrentGroupId());
        post.setUserCode(super.getCurrentUserCode());
        post = postRepository.save(post);
        if (postRequest.getPublicDate().isAfter(LocalDateTime.now())) {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.putAsString("POST_ID", post.getPostId());
            JobDetail jobDetail = jobScheduleCreator.createJob(PublicPostJob.class, true,
                                                               PublicPostJob.class.getSimpleName() + post.getPostId(),
                                                               System.class.getSimpleName(), jobDataMap);
            SimpleTrigger simpleTrigger = jobScheduleCreator.createSimpleTrigger(
                    PublicPostJob.class.getSimpleName() + post.getPostId(),
                    Date.from(post.getPublicDate().atZone(ZoneId.systemDefault()).toInstant()), 0L, 0,
                    SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
            jobScheduleCreator.scheduleJob(jobDetail, simpleTrigger);
        }
    }

    @Override
    public ResponsePage<PostResponse> getPosts(PagingParam pagingParam) {
        return null;
    }

    @Override
    public List<PostResponse> getFeedsSubscribe(PagingParam pagingParam) {
        List<Post> postPage = postRepository.findFeedSubscribe(LocalDateTime.now(), super.getCurrentUserId());
        return null;
    }

    @Override
    public List<PostResponse> getFeedsAll(PagingParam pagingParam) {
        return null;
    }

    @Override
    public void updatePost(PostRequest postRequest) {

    }

    @Override
    public ResponsePage<PostResponse> getPosts(Long albumId, PagingParam pagingParam) {
        return null;
    }
}
