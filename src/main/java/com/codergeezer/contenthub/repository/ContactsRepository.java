package com.codergeezer.contenthub.repository;

import com.codergeezer.contenthub.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author haidv
 * @version 1.0
 */
@Repository
public interface ContactsRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByContactAndTypeAndDeletedFalse(String email, Integer type);

    boolean existsByContactAndTypeAndDeletedFalse(String email, Integer type);

    Optional<Contact> findByUserIdAndPrimaryTrueAndDeletedFalse(Long userId);
}
