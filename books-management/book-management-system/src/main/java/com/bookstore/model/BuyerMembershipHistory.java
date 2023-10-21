package com.bookstore.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "buyer_membership_history")
public class BuyerMembershipHistory {

    @Id
    @Column(name = "buyer_membership_id")
    @SequenceGenerator(name = "buyer_membership_seq", initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "buyer_membership_seq")
    private Integer buyerMembershipId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_membership_type_id", referencedColumnName = "membership_type_id", nullable = false)
    private MembershipTypes membershipType;

    @Column(name = "membership_price")
    private Double membershipPrice;

    @Column(name = "membership_start_date")
    private Date membershipStartDate = new Date();

    @Column(name = "membership_end_data")
    private Date membershipEndDate;

    @OneToMany(mappedBy = "buyerMembershipHistory")
    List<BookStoreTransactionHistory> bookStoreTransactionHistories;

    public List<BookStoreTransactionHistory> getBookStoreTransactionHistories() {
        return bookStoreTransactionHistories;
    }

    public void setBookStoreTransactionHistories(List<BookStoreTransactionHistory> bookStoreTransactionHistories) {
        this.bookStoreTransactionHistories = bookStoreTransactionHistories;
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

    public Integer getBuyerMembershipId() {
        return buyerMembershipId;
    }

    public void setBuyerMembershipId(Integer buyerMembershipId) {
        this.buyerMembershipId = buyerMembershipId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BuyerMembershipInfo{");
        sb.append("buyerMembershipId=").append(buyerMembershipId);
        sb.append(", user=").append(user);
        sb.append(", membershipType=").append(membershipType);
        sb.append(", membershipPrice=").append(membershipPrice);
        sb.append(", membershipStartDate=").append(membershipStartDate);
        sb.append(", membershipEndDate=").append(membershipEndDate);
        sb.append('}');
        return sb.toString();
    }
}
