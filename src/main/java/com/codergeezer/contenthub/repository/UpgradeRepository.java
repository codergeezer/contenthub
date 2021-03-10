package com.codergeezer.contenthub.repository;

import com.codergeezer.contenthub.entity.Upgrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpgradeRepository extends JpaRepository<Upgrade, Long> {

}
