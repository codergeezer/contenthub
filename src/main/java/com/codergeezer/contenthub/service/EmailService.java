package com.codergeezer.contenthub.service;

import com.codergeezer.contenthub.dto.request.EmailRequest;
import com.codergeezer.contenthub.dto.response.EmailResponse;

import java.time.LocalDate;
import java.util.List;

/**
 * @author haidv
 * @version 1.0
 */
public interface EmailService {

    void createEmail(EmailRequest emailRequest);

    List<EmailResponse> historySendEmailSuccess(LocalDate startDate, LocalDate endDate);

    List<EmailResponse> historySendEmailFailed(LocalDate startDate, LocalDate endDate);

    void resendEmail(Long emailId);
}
