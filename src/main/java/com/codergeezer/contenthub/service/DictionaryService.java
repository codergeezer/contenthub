package com.codergeezer.contenthub.service;

import com.codergeezer.contenthub.dto.response.DictionaryResponse;

import java.util.Collections;
import java.util.List;

/**
 * @author haidv
 * @version 1.0
 */
public interface DictionaryService {

    List<DictionaryResponse> findDictionaries();

    List<DictionaryResponse> findDictionariesByGroupCodes(List<String> groupCodes);

    List<DictionaryResponse> findDictionariesByCodes(List<String> codes);

    List<DictionaryResponse> findDictionariesByGroupCodesAndCodes(List<String> groupCodes, List<String> codes);

    default List<DictionaryResponse> findDictionariesByGroupCodeAndCode(String groupCode, String code) {
        return findDictionariesByGroupCodesAndCodes(Collections.singletonList(code),
                                                    Collections.singletonList(groupCode));
    }

    default List<DictionaryResponse> findDictionariesByGroupCode(String groupCode) {
        return findDictionariesByGroupCodes(Collections.singletonList(groupCode));
    }

    default List<DictionaryResponse> findDictionariesByCode(String code) {
        return findDictionariesByCodes(Collections.singletonList(code));
    }
}
