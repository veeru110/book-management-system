package com.bookstore.dao;

import com.bookstore.model.MembershipTypes;

import java.util.List;

public interface IMembershipManager {
    List<MembershipTypes> getAllMemberships();

    MembershipTypes findByPremiumLevelAndMembershipDuration(String premiumLevel, Integer membershipDuration);
}
