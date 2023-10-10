package com.bookstore.repository;

import com.bookstore.model.MembershipTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipTypeRepository extends JpaRepository<MembershipTypes, Integer> {
    List<MembershipTypes> findAll();

    MembershipTypes findByMembershipPremiumLevelAndMembershipDurationInDays(String premiumLevel, Integer membershipDuration);
}
