package com.bookstore.controller;

import com.bookstore.command.BuyerMembershipCommand;
import com.bookstore.service.BuyerMembershipService;
import com.bookstore.vo.BuyerMembershipVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("buyerMemberships")
@PreAuthorize("hasRole('BUYER','ADMIN')")
public class BuyerMembershipController {

    private final BuyerMembershipService buyerMembershipService;

    @Autowired
    public BuyerMembershipController(BuyerMembershipService buyerMembershipService) {
        this.buyerMembershipService = buyerMembershipService;
    }

    @GetMapping("/getAllMemberships")
    public ResponseEntity<List<BuyerMembershipVo>> getAllBuyerMemberships() {
        List<BuyerMembershipVo> buyerMembershipVos = buyerMembershipService.getAllBuyersMemberships();
        return new ResponseEntity<>(buyerMembershipVos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('BUYER')")
    @PostMapping("/buyMembership")
    public ResponseEntity<BuyerMembershipVo> buyMembership(@RequestBody BuyerMembershipCommand buyerMembershipCommand) {
        BuyerMembershipVo buyerMembershipVo = buyerMembershipService.buyMembership(buyerMembershipCommand);
        return new ResponseEntity<>(buyerMembershipVo, HttpStatus.OK);
    }
}
