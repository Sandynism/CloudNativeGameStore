package com.cognizant.levelupservice.service;

import com.cognizant.levelupservice.dao.LevelUpDao;
import com.cognizant.levelupservice.dao.LevelUpDaoJdbcTemplateImpl;
import com.cognizant.levelupservice.model.LevelUp;
import com.cognizant.levelupservice.viewModel.LevelUpViewModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    LevelUpDao levelUpDao;
    ServiceLayer serviceLayer;


    @Before
    public void setUp() throws Exception {

        setUpLevelUpDaoMock();

        serviceLayer = new ServiceLayer(levelUpDao);
    }




    private void setUpLevelUpDaoMock(){

        levelUpDao = mock(LevelUpDaoJdbcTemplateImpl.class);

        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(3);
        levelUp.setCustomerId(2);
        levelUp.setMemberDate(LocalDate.of(2012, 12, 12));
        levelUp.setPoints(100);

        LevelUp levelUp1 = new LevelUp();
        levelUp1.setCustomerId(2);
        levelUp1.setMemberDate(LocalDate.of(2012, 12, 12));
        levelUp1.setPoints(100);

        LevelUp levelUp2 = new LevelUp();
        levelUp2.setLevelUpId(10);
        levelUp2.setCustomerId(6);
        levelUp2.setMemberDate(LocalDate.of(2011, 11, 11));
        levelUp2.setPoints(111);

        // LevelUp List
        List<LevelUp> levelUpList = new ArrayList<>();
        levelUpList.add(levelUp);

        // LevelUp add
        doReturn(levelUp).when(levelUpDao).addLevelUp(levelUp1);
        doReturn(levelUp).when(levelUpDao).getLevelUp(3);

        // LevelUp update
        doNothing().when(levelUpDao).updateLevelUp(levelUp,10);
        doReturn(levelUp2).when(levelUpDao).getLevelUp(10);

        // LevelUp delete
        doNothing().when(levelUpDao).deleteLevelUp(15);
        doReturn(null).when(levelUpDao).getLevelUp(15);

        // LevelUp get all
        doReturn(levelUpList).when(levelUpDao).getAllLevelUps();

        // LevelUp by customer id
        doReturn(levelUp).when(levelUpDao).getLevelUpByCustomerId(2);

    }

    @Test
    public void saveFindLevelUp() {

        LevelUpViewModel levelUpViewModel = new LevelUpViewModel();
        levelUpViewModel.setCustomerId(2);
        levelUpViewModel.setMemberDate(LocalDate.of(2012, 12, 12));
        levelUpViewModel.setPoints(100);

        levelUpViewModel = serviceLayer.saveLevelUp(levelUpViewModel);

        LevelUpViewModel fromService = serviceLayer.findLevelUp(levelUpViewModel.getLevelUpId());

        assertEquals(levelUpViewModel, fromService);

    }

    @Test
    public void updateLevelUp() {

        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(10);
        levelUp.setCustomerId(6);
        levelUp.setMemberDate(LocalDate.of(2011, 11, 11));
        levelUp.setPoints(111);

        LevelUpViewModel levelUpViewModel = new LevelUpViewModel();
        levelUpViewModel.setLevelUpId(levelUp.getLevelUpId());
        levelUpViewModel.setCustomerId(levelUp.getCustomerId());
        levelUpViewModel.setPoints(levelUp.getPoints());
        levelUpViewModel.setMemberDate(levelUp.getMemberDate());

        serviceLayer.updateLevelUp(levelUpViewModel);

        LevelUpViewModel fromService = serviceLayer.findLevelUp(levelUpViewModel.getLevelUpId());

        assertEquals(levelUpViewModel, fromService);
    }

    @Test(expected = NoSuchElementException.class)
    public void removeLevelUp() {

        serviceLayer.removeLevelUp(15);

        LevelUpViewModel levelUpViewModel = serviceLayer.findLevelUp(15);

    }


    @Test
    public void findAllLevelUps() {

        LevelUpViewModel levelUpViewModel = new LevelUpViewModel();
        levelUpViewModel.setCustomerId(2);
        levelUpViewModel.setMemberDate(LocalDate.of(2012, 12, 12));
        levelUpViewModel.setPoints(100);

        levelUpViewModel = serviceLayer.saveLevelUp(levelUpViewModel);

        List<LevelUpViewModel> levelUpViewModels = serviceLayer.findAllLevelUps();

        assertEquals(1, levelUpViewModels.size());

    }


    @Test
    public void findLevelUpByCustomer(){

        LevelUpViewModel levelUpViewModel = new LevelUpViewModel();
        levelUpViewModel.setCustomerId(2);
        levelUpViewModel.setMemberDate(LocalDate.of(2012, 12, 12));
        levelUpViewModel.setPoints(100);

        levelUpViewModel = serviceLayer.saveLevelUp(levelUpViewModel);

        LevelUpViewModel fromService = serviceLayer.findLevelUpByCustomerId(2);

        assertNotNull(fromService);

    }
}