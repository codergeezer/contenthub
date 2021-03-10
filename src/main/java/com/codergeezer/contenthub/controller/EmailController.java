package com.codergeezer.contenthub.controller;

import com.codergeezer.contenthub.service.EmailService;
import com.codergeezer.core.base.data.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haidv
 * @version 1.0
 */
@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/api/public/v1/emails")
    public ResponseEntity<?> createGroup() {
        emailService.createEmail(null);
        return ResponseUtils.success();
    }
}
