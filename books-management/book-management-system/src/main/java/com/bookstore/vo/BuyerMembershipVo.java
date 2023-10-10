package com.bookstore.vo;

import com.bookstore.model.MembershipTypes;

import java.util.Date;

public class BuyerMembershipVo {

    private UserVo user;

    private MembershipTypes membershipType;

    private Double membershipPrice;

    private Date membershipStartDate = new Date();

    private Date membershipEndDate;


    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }

    public MembershipTypes getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipTypes membershipType) {
        this.membershipType = membershipType;
    }

    public Double getMembershipPrice() {
        return membershipPrice;
    }

    public void setMembershipPrice(Double membershipPrice) {
        this.membershipPrice = membershipPrice;
    }

    public Date getMembershipStartDate() {
        return membershipStartDate;
    }

    public void setMembershipStartDate(Date membershipStartDate) {
        this.membershipStartDate = membershipStartDate;
    }

    public Date getMembershipEndDate() {
        return membershipEndDate;
    }

    public void setMembershipEndDate(Date membershipEndDate) {
        this.membershipEndDate = membershipEndDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BuyerMembershipVo{");
        sb.append("user=").append(user);
        sb.append(", membershipType=").append(membershipType);
        sb.append(", membershipPrice=").append(membershipPrice);
        sb.append(", membershipStartDate=").append(membershipStartDate);
        sb.append(", membershipEndDate=").append(membershipEndDate);
        sb.append('}');
        return sb.toString();
    }
}
