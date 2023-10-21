package com.bookstore.repository;

import com.bookstore.model.BuyerMembershipHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuyerMembershipInfoRepository extends JpaRepository<BuyerMembershipHistory, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM buyer_membership_history b inner join user u on u.user_id=b.fk_user_id  and u.email=:email and is_deleted=0")
    List<BuyerMembershipHistory> findAllBuyerMemberships(@Param("email") String email);

    @Query(value = "SELECT b FROM BuyerMembershipHistory b where b.user.email=:email order by b.membershipStartDate desc, b.membershipType.discountPercentage desc limit 1")
    Optional<BuyerMembershipHistory> getActiveMembershipForTheUser(@Param("email") String email);

    @Query(value = "SELECT b from BuyerMembershipHistory b where b.membershipEndDate>now() and b.membershipType.enablePromotionalNotifications=true and b.user.isDeleted=false group by b.user.userId")
    List<BuyerMembershipHistory> getAllActiveBuyerMemberships();
}
