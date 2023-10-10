package com.bookstore.dao;

import com.bookstore.model.BuyerMembershipInfo;
import com.bookstore.repository.BuyerMembershipInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuyerMembershipManagerImpl implements IBuyerMembershipManager {

    private BuyerMembershipInfoRepository buyerMembershipInfoRepository;

    @Autowired
    public BuyerMembershipManagerImpl(BuyerMembershipInfoRepository buyerMembershipInfoRepository) {
        this.buyerMembershipInfoRepository = buyerMembershipInfoRepository;
    }

    @Override
    public List<BuyerMembershipInfo> getAllBuyerMembershipInfos(String username) {
        if (StringUtils.isEmpty(username)) {
            return buyerMembershipInfoRepository.findAll();
        }
        return buyerMembershipInfoRepository.findAllBuyerMemberships(username);
    }

    @Override
    public void save(BuyerMembershipInfo buyerMembershipInfo) {
        buyerMembershipInfoRepository.save(buyerMembershipInfo);
    }
}
