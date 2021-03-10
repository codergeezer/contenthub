package com.codergeezer.contenthub.service;

import com.codergeezer.contenthub.dto.request.TierRequest;
import com.codergeezer.contenthub.dto.response.TierResponse;
import com.codergeezer.core.base.data.PagingParam;
import com.codergeezer.core.base.data.ResponsePage;

/**
 * @author haidv
 * @version 1.0
 */
public interface TierService {

    void createTier(TierRequest tierRequest);

    ResponsePage<TierResponse> getTiers(PagingParam pagingParam);
}
