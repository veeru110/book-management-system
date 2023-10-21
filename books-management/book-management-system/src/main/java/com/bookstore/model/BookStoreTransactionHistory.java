package com.bookstore.model;

import com.bookstore.constants.BookStoreTransactionType;
import jakarta.persistence.*;

@Entity
@Table(name = "book_store_transaction_history")
public class BookStoreTransactionHistory {

    @Id
    @Column(name = "book_store_transaction_id")
    @SequenceGenerator(name = "book_store_txn_seq", initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_store_txn_seq")
    private Integer bookStoreTransactionHistoryId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_bought", referencedColumnName = "book_name", nullable = false)
    private Book bookBought;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_membership", referencedColumnName = "buyer_membership_id")
    private BuyerMembershipHistory buyerMembershipHistory;

    @Column(name = "transaction_price")
    private Double transactionAmount = 0d;

    @Column(name = "actual_price")
    private Double actualPrice = 0d;

    @Column(name = "discount_amount")
    private Double discountAmount = 0d;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private BookStoreTransactionType transactionType;

    public BookStoreTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(BookStoreTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getBookStoreTransactionHistoryId() {
        return bookStoreTransactionHistoryId;
    }

    public void setBookStoreTransactionHistoryId(Integer bookStoreTransactionHistoryId) {
        this.bookStoreTransactionHistoryId = bookStoreTransactionHistoryId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBookBought() {
        return bookBought;
    }

    public void setBookBought(Book bookBought) {
        this.bookBought = bookBought;
    }

    public BuyerMembershipHistory getBuyerMembershipHistory() {
        return buyerMembershipHistory;
    }

    public void setBuyerMembershipHistory(BuyerMembershipHistory buyerMembershipHistory) {
        this.buyerMembershipHistory = buyerMembershipHistory;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookStoreTransactionHistory{");
        sb.append("bookStoreTransactionHistoryId=").append(bookStoreTransactionHistoryId);
        sb.append(", user=").append(user);
        sb.append(", bookBought=").append(bookBought);
        sb.append(", buyerMembershipHistory=").append(buyerMembershipHistory);
        sb.append(", transactionAmount=").append(transactionAmount);
        sb.append(", actualPrice=").append(actualPrice);
        sb.append(", discountAmount=").append(discountAmount);
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }
}
