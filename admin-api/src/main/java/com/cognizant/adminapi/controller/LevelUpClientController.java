package com.cognizant.adminapi.controller;

import com.cognizant.adminapi.exception.NoSuchLevelUpException;
import com.cognizant.adminapi.exception.NotFoundException;
import com.cognizant.adminapi.model.LevelUp;
import com.cognizant.adminapi.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/levelup")
public class LevelUpClientController {

    @Autowired
    ServiceLayer sl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUp createLevelUp(@RequestBody LevelUp levelUp) {
        return sl.createLevelUp(levelUp);
    }

    @GetMapping(value = "/{levelUpId}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUp getLevelUp(@PathVariable(name = "levelUpId") Integer levelUpId) throws NoSuchLevelUpException {
        LevelUp levelUp = sl.getLevelUp(levelUpId);

        if (levelUp == null)
            throw new NoSuchLevelUpException(levelUpId);

        return levelUp;
    }

    @PutMapping(value = "/{levelUpId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateLevelUp(@RequestBody LevelUp levelUp, @PathVariable(name = "levelUpId") Integer levelUpId) throws NoSuchLevelUpException {
        LevelUp lu = sl.getLevelUp(levelUp.getCustomerId());

        if (lu == null)
            throw new NoSuchLevelUpException(levelUpId);

        sl.updateLevelUp(levelUp);
    }

    @DeleteMapping(value = "/{levelUpId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLevelUp(@PathVariable(name = "levelUpId") Integer levelUpId) {
        sl.deleteLevelUp(levelUpId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LevelUp> getAllLevelUps() {
        List<LevelUp> levelUpList = sl.getAllLevelUps();

        if (levelUpList != null && levelUpList.size() == 0) {
            throw new NotFoundException("There are no level up entries available.");
        }

        return levelUpList;
    }

    @GetMapping(value = "/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUp getLevelUpByCustomerId(@PathVariable(name = "customerId") Integer customerId) {
        LevelUp lu = sl.getLevelUpByCustomerId(customerId);

        if (lu == null)
            throw new NotFoundException("There is no level up entry with customer id " + customerId);

        return lu;
    }
}
