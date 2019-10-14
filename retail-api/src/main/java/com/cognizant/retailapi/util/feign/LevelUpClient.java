package com.cognizant.retailapi.util.feign;

import com.cognizant.retailapi.model.LevelUpViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "level-up-service")
public interface LevelUpClient {

    @PostMapping(value = "/levelup")
    LevelUpViewModel createLevelUp(@RequestBody LevelUpViewModel levelUpViewModel);

    @GetMapping(value = "/levelup/{levelUpId}")
    LevelUpViewModel getLevelUp(@PathVariable Integer levelUpId);

    @PutMapping(value = "/levelup/{levelUpId}")
    void updateLevelUp(@RequestBody LevelUpViewModel levelUpViewModel, @PathVariable Integer levelUpId);

    @DeleteMapping(value = "/levelup/{levelUpId}")
    void deleteLevelUp(@PathVariable Integer levelUpId);

    @GetMapping(value = "/levelup")
    List<LevelUpViewModel> getAllLevelUps();

    @GetMapping("/levelup/customer/{customerId}")
    LevelUpViewModel getLevelUpByCustomerId(@PathVariable(name = "customerId") Integer customerId);
}
