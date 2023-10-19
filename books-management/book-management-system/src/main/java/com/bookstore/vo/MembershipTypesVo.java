package com.bookstore.vo;


public class MembershipTypesVo {
    private String membershipPremiumLevel;

    private String membershipDuration;

    private Double membershipPrice;

    private Integer discountPercentage;


    public String getMembershipPremiumLevel() {
        return membershipPremiumLevel;
    }

    public void setMembershipPremiumLevel(String membershipPremiumLevel) {
        this.membershipPremiumLevel = membershipPremiumLevel;
    }

    public String getMembershipDuration() {
        return membershipDuration;
    }

    public void setMembershipDuration(String membershipDuration) {
        this.membershipDuration = membershipDuration;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MembershipTypesVo{");
        sb.append("membershipPremiumLevel='").append(membershipPremiumLevel).append('\'');
        sb.append(", membershipDuration='").append(membershipDuration).append('\'');
        sb.append(", membershipPrice=").append(membershipPrice);
        sb.append(", discountPercentage=").append(discountPercentage);
        sb.append('}');
        return sb.toString();
    }
}
