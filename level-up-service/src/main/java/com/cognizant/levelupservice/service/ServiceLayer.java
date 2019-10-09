package com.cognizant.levelupservice.service;

import com.cognizant.levelupservice.dao.LevelUpDao;
import com.cognizant.levelupservice.model.LevelUp;
import com.cognizant.levelupservice.viewModel.LevelUpViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class ServiceLayer {

    LevelUpDao levelUpDao;

    @Autowired
    public ServiceLayer(LevelUpDao levelUpDao) {
        this.levelUpDao = levelUpDao;
    }

    public LevelUpViewModel saveLevelUp (LevelUpViewModel levelUpViewModel){

        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(levelUpViewModel.getCustomerId());
        levelUp.setPoints(levelUpViewModel.getPoints());
        levelUp.setMemberDate(levelUpViewModel.getMemberDate());

        levelUp = levelUpDao.addLevelUp(levelUp);

        levelUpViewModel.setLevelUpId(levelUp.getLevelUpId());

        return levelUpViewModel;

    }


    public void updateLevelUp(LevelUpViewModel levelUpViewModel){

        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(levelUpViewModel.getLevelUpId());
        levelUp.setCustomerId(levelUpViewModel.getCustomerId());
        levelUp.setPoints(levelUpViewModel.getPoints());
        levelUp.setMemberDate(levelUpViewModel.getMemberDate());

        levelUpDao.updateLevelUp(levelUp,levelUp.getLevelUpId());

    }

    public void removeLevelUp (int id){

        LevelUp levelUp = levelUpDao.getLevelUp(id);

        if(levelUp == null) throw new NoSuchElementException(String.format("no Level up with id %s found", id));

        levelUpDao.deleteLevelUp(id);

    }

    public LevelUpViewModel findLevelUp (int id) {

        LevelUp levelUp = levelUpDao.getLevelUp(id);

        if(levelUp == null) throw new NoSuchElementException(String.format("no Level up with id %s found", id));

        else
            return buildLevelUpViewModel(levelUp);

    }


    public List<LevelUpViewModel> findAllLevelUps(){

        List<LevelUp> levelUps = levelUpDao.getAllLevelUps();

        List<LevelUpViewModel> levelUpViewModels = new ArrayList<>();

        for(LevelUp l: levelUps){

            levelUpViewModels.add(buildLevelUpViewModel(l));

        }

        return levelUpViewModels;

    }

    public LevelUpViewModel findLevelUpByCustomerId (int customerId) {

        LevelUp levelUp = levelUpDao.getLevelUpByCustomerId(customerId);

        if(levelUp == null) throw new NoSuchElementException(String.format("no Level up with customer id %s", customerId));

        else
            return buildLevelUpViewModel(levelUp);

    }






    @Transactional
    private LevelUpViewModel buildLevelUpViewModel(LevelUp levelUp){

        LevelUpViewModel levelUpViewModel = new LevelUpViewModel();
        levelUpViewModel.setLevelUpId(levelUp.getLevelUpId());
        levelUpViewModel.setCustomerId(levelUp.getCustomerId());
        levelUpViewModel.setPoints(levelUp.getPoints());
        levelUpViewModel.setMemberDate(levelUp.getMemberDate());

        return levelUpViewModel;

    }





}
