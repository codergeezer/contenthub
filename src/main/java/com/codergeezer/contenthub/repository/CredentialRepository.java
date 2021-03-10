package com.codergeezer.contenthub.repository;

import com.codergeezer.contenthub.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author haidv
 * @version 1.0
 */
@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    boolean existsByDeletedFalseAndUserName(String username);

    Optional<Credential> findByUserNameAndDeletedFalse(String username);

    Optional<Credential> findByDeletedFalseAndUserId(Long userId);
}
