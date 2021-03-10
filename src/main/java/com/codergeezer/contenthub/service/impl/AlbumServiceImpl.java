package com.codergeezer.contenthub.service.impl;

import com.codergeezer.contenthub.dto.request.AlbumRequest;
import com.codergeezer.contenthub.dto.response.AlbumResponse;
import com.codergeezer.contenthub.entity.Album;
import com.codergeezer.contenthub.enums.ErrorCode;
import com.codergeezer.contenthub.repository.AlbumRepository;
import com.codergeezer.contenthub.repository.TierRepository;
import com.codergeezer.contenthub.service.AlbumService;
import com.codergeezer.contenthub.service.PrincipalProvider;
import com.codergeezer.core.base.data.PagingParam;
import com.codergeezer.core.base.data.ResponsePage;
import com.codergeezer.core.base.exception.BaseException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author haidv
 * @version 1.0
 */
@Service
public class AlbumServiceImpl extends PrincipalProvider implements AlbumService {

    private final AlbumRepository albumRepository;

    private final TierRepository tierRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository,
                            TierRepository tierRepository) {
        this.albumRepository = albumRepository;
        this.tierRepository = tierRepository;
    }

    @Override
    public void createAlbum(AlbumRequest albumRequest) {
        if (!super.hasCreateGroup()) {
            throw new BaseException(ErrorCode.USER_HAS_NOT_GROUP);
        }
        if (albumRequest.getTierId() != null) {
            tierRepository.findByTierIdAndGroupIdAndDeletedFalse(albumRequest.getTierId(),
                                                                 super.getCurrentGroupId())
                          .orElseThrow(() -> new BaseException(ErrorCode.TIER_NOT_FOUND));
        }
        Album album = super.modelMapper.convertToAlbum(albumRequest);
        album.setGroupId(super.getCurrentGroupId());
        album.setGroupCode(super.getCurrentGroupCode());
        album.setOwnerId(super.getCurrentUserId());
        album.setOwnerCode(super.getCurrentUserCode());
        albumRepository.save(album);
    }

    @Override
    public ResponsePage<AlbumResponse> getAlbums(PagingParam pagingParam) {
        if (!super.hasCreateGroup()) {
            throw new BaseException(ErrorCode.USER_HAS_NOT_GROUP);
        }
        Page<Album> albumPage = albumRepository.findByDeletedFalseAndGroupId(super.getCurrentGroupId(),
                                                                             pagingParam.pageable());
        return new ResponsePage<>(albumPage, super.modelMapper.convertToAlbumResponses(albumPage.getContent()));
    }

    @Override
    public ResponsePage<AlbumResponse> getAlbums(Long groupId, PagingParam pagingParam) {
        Page<Album> albumPage = albumRepository.findByDeletedFalseAndGroupId(groupId, pagingParam.pageable());
        return new ResponsePage<>(albumPage, super.modelMapper.convertToAlbumResponses(albumPage.getContent()));
    }
}
