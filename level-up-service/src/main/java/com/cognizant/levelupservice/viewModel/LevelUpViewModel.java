package com.cognizant.levelupservice.viewModel;

import java.time.LocalDate;
import java.util.Objects;

public class LevelUpViewModel {

    private Integer levelUpId;
    private Integer customerId;
    private Integer points;
    private LocalDate memberDate;

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
        if (!(o instanceof LevelUpViewModel)) return false;
        LevelUpViewModel that = (LevelUpViewModel) o;
        return Objects.equals(levelUpId, that.levelUpId) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(points, that.points) &&
                Objects.equals(memberDate, that.memberDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(levelUpId, customerId, points, memberDate);
    }
}
