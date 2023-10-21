package com.bookstore.vo;


public class MembershipTypeVo {

    private String membershipPremiumLevel;

    private Integer membershipDurationInDays;

     private Double membershipPrice;

    private Integer discountPercentage;

    private Boolean enablePromotionalNotifications = false;


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

    public Double getMembershipPrice() {
        return membershipPrice;
    }

    public void setMembershipPrice(Double membershipPrice) {
        this.membershipPrice = membershipPrice;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Boolean getEnablePromotionalNotifications() {
        return enablePromotionalNotifications;
    }

    public void setEnablePromotionalNotifications(Boolean enablePromotionalNotifications) {
        this.enablePromotionalNotifications = enablePromotionalNotifications;
    }
}
