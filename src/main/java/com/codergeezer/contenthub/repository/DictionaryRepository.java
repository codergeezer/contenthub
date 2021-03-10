package com.codergeezer.contenthub.repository;

import com.codergeezer.contenthub.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author haidv
 * @version 1.0
 */
@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

    List<Dictionary> findByDeletedFalse();

    List<Dictionary> findByDeletedFalseAndDictionaryGroupCodeIn(List<String> groupCodes);

    List<Dictionary> findByDeletedFalseAndDictionaryCodeInAndDictionaryGroupCodeIn(List<String> codes,
                                                                                   List<String> groupCode);

    List<Dictionary> findByDeletedFalseAndDictionaryCodeIn(List<String> codes);
}
