package com.bookstore.service;

import com.bookstore.dao.IBookManager;
import com.bookstore.dao.IUserManager;
import com.bookstore.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private static final Logger logger = LogManager.getLogger(DashboardServiceImpl.class);


    private final IUserManager userManager;
    private final IBookManager bookManager;

    @Autowired
    public DashboardServiceImpl(IUserManager userManager, IBookManager bookManager) {
        this.userManager = userManager;
        this.bookManager = bookManager;
    }

    @Override
    public DashboardVo getDashboardStats() throws RuntimeException {

        DashboardVo dashboardVo = new DashboardVo();
        try {
            Integer usersCount = userManager.countAllUsers();
            dashboardVo.setRegisteredUsers(usersCount);

            List<BookVo> availableBookStock = bookManager.getAvailableBooksStockInfo();
            BookStoreStock bookStoreStock = new BookStoreStock();
            bookStoreStock.setBooksStock(availableBookStock);
            bookStoreStock.setNoOfBooksInStore(availableBookStock.stream().map(BookVo::getAvailableStock).reduce(0L, Long::sum));
            dashboardVo.setBookStoreStock(bookStoreStock);

            List<BooksSaleInfo> booksSalesInfo = bookManager.getBookSalesInfo();
            BookStoreSalesInfo bookStoreSalesInfo = new BookStoreSalesInfo();
            bookStoreSalesInfo.setBooksSalesInfo(booksSalesInfo);
            //bookStoreSalesInfo.setTotalBooksSold(booksSalesInfo.stream().map(BooksSaleInfo::getTotalSales).reduce(0L, Long::sum));
            bookStoreSalesInfo.setTotalSaleAmount(booksSalesInfo.stream().map(BooksSaleInfo::getTotalAmount).reduce(0d, Double::sum));
            dashboardVo.setBookStoreSalesInfo(bookStoreSalesInfo);
        } catch (Exception e) {
            logger.error("Error while fetching dashboard stats", e);
        }
        return dashboardVo;
    }
}
