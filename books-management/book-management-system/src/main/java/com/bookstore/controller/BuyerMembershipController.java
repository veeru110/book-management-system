package com.bookstore.controller;

import com.bookstore.command.BuyerMembershipCommand;
import com.bookstore.model.MembershipTypes;
import com.bookstore.service.BuyerMembershipService;
import com.bookstore.vo.BuyerMembershipVo;
import com.bookstore.vo.MembershipTypeVo;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buyerMemberships")
@PreAuthorize("hasAnyRole('BUYER','ADMIN')")
@SecurityRequirement(name = "Authorization")
public class BuyerMembershipController {

    private final BuyerMembershipService buyerMembershipService;

    @Autowired
    public BuyerMembershipController(BuyerMembershipService buyerMembershipService) {
        this.buyerMembershipService = buyerMembershipService;
    }

    @GetMapping("/getAllAvailableMemberships")
    public ResponseEntity<List<MembershipTypeVo>> getAllAvailableMemberships(){
        List<MembershipTypeVo> membershipTypeVos = buyerMembershipService.getAllAvailableMemberships();
        return new ResponseEntity<>(membershipTypeVos,HttpStatus.OK);
    }

    @GetMapping("/getAllUserMemberships")
    public ResponseEntity<List<BuyerMembershipVo>> getAllBuyerMemberships() {
        List<BuyerMembershipVo> buyerMembershipVos = buyerMembershipService.getAllBuyersMemberships();
        return new ResponseEntity<>(buyerMembershipVos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addMembershipPremiumLevel")
    public ResponseEntity<MembershipTypeVo> addMembershipLevel(@RequestBody BuyerMembershipCommand buyerMembershipCommand) throws Exception {
        MembershipTypeVo membershipTypeVo = buyerMembershipService.addMembershipLevel(buyerMembershipCommand);
        return new ResponseEntity<>(membershipTypeVo, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('BUYER')")
    @PostMapping("/buyMembership")
    public ResponseEntity<BuyerMembershipVo> buyMembership(@RequestBody BuyerMembershipCommand buyerMembershipCommand) throws Exception {
        BuyerMembershipVo buyerMembershipVo = buyerMembershipService.buyMembership(buyerMembershipCommand);
        return new ResponseEntity<>(buyerMembershipVo, HttpStatus.OK);
    }
}
