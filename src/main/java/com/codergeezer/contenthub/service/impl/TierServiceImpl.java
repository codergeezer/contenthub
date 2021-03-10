package com.codergeezer.contenthub.service.impl;

import com.codergeezer.contenthub.dto.request.TierRequest;
import com.codergeezer.contenthub.dto.response.TierResponse;
import com.codergeezer.contenthub.entity.Tier;
import com.codergeezer.contenthub.repository.TierRepository;
import com.codergeezer.contenthub.service.PrincipalProvider;
import com.codergeezer.contenthub.service.TierService;
import com.codergeezer.core.base.data.PagingParam;
import com.codergeezer.core.base.data.ResponsePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author haidv
 * @version 1.0
 */
@Service
public class TierServiceImpl extends PrincipalProvider implements TierService {

    private final TierRepository tierRepository;

    @Autowired
    public TierServiceImpl(TierRepository tierRepository) {
        this.tierRepository = tierRepository;
    }

    @Override
    public void createTier(TierRequest tierRequest) {
        Tier tier = super.modelMapper.convertToTier(tierRequest);
        tier.setGroupId(super.getCurrentGroupId());
        tier.setGroupCode(super.getCurrentGroupCode());
        tierRepository.save(tier);
    }

    @Override
    public ResponsePage<TierResponse> getTiers(PagingParam pagingParam) {
        return null;
    }
}
