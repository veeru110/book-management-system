package com.bookstore.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class BuyerMembershipCommand {
    @NotNull
    @NotEmpty
    private String membershipPremiumLevel;
    @NotNull
    private Integer membershipDurationInDays;

    private Double discountPercentage;

    private Boolean enablePromotionalNotifications;

    private Double membershipPrice;

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Boolean getEnablePromotionalNotifications() {
        return enablePromotionalNotifications;
    }

    public void setEnablePromotionalNotifications(Boolean enablePromotionalNotifications) {
        this.enablePromotionalNotifications = enablePromotionalNotifications;
    }

    public Double getMembershipPrice() {
        return membershipPrice;
    }

    public void setMembershipPrice(Double membershipPrice) {
        this.membershipPrice = membershipPrice;
    }

    public String getMembershipPremiumLevel() {
        return membershipPremiumLevel;
    }

    public void setMembershipPremiumLevel(String membershipPremiumLevel) {
        this.membershipPremiumLevel = membershipPremiumLevel;
    }

    public Integer getMembershipDurationInDays() {
        return membershipDurationInDays;
    }

    public void setMembershipDurationInDays(Integer membershipDurationInDays) {
        this.membershipDurationInDays = membershipDurationInDays;
    }
}
