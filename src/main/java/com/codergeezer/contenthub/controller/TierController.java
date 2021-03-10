package com.codergeezer.contenthub.controller;

import com.codergeezer.contenthub.dto.request.TierRequest;
import com.codergeezer.contenthub.dto.response.TierResponse;
import com.codergeezer.contenthub.service.TierService;
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
public class TierController {

    private final TierService tierService;

    @Autowired
    public TierController(TierService tierService) {
        this.tierService = tierService;
    }

    @PreAuthorize("hasRole(T(com.codergeezer.contenthub.utils.Constant).GROUP_ADMIN)")
    @PostMapping("/api/v1/tiers")
    public ResponseEntity<ResponseData<Object>> createTier(@RequestBody TierRequest tierRequest) {
        tierService.createTier(tierRequest);
        return ResponseUtils.created();
    }

    @GetMapping("/api/v1/tiers")
    public ResponseEntity<ResponseData<ResponsePage<TierResponse>>> getTiers(@ModelAttribute PagingParam pagingParam) {
        return ResponseUtils.success(tierService.getTiers(pagingParam));
    }
}
