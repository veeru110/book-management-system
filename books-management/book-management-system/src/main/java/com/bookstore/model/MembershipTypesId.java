package com.bookstore.model;

import java.util.Objects;

public class MembershipTypesId {
    private String membershipPremiumLevel;

    private String membershipDuration;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MembershipTypesId that = (MembershipTypesId) o;
        return membershipPremiumLevel.equals(that.membershipPremiumLevel) && membershipDuration.equals(that.membershipDuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(membershipPremiumLevel, membershipDuration);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MembershipTypesId{");
        sb.append("membershipPremiumLevel='").append(membershipPremiumLevel).append('\'');
        sb.append(", membershipDuration='").append(membershipDuration).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
