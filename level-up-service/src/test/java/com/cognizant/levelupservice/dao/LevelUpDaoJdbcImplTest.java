package com.cognizant.levelupservice.dao;

import com.cognizant.levelupservice.model.LevelUp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LevelUpDaoJdbcImplTest {

    @Autowired
    LevelUpDao levelUpDao;

    @Before
    public void setUp() throws Exception {

        List<LevelUp> levelUpList = levelUpDao.getAllLevelUps();

        levelUpList.forEach(levelUp -> levelUpDao.deleteLevelUp(levelUp.getLevelUpId()));

    }


    @Test
    public void addGetDeleteLevelUp() {

        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(2);
        levelUp.setMemberDate(LocalDate.of(2012, 12, 12));
        levelUp.setPoints(100);

        levelUp = levelUpDao.addLevelUp(levelUp);

        LevelUp fromDao = levelUpDao.getLevelUp(levelUp.getLevelUpId());

        assertEquals(levelUp, fromDao);

        levelUpDao.deleteLevelUp(levelUp.getLevelUpId());

        fromDao = levelUpDao.getLevelUp(levelUp.getLevelUpId());

        assertNull(fromDao);


    }


    @Test
    public void getAllLevelUps() {

        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(2);
        levelUp.setMemberDate(LocalDate.of(2012, 12, 12));
        levelUp.setPoints(100);

        levelUp = levelUpDao.addLevelUp(levelUp);

        levelUp = new LevelUp();
        levelUp.setCustomerId(6);
        levelUp.setMemberDate(LocalDate.of(2011, 11, 11));
        levelUp.setPoints(111);

        levelUp = levelUpDao.addLevelUp(levelUp);

        List<LevelUp> levelUpList = levelUpDao.getAllLevelUps();

        assertEquals(2, levelUpList.size());

    }

    @Test
    public void updateLevelUp() {

        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(2);
        levelUp.setMemberDate(LocalDate.of(2012, 12, 12));
        levelUp.setPoints(100);

        levelUp = levelUpDao.addLevelUp(levelUp);

        levelUp.setMemberDate(LocalDate.of(2011, 11, 11));
        levelUp.setPoints(111);

        levelUpDao.updateLevelUp(levelUp,levelUp.getLevelUpId());

        LevelUp fromDao = levelUpDao.getLevelUp(levelUp.getLevelUpId());

        assertEquals(levelUp, fromDao);

    }


    @Test
    public void getLevelUpByCustomerId() {

        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(2);
        levelUp.setMemberDate(LocalDate.of(2012, 12, 12));
        levelUp.setPoints(100);

        levelUp = levelUpDao.addLevelUp(levelUp);

        LevelUp levelUpByCustomerId = levelUpDao.getLevelUpByCustomerId(2);

        assertEquals(levelUp, levelUpByCustomerId);

    }
}