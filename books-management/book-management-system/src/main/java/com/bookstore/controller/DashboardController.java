package com.bookstore.controller;

import com.bookstore.service.DashboardService;
import com.bookstore.vo.DashboardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DashboardController {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<DashboardVo> stats() throws RuntimeException {
        DashboardVo dashboardVo = dashboardService.getDashboardStats();
        return new ResponseEntity<>(dashboardVo, HttpStatus.OK);
    }

}
