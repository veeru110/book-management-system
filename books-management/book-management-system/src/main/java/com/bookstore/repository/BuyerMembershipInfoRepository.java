package com.bookstore.repository;

import com.bookstore.model.BuyerMembershipInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyerMembershipInfoRepository extends JpaRepository<BuyerMembershipInfo, Integer> {

    @Query("SELECT * FROM BuyerMembershipInfo b where b.user.email=?1 and is_deleted=0")
    List<BuyerMembershipInfo> findAllBuyerMemberships(String username);
}
