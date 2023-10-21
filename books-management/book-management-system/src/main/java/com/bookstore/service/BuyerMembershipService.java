package com.bookstore.service;

import com.bookstore.command.BuyerMembershipCommand;
import com.bookstore.vo.BuyerMembershipVo;
import com.bookstore.vo.MembershipTypeVo;

import java.util.List;

public interface BuyerMembershipService {

    List<BuyerMembershipVo> getAllBuyersMemberships();

    BuyerMembershipVo buyMembership(BuyerMembershipCommand buyerMembershipCommand);

    MembershipTypeVo addMembershipLevel(BuyerMembershipCommand buyerMembershipCommand) throws Exception;
}
