package com.cognizant.levelupservice;

import com.cognizant.levelupservice.dao.LevelUpDao;

import com.cognizant.levelupservice.service.ServiceLayer;
import com.cognizant.levelupservice.utill.message.LevelUp;
import com.cognizant.levelupservice.viewModel.LevelUpViewModel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @Autowired
    ServiceLayer serviceLayer;

    @RabbitListener(queues = LevelUpServiceApplication.QUEUE_NAME)
    public void receiveMessage(LevelUp msg) {

        System.out.println("Incoming message: " + msg.toString());
        try {
            if (msg.getLevelUpId() == null) {
                LevelUpViewModel levelUpViewModel = new LevelUpViewModel();
                levelUpViewModel.setCustomerId(msg.getCustomerId());
                levelUpViewModel.setMemberDate(msg.getMemberDate());
                levelUpViewModel.setPoints(msg.getPoints());
                levelUpViewModel = serviceLayer.saveLevelUp(levelUpViewModel);
                System.out.println("LevelUp added: " + levelUpViewModel.toString());
            } else {
                LevelUpViewModel levelUpViewModel = new LevelUpViewModel();
                levelUpViewModel.setLevelUpId(msg.getLevelUpId());
                levelUpViewModel.setCustomerId(msg.getCustomerId());
                levelUpViewModel.setMemberDate(msg.getMemberDate());
                levelUpViewModel.setPoints(msg.getPoints());
                serviceLayer.updateLevelUp(levelUpViewModel);

                System.out.println("LevelUp updated: " + levelUpViewModel.toString());
            }
        } catch (Exception e) {
            System.out.println("There is an exception: " + e.getMessage());
        }
    }




}







