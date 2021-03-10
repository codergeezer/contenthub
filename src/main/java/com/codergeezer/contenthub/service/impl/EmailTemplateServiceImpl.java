package com.codergeezer.contenthub.service.impl;

import com.codergeezer.contenthub.dto.request.EmailTemplateRequest;
import com.codergeezer.contenthub.dto.response.EmailTemplateResponse;
import com.codergeezer.contenthub.entity.EmailTemplate;
import com.codergeezer.contenthub.enums.ErrorCode;
import com.codergeezer.contenthub.repository.EmailTemplateRepository;
import com.codergeezer.contenthub.service.EmailTemplateService;
import com.codergeezer.contenthub.service.PrincipalProvider;
import com.codergeezer.core.base.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author haidv
 * @version 1.0
 */
@Service
public class EmailTemplateServiceImpl extends PrincipalProvider implements EmailTemplateService {

    private final EmailTemplateRepository emailTemplateRepository;

    @Autowired
    public EmailTemplateServiceImpl(EmailTemplateRepository emailTemplateRepository) {
        this.emailTemplateRepository = emailTemplateRepository;
    }

    @Override
    public List<EmailTemplateResponse> getEmailTemplate() {
        return super.modelMapper.convertToEmailTemplateResponses(emailTemplateRepository.findByDeletedFalse());
    }

    @Override
    public void createEmailTemplate(EmailTemplateRequest emailTemplateRequest) {
        if (emailTemplateRepository.existsByCodeAndDeletedFalse(emailTemplateRequest.getCode())) {
            throw new BaseException("Template code existed", ErrorCode.EMAIL_TEMPLATE_EXISTED);
        }
        emailTemplateRepository.save(super.modelMapper.convertToEmailTemplate(emailTemplateRequest));
    }

    @Override
    public void updateEmailTemplate(Long emailTemplateId, EmailTemplateRequest emailTemplateRequest) {
        EmailTemplate v =
                emailTemplateRepository.findByEmailTemplateIdAndDeletedFalse(emailTemplateId)
                                       .orElseThrow(() -> new BaseException(
                                               ErrorCode.EMAIL_TEMPLATE_NOT_EXISTED));
        if (v.getCode().equals(emailTemplateRequest.getCode()) &&
            emailTemplateRepository.existsByCodeAndDeletedFalse(emailTemplateRequest.getCode())) {
            throw new BaseException("Template code existed", ErrorCode.EMAIL_TEMPLATE_EXISTED);
        }
        emailTemplateRepository.save(super.modelMapper.convertToEmailTemplate(v, emailTemplateRequest));
    }

    @Override
    public void deleteEmailTemplate(Long emailTemplateId) {
        emailTemplateRepository.findByEmailTemplateIdAndDeletedFalse(emailTemplateId).ifPresent(v -> {
            v.setDeleted(true);
            emailTemplateRepository.save(v);
        });
    }
}
