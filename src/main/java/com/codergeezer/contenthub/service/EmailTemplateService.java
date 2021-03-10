package com.codergeezer.contenthub.service;

import com.codergeezer.contenthub.dto.request.EmailTemplateRequest;
import com.codergeezer.contenthub.dto.response.EmailTemplateResponse;

import java.util.List;

/**
 * @author haidv
 * @version 1.0
 */
public interface EmailTemplateService {

    List<EmailTemplateResponse> getEmailTemplate();

    void createEmailTemplate(EmailTemplateRequest emailTemplateRequest);

    void updateEmailTemplate(Long emailTemplateId, EmailTemplateRequest emailTemplateRequest);

    void deleteEmailTemplate(Long emailTemplateId);
}
