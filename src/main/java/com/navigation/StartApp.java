package com.navigation;

import com.navigation.model.DistanceAndPosition;
import com.navigation.model.Point;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
public class StartApp {
    public static void main(String[] args) {
        SpringApplication.run(StartApp.class, args);
    }

    @Bean
    public Map<UUID, Point> mobileStationsMap() {return Collections.synchronizedMap(new HashMap<>());}

    @Bean
    public Map<UUID, DistanceAndPosition> detectedMobileStationsMap() {return Collections.synchronizedMap(new HashMap<>());}
}