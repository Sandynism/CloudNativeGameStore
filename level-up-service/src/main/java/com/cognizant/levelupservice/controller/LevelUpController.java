package com.cognizant.levelupservice.controller;

import com.cognizant.levelupservice.exception.NotFoundException;
import com.cognizant.levelupservice.service.ServiceLayer;
import com.cognizant.levelupservice.viewModel.LevelUpViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/levelup")
public class LevelUpController {

    @Autowired
    ServiceLayer serviceLayer;

    @PostMapping//Another way to set the Rest API Post mapping
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUpViewModel createLevelUp(@RequestBody @Valid LevelUpViewModel levelUpViewModel) {

        return serviceLayer.saveLevelUp(levelUpViewModel);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LevelUpViewModel> getLevelUps() {

        List<LevelUpViewModel> levelUps = serviceLayer.findAllLevelUps();

        if (levelUps != null && levelUps.size() == 0)
            throw new NotFoundException("we don't have any level ups");
        return levelUps;
    }

    @GetMapping("/{levelUpId}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUpViewModel getLevelUp(@PathVariable("levelUpId") int levelUpId) {

        LevelUpViewModel levelUpViewModel = serviceLayer.findLevelUp(levelUpId);

        if (levelUpViewModel == null)

            throw new NotFoundException("Sorry! we don't have this Level up id no. " + levelUpId);
        return levelUpViewModel;
    }


    @DeleteMapping("/{levelUpId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLevelUp(@PathVariable("levelUpId") int levelUpId) {

        serviceLayer.removeLevelUp(levelUpId);
    }


    @PutMapping("/{levelUpId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLevelUp(@PathVariable("levelUpId") int levelUpId, @RequestBody @Valid LevelUpViewModel levelUpViewModel) {

        if (levelUpViewModel.getLevelUpId() == 0)
            levelUpViewModel.setLevelUpId(levelUpId);

        if (levelUpId != levelUpViewModel.getLevelUpId()) {

            throw new IllegalArgumentException("Sorry! we don't have level up ID no. " + levelUpId);
        }

        serviceLayer.updateLevelUp(levelUpViewModel);
    }


    @GetMapping("/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUpViewModel getLevelUpByCustomerId(@PathVariable("customerId") int customerId) {

        LevelUpViewModel levelUpViewModel = serviceLayer.findLevelUpByCustomerId(customerId);

        if (levelUpViewModel == null)

            throw new NotFoundException("Sorry! we don't have this customer id no. " + customerId);

        return levelUpViewModel;
    }


}
