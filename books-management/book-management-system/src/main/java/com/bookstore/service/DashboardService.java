package com.bookstore.service;

import com.bookstore.vo.DashboardVo;

public interface DashboardService {

    DashboardVo getDashboardStats() throws RuntimeException;
}
