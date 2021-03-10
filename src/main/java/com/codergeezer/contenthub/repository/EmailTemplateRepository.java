package com.codergeezer.contenthub.repository;

import com.codergeezer.contenthub.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author haidv
 * @version 1.0
 */
@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

    List<EmailTemplate> findByDeletedFalse();

    boolean existsByCodeAndDeletedFalse(String code);

    Optional<EmailTemplate> findByEmailTemplateIdAndDeletedFalse(Long templateId);
}
