package com.navigation.repository;

import com.navigation.model.BaseStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

public interface BaseStationRepository extends JpaRepository<BaseStation, UUID> {
    @Transactional
    BaseStation save(BaseStation bs);
}
