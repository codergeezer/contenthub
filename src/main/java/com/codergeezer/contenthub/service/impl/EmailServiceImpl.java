package com.codergeezer.contenthub.service.impl;

import com.codergeezer.contenthub.dto.request.EmailRequest;
import com.codergeezer.contenthub.dto.response.EmailResponse;
import com.codergeezer.contenthub.repository.EmailRepository;
import com.codergeezer.contenthub.repository.EmailSendFailedRepository;
import com.codergeezer.contenthub.repository.EmailSendRepository;
import com.codergeezer.contenthub.service.EmailService;
import com.codergeezer.contenthub.service.PrincipalProvider;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author haidv
 * @version 1.0
 */
@Service
public class EmailServiceImpl extends PrincipalProvider implements EmailService {

    private final EmailRepository emailRepository;

    private final EmailSendFailedRepository emailSendFailedRepository;

    private final EmailSendRepository emailSendRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public EmailServiceImpl(EmailRepository emailRepository,
                            EmailSendFailedRepository emailSendFailedRepository,
                            EmailSendRepository emailSendRepository,
                            ApplicationEventPublisher applicationEventPublisher) {
        this.emailRepository = emailRepository;
        this.emailSendFailedRepository = emailSendFailedRepository;
        this.emailSendRepository = emailSendRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void createEmail(EmailRequest emailRequest) {
    }

    @Override
    public List<EmailResponse> historySendEmailSuccess(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public List<EmailResponse> historySendEmailFailed(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public void resendEmail(Long emailId) {

    }
}
