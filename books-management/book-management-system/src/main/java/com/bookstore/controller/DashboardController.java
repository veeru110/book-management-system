package com.bookstore.controller;

import com.bookstore.service.DashboardService;
import com.bookstore.vo.DashboardVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class DashboardController {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardVo> stats() {
        DashboardVo dashboardVo = dashboardService.getDashboardStats();
        if (Objects.isNull(dashboardVo) || !StringUtils.isEmpty(dashboardVo.getFailureMessage())) {
            if(Objects.isNull(dashboardVo)){
                dashboardVo = new DashboardVo();
            }
            return new ResponseEntity<>(dashboardVo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(dashboardVo, HttpStatus.OK);
    }

}
