package com.codergeezer.contenthub.repository;

import com.codergeezer.contenthub.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author haidv
 * @version 1.0
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query(value = "select nextval('group_code_seq')", nativeQuery = true)
    long findGroupCodeSequenceNextVal();

    Optional<Group> findByDeletedFalseAndAdminId(Long adminId);

    Optional<Group> findByDeletedFalseAndGroupId(Long groupId);

    Page<Group> findByDeletedFalse(Pageable pageable);
}
