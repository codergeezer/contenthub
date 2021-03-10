package com.codergeezer.contenthub.service;

import com.codergeezer.contenthub.dto.request.AlbumRequest;
import com.codergeezer.contenthub.dto.response.AlbumResponse;
import com.codergeezer.core.base.data.PagingParam;
import com.codergeezer.core.base.data.ResponsePage;

/**
 * @author haidv
 * @version 1.0
 */
public interface AlbumService {

    void createAlbum(AlbumRequest albumRequest);

    ResponsePage<AlbumResponse> getAlbums(PagingParam pagingParam);

    ResponsePage<AlbumResponse> getAlbums(Long groupId, PagingParam pagingParam);
}
