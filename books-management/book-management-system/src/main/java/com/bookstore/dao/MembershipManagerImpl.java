package com.bookstore.dao;

import com.bookstore.model.MembershipTypes;
import com.bookstore.repository.MembershipTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MembershipManagerImpl implements IMembershipManager {

    private final MembershipTypeRepository membershipTypeRepository;

    @Autowired
    public MembershipManagerImpl(MembershipTypeRepository membershipTypeRepository) {
        this.membershipTypeRepository = membershipTypeRepository;
    }

    @Override
    public List<MembershipTypes> getAllMemberships() {
        return membershipTypeRepository.findAll();
    }

    @Override
    public MembershipTypes findByPremiumLevelAndMembershipDuration(String premiumLevel, Integer membershipDuration) {
        return membershipTypeRepository.findByMembershipPremiumLevelAndMembershipDurationInDays(premiumLevel, membershipDuration);
    }

    @Override
    public MembershipTypes save(MembershipTypes membershipTypes) {
        return membershipTypeRepository.save(membershipTypes);
    }
}
