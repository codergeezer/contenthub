package com.codergeezer.contenthub.repository;

import com.codergeezer.contenthub.entity.EmailSend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * @author haidv
 * @version 1.0
 */
@Repository
public interface EmailSendRepository extends JpaRepository<EmailSend, Long> {

    Page<EmailSend> findBySendTimeBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
