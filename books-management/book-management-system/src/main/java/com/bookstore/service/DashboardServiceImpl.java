package com.bookstore.service;

import com.bookstore.dao.IUserManager;
import com.bookstore.vo.DashboardVo;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {


    private final IUserManager userManager;

    public DashboardServiceImpl(IUserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public DashboardVo getDashboardStats() {
        DashboardVo dashboardVo = new DashboardVo();
        Integer usersCount = userManager.countAllUsers();
        dashboardVo.setRegisteredUsers(usersCount);
        return dashboardVo;
    }
}
