package com.cognizant.adminapi.util.feign;

import com.cognizant.adminapi.model.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "level-up-service")
@RequestMapping(value = "/levelup")
public interface LevelUpClient {

    @PostMapping
    LevelUp addLevelUp(@RequestBody LevelUp levelUp);

    @GetMapping(value = "/{levelUpId}")
    LevelUp getLevelUp(@PathVariable Integer levelUpId);

    @PutMapping(value = "/{levelUpId}")
    void updateLevelUp(@RequestBody LevelUp levelUp, @PathVariable Integer levelUpId);

    @DeleteMapping(value = "/{levelUpId}")
    void deleteLevelUp(@PathVariable Integer levelUpId);

    @GetMapping
    List<LevelUp> getAllLevelUps();

    @GetMapping("/customer/{customerId}")
    LevelUp getLevelUpByCustomerId(@PathVariable(name = "customerId") Integer customerId);
}
