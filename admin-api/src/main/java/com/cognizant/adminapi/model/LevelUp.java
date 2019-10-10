package com.cognizant.adminapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class LevelUp {
    private Integer levelUpId;
    private Integer customerId;
    private Integer points;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate memberDate;

    public LevelUp(){}

    public LevelUp(Integer customerId, Integer points, LocalDate memberDate) {
        this.customerId = customerId;
        this.points = points;
        this.memberDate = memberDate;
    }

    public LevelUp(Integer levelUpId, Integer customerId, Integer points, LocalDate memberDate) {
        this.levelUpId = levelUpId;
        this.customerId = customerId;
        this.points = points;
        this.memberDate = memberDate;
    }

    public Integer getLevelUpId() {
        return levelUpId;
    }

    public void setLevelUpId(Integer levelUpId) {
        this.levelUpId = levelUpId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public LocalDate getMemberDate() {
        return memberDate;
    }

    public void setMemberDate(LocalDate memberDate) {
        this.memberDate = memberDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LevelUp)) return false;
        LevelUp levelUp = (LevelUp) o;
        return Objects.equals(getLevelUpId(), levelUp.getLevelUpId()) &&
                Objects.equals(getCustomerId(), levelUp.getCustomerId()) &&
                Objects.equals(getPoints(), levelUp.getPoints()) &&
                Objects.equals(getMemberDate(), levelUp.getMemberDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLevelUpId(), getCustomerId(), getPoints(), getMemberDate());
    }
}
