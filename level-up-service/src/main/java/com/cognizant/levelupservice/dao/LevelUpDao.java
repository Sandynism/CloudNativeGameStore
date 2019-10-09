package com.cognizant.levelupservice.dao;

import com.cognizant.levelupservice.model.LevelUp;

import java.util.List;

public interface LevelUpDao {

    LevelUp getLevelUp(int id);

    LevelUp addLevelUp (LevelUp levelUp);

    List<LevelUp> getAllLevelUps();

    void updateLevelUp(LevelUp levelUp, int id);

    void deleteLevelUp(int id);

    LevelUp getLevelUpByCustomerId(int customerId);
}
