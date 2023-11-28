package com.bookstore.vo;

import java.util.List;

public class BookStoreSalesInfo {

    private List<BooksSaleInfo> booksSalesInfo;

    private Long totalBooksSold;

    private Double totalSaleAmount;

    public List<BooksSaleInfo> getBooksSalesInfo() {
        return booksSalesInfo;
    }

    public void setBooksSalesInfo(List<BooksSaleInfo> booksSalesInfo) {
        this.booksSalesInfo = booksSalesInfo;
    }

    public Long getTotalBooksSold() {
        return totalBooksSold;
    }

    public void setTotalBooksSold(Long totalBooksSold) {
        this.totalBooksSold = totalBooksSold;
    }

    public Double getTotalSaleAmount() {
        return totalSaleAmount;
    }

    public void setTotalSaleAmount(Double totalSaleAmount) {
        this.totalSaleAmount = totalSaleAmount;
    }
}
