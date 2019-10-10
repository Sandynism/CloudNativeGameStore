package com.cognizant.adminapi.controller;

import com.cognizant.adminapi.exception.NoSuchLevelUpException;
import com.cognizant.adminapi.exception.NotFoundException;
import com.cognizant.adminapi.model.LevelUpViewModel;
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
    public LevelUpViewModel createLevelUp(@RequestBody LevelUpViewModel lvm) {
        return sl.createLevelUp(lvm);
    }

    @GetMapping(value = "/{levelUpId}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUpViewModel getLevelUp(@PathVariable(name = "levelUpId") Integer levelUpId) throws NoSuchLevelUpException {
        LevelUpViewModel lvm = sl.getLevelUp(levelUpId);

        if (lvm == null)
            throw new NoSuchLevelUpException(levelUpId);

        return lvm;
    }

    @PutMapping(value = "/{levelUpId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateLevelUp(@RequestBody LevelUpViewModel lvm, @PathVariable(name = "levelUpId") Integer levelUpId) throws NoSuchLevelUpException {
        LevelUpViewModel levelUp = sl.getLevelUp(lvm.getLevelUpId());

        if (levelUp == null)
            throw new NoSuchLevelUpException(levelUpId);

        sl.updateLevelUp(lvm);
    }

    @DeleteMapping(value = "/{levelUpId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLevelUp(@PathVariable(name = "levelUpId") Integer levelUpId) {
        LevelUpViewModel levelUp = sl.getLevelUp(levelUpId);

        if (levelUp == null)
            throw new NoSuchLevelUpException(levelUpId);

        sl.deleteLevelUp(levelUpId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LevelUpViewModel> getAllLevelUps() {
        List<LevelUpViewModel> lvmList = sl.getAllLevelUps();

        if (lvmList != null && lvmList.size() == 0) {
            throw new NotFoundException("There are no level up entries available.");
        }

        return lvmList;
    }

    @GetMapping(value = "/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUpViewModel getLevelUpByCustomerId(@PathVariable(name = "customerId") Integer customerId) {
        LevelUpViewModel lvm = sl.getLevelUpByCustomerId(customerId);

        if (lvm == null)
            throw new NotFoundException("There is no level up entry with customer id " + customerId);

        return lvm;
    }
}
