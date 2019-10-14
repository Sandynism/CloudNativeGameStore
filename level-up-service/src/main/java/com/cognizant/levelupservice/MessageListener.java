package com.cognizant.levelupservice;

import com.cognizant.levelupservice.dao.LevelUpDao;

import com.cognizant.levelupservice.model.LevelUp;
import com.cognizant.levelupservice.utill.message.LevelUpEntry;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @Autowired
    LevelUpDao levelUpDao;

    @RabbitListener(queues = LevelUpServiceApplication.QUEUE_NAME)
    public void receiveMessage(LevelUpEntry msg) {

        System.out.println("Incoming message: " + msg.toString());
        try {
            if (msg.getLevelUpId() == 0) {
                LevelUp levelUp = new LevelUp();
                levelUp.setCustomerId(msg.getCustomerId());
                levelUp.setMemberDate(msg.getMemberDate());
                levelUp.setPoints(msg.getPoints());
                levelUpDao.addLevelUp(levelUp);
                System.out.println("LevelUp added: " + levelUp.toString());
            } else {
                LevelUp levelUp = new LevelUp();
                levelUp.setLevelUpId(msg.getLevelUpId());
                levelUp.setPoints(msg.getPoints());

                levelUpDao.updateLevelUp(levelUp, levelUp.getLevelUpId());

                System.out.println("LevelUp updated: " + levelUp.toString());
            }
        } catch (Exception e) {
            System.out.println("There is an exception: " + e.getMessage());
        }
    }




}







