package com.bookstore.dao;

import com.bookstore.model.BuyerMembershipInfo;

import java.util.List;

public interface IBuyerMembershipManager {
    List<BuyerMembershipInfo> getAllBuyerMembershipInfos(String username);

    void save(BuyerMembershipInfo buyerMembershipInfo);

}
