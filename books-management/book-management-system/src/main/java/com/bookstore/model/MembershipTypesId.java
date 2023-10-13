package com.bookstore.model;

import java.util.Objects;

public class MembershipTypesId {

    private Integer memberShipTypeId;
    private String membershipPremiumLevel;

    private Integer membershipDurationInDays;

    public Integer getMemberShipTypeId() {
        return memberShipTypeId;
    }

    public void setMemberShipTypeId(Integer memberShipTypeId) {
        this.memberShipTypeId = memberShipTypeId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MembershipTypesId that = (MembershipTypesId) o;
        return memberShipTypeId.equals(that.memberShipTypeId) && membershipPremiumLevel.equals(that.membershipPremiumLevel) && membershipDurationInDays.equals(that.membershipDurationInDays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberShipTypeId, membershipPremiumLevel, membershipDurationInDays);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MembershipTypesId{");
        sb.append("memberShipTypeId=").append(memberShipTypeId);
        sb.append(", membershipPremiumLevel='").append(membershipPremiumLevel).append('\'');
        sb.append(", membershipDurationInDays=").append(membershipDurationInDays);
        sb.append('}');
        return sb.toString();
    }
}
