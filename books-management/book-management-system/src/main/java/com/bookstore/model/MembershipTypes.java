package com.bookstore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "membership_types")
@IdClass(MembershipTypesId.class)
public class MembershipTypes {

    @Id
    @Column(name = "membership_premium_level", nullable = false)
    private String membershipPremiumLevel;

    @Id
    @Column(name = "membership_duration", nullable = false)
    private Integer membershipDuration;

    @Column(name = "membership_price", nullable = false)
    private Double membershipPrice;

    @Column(name = "discount_percentage")
    private Integer discountPercentage;

    public Integer getMembershipDuration() {
        return membershipDuration;
    }

    public void setMembershipDuration(Integer membershipDuration) {
        this.membershipDuration = membershipDuration;
    }

    public String getMembershipPremiumLevel() {
        return membershipPremiumLevel;
    }

    public void setMembershipPremiumLevel(String membershipPremiumLevel) {
        this.membershipPremiumLevel = membershipPremiumLevel;
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
}
