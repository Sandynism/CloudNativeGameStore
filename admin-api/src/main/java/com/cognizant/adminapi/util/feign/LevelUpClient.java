package com.cognizant.adminapi.util.feign;

import com.cognizant.adminapi.model.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "level-up-service")
public interface LevelUpClient {

    @PostMapping(value = "/levelup")
    LevelUp createLevelUp(@RequestBody LevelUp levelUp);

    @GetMapping(value = "/levelup/{levelUpId}")
    LevelUp getLevelUp(@PathVariable Integer levelUpId);

    @PutMapping(value = "/levelup/{levelUpId}")
    void updateLevelUp(@RequestBody LevelUp levelUp, @PathVariable Integer levelUpId);

    @DeleteMapping(value = "/levelup/{levelUpId}")
    void deleteLevelUp(@PathVariable Integer levelUpId);

    @GetMapping(value = "/levelup")
    List<LevelUp> getAllLevelUps();

    @GetMapping("/levelup/customer/{customerId}")
    LevelUp getLevelUpByCustomerId(@PathVariable(name = "customerId") Integer customerId);
}
