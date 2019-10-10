package com.cognizant.adminapi.exception;

public class NoSuchLevelUpException extends RuntimeException {
    public NoSuchLevelUpException(Integer levelUpId) {
        super("There is no such level up entry with levelup id " + levelUpId + ".");
    }
}
