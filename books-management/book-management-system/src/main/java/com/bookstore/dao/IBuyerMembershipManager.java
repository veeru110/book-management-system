package com.bookstore.dao;

import com.bookstore.model.BuyerMembershipHistory;

import java.util.List;

public interface IBuyerMembershipManager {
    List<BuyerMembershipHistory> getAllBuyerMembershipInfo(String username);

    void save(BuyerMembershipHistory buyerMembershipHistory);

}
