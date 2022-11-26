package com.navigation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import com.navigation.model.BaseStation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.UUID;

@DataJpaTest
class BaseStationRepositoryTest {
    @Autowired
    BaseStationRepository bsRepo;

    @Test
    void saveTest() {
        UUID bsID = UUID.randomUUID();
        String bsName = "Askeladd";
        float x = 5f;
        float y = 5f;
        float radius = 10f;
        BaseStation bs = bsRepo.save(new BaseStation(bsID, bsName, x, y, radius));

        assertThat(bs).hasFieldOrPropertyWithValue("id", bsID);
        assertThat(bs).hasFieldOrPropertyWithValue("x", x);
        assertThat(bs).hasFieldOrPropertyWithValue("y",  y);
        assertThat(bs).hasFieldOrPropertyWithValue("detectionRadiusInMeters", radius);
    }
}