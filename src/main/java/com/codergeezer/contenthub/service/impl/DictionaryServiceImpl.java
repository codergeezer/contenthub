package com.codergeezer.contenthub.service.impl;

import com.codergeezer.contenthub.dto.response.DictionaryResponse;
import com.codergeezer.contenthub.repository.DictionaryRepository;
import com.codergeezer.contenthub.service.DictionaryService;
import com.codergeezer.contenthub.service.PrincipalProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haidv
 * @version 1.0
 */
@Service
public class DictionaryServiceImpl extends PrincipalProvider implements DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    @Autowired
    public DictionaryServiceImpl(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    @Cacheable(cacheNames = "cache-dictionary")
    @Override
    public List<DictionaryResponse> findDictionaries() {
        return super.modelMapper.convertToDictionaryResponses(dictionaryRepository.findByDeletedFalse());
    }

    @Cacheable(cacheNames = "cache-dictionary", key = "#groupCodes")
    @Override
    public List<DictionaryResponse> findDictionariesByGroupCodes(List<String> groupCodes) {
        if (CollectionUtils.isEmpty(groupCodes)) {
            return new ArrayList<>();
        }
        return super.modelMapper
                .convertToDictionaryResponses(dictionaryRepository.findByDeletedFalseAndDictionaryGroupCodeIn(groupCodes));
    }

    @Cacheable(cacheNames = "cache-dictionary", key = "#{codes}")
    @Override
    public List<DictionaryResponse> findDictionariesByCodes(List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return new ArrayList<>();
        }
        return super.modelMapper
                .convertToDictionaryResponses(dictionaryRepository.findByDeletedFalseAndDictionaryCodeIn(codes));
    }

    @Cacheable(cacheNames = "cache-dictionary", key = "#{codes + '-' + groupCodes}")
    @Override
    public List<DictionaryResponse> findDictionariesByGroupCodesAndCodes(List<String> groupCodes, List<String> codes) {
        if (CollectionUtils.isEmpty(groupCodes) || CollectionUtils.isEmpty(codes)) {
            return new ArrayList<>();
        }
        return super.modelMapper
                .convertToDictionaryResponses(
                        dictionaryRepository
                                .findByDeletedFalseAndDictionaryCodeInAndDictionaryGroupCodeIn(codes, groupCodes));
    }
}
