package com.bookstore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "membership_types")
@IdClass(MembershipTypesId.class)
public class MembershipTypes {

    @Id
    @Column(name = "membership_type_id")
    @SequenceGenerator(name = "membership_type_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "membership_type_seq")
    private Integer memberShipTypeId;

    @Id
    @Column(name = "membership_premium_level", nullable = false)
    private String membershipPremiumLevel;

    @Id
    @Column(name = "membership_duration_in_days", nullable = false)
    private Integer membershipDurationInDays;

    @Column(name = "membership_price", nullable = false)
    private Double membershipPrice;

    @Column(name = "discount_percentage")
    private Integer discountPercentage;

    public Integer getMembershipDurationInDays() {
        return membershipDurationInDays;
    }

    public void setMembershipDurationInDays(Integer membershipDurationInDays) {
        this.membershipDurationInDays = membershipDurationInDays;
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

    public Integer getMemberShipTypeId() {
        return memberShipTypeId;
    }

    public void setMemberShipTypeId(Integer memberShipTypeId) {
        this.memberShipTypeId = memberShipTypeId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MembershipTypes{");
        sb.append("memberShipTypeId=").append(memberShipTypeId);
        sb.append(", membershipPremiumLevel='").append(membershipPremiumLevel).append('\'');
        sb.append(", membershipDuration=").append(membershipDurationInDays);
        sb.append(", membershipPrice=").append(membershipPrice);
        sb.append(", discountPercentage=").append(discountPercentage);
        sb.append('}');
        return sb.toString();
    }
}
