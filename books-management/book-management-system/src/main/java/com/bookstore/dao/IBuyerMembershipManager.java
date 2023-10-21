package com.bookstore.dao;

import com.bookstore.model.BuyerMembershipHistory;

import java.util.List;
import java.util.Optional;

public interface IBuyerMembershipManager {
    List<BuyerMembershipHistory> getAllBuyerMembershipInfo(String username);

    void save(BuyerMembershipHistory buyerMembershipHistory);

    Optional<BuyerMembershipHistory> getActiveMembershipForTheUser(String username);

}
