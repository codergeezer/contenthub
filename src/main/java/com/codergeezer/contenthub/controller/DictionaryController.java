package com.codergeezer.contenthub.controller;

import com.codergeezer.contenthub.service.DictionaryService;
import com.codergeezer.core.base.data.ResponseData;
import com.codergeezer.core.base.data.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author haidv
 * @version 1.0
 */
@RestController
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/api/v1/dictionaries")
    public ResponseEntity<ResponseData<Object>> getDictionaries(@RequestParam(required = false) List<String> codes,
                                                                @RequestParam(required = false) List<String> groupCodes) {
        if (CollectionUtils.isEmpty(codes) && CollectionUtils.isEmpty(groupCodes)) {
            return ResponseUtils.success(dictionaryService.findDictionaries());
        }
        if (!CollectionUtils.isEmpty(codes) && !CollectionUtils.isEmpty(groupCodes)) {
            return ResponseUtils.success(dictionaryService.findDictionariesByGroupCodesAndCodes(groupCodes, codes));
        }
        if (!CollectionUtils.isEmpty(groupCodes)) {
            return ResponseUtils.success(dictionaryService.findDictionariesByGroupCodes(groupCodes));
        }
        if (!CollectionUtils.isEmpty(codes)) {
            return ResponseUtils.success(dictionaryService.findDictionariesByCodes(codes));
        }
        return ResponseUtils.success();
    }
}
