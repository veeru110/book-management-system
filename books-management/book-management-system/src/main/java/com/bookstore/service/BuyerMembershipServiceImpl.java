package com.bookstore.service;

import com.bookstore.command.BuyerMembershipCommand;
import com.bookstore.constants.UserRole;
import com.bookstore.dao.IBuyerMembershipManager;
import com.bookstore.dao.IMembershipManager;
import com.bookstore.exception.DuplicateMembershipException;
import com.bookstore.exception.MembershipNotFoundException;
import com.bookstore.model.BuyerMembershipHistory;
import com.bookstore.model.MembershipTypes;
import com.bookstore.model.User;
import com.bookstore.utils.UserUtils;
import com.bookstore.vo.BuyerMembershipVo;
import com.bookstore.vo.MembershipTypeVo;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BuyerMembershipServiceImpl implements BuyerMembershipService {

    private static final Mapper mapper = new DozerBeanMapper();
    private static final Logger logger = LogManager.getLogger(BuyerMembershipServiceImpl.class);

    private final IBuyerMembershipManager buyerMembershipManager;
    private final IMembershipManager membershipManager;
    private final UserUtils userUtils;


    @Autowired
    public BuyerMembershipServiceImpl(IBuyerMembershipManager buyerMembershipManager, UserUtils userUtils, IMembershipManager membershipManager) {
        this.buyerMembershipManager = buyerMembershipManager;
        this.userUtils = userUtils;
        this.membershipManager = membershipManager;
    }


    @Override
    public List<BuyerMembershipVo> getAllBuyersMemberships() {
        try {
            User user = userUtils.getUser();
            String username = null;
            if (!user.getRole().equals(UserRole.ADMIN)) {
                username = user.getEmail();
            }
            List<BuyerMembershipHistory> buyerMembershipHistory = buyerMembershipManager.getAllBuyerMembershipInfo(username);
            return buyerMembershipHistory.stream().map(b -> mapper.map(b, BuyerMembershipVo.class)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error while fetching buyer memberships", e);
            throw e;
        }
    }

    @Override
    public BuyerMembershipVo buyMembership(BuyerMembershipCommand buyerMembershipCommand) {
        try {
            User user = userUtils.getUser();
            if (user.getRole().equals(UserRole.ADMIN)) {
                throw new AuthorizationServiceException("Not Authorized");
            }
            MembershipTypes membershipTypes = membershipManager.findByPremiumLevelAndMembershipDuration(buyerMembershipCommand.getMembershipPremiumLevel(), buyerMembershipCommand.getMembershipDurationInDays());
            if (Objects.isNull(membershipTypes)) {
                throw new MembershipNotFoundException("Membership not found for premium " + buyerMembershipCommand.getMembershipPremiumLevel() + " and duration " + buyerMembershipCommand.getMembershipDurationInDays());
            }
            BuyerMembershipHistory buyerMembershipHistory = new BuyerMembershipHistory();
            buyerMembershipHistory.setUser(user);
            buyerMembershipHistory.setMembershipType(membershipTypes);
            buyerMembershipHistory.setMembershipEndDate(DateUtils.addDays(buyerMembershipHistory.getMembershipStartDate(), membershipTypes.getMembershipDurationInDays()));
            buyerMembershipHistory.setMembershipPrice(membershipTypes.getMembershipPrice());
            buyerMembershipManager.save(buyerMembershipHistory);
            return mapper.map(buyerMembershipHistory, BuyerMembershipVo.class);
        } catch (Exception e) {
            logger.error("Error while buying membership", e);
            throw e;
        }
    }

    @Override
    public MembershipTypeVo addMembershipLevel(BuyerMembershipCommand buyerMembershipCommand) throws Exception {
        try {
            MembershipTypes membershipTypes = membershipManager.findByPremiumLevelAndMembershipDuration(buyerMembershipCommand.getMembershipPremiumLevel(), buyerMembershipCommand.getMembershipDurationInDays());
            if (!Objects.isNull(membershipTypes)) {
                throw new DuplicateMembershipException("Membership Already exists exception with " + buyerMembershipCommand.getMembershipPremiumLevel() + " and " + buyerMembershipCommand.getMembershipDurationInDays() + " days");
            }
            membershipTypes = mapper.map(buyerMembershipCommand, MembershipTypes.class);
            membershipTypes = membershipManager.save(membershipTypes);
            return mapper.map(membershipTypes, MembershipTypeVo.class);
        } catch (Exception e) {
            logger.error("Error while adding new membership", e);
            throw e;
        }
    }
}
