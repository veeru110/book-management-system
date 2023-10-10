package com.bookstore.service;

import com.bookstore.command.BuyerMembershipCommand;
import com.bookstore.vo.BuyerMembershipVo;

import java.util.List;

public interface BuyerMembershipService {

    List<BuyerMembershipVo> getAllBuyersMemberships();

    BuyerMembershipVo buyMembership(BuyerMembershipCommand buyerMembershipCommand);
}
