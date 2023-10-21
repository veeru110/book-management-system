package com.bookstore.vo;

import java.util.ArrayList;
import java.util.List;

public class EmailTableVo {
    private String column1Header;
    private String column2Header;

    private List<EmailTableRow> dataItr = new ArrayList<>();

    public EmailTableVo(String column1Header, String column2Header) {
        this.column1Header = column1Header;
        this.column2Header = column2Header;
    }

    public void addTableRow(EmailTableRow emailTableRow){
        this.dataItr.add(emailTableRow);
    }

    public String getColumn1Header() {
        return column1Header;
    }

    public void setColumn1Header(String column1Header) {
        this.column1Header = column1Header;
    }

    public String getColumn2Header() {
        return column2Header;
    }

    public void setColumn2Header(String column2Header) {
        this.column2Header = column2Header;
    }

    public List<EmailTableRow> getDataItr() {
        return dataItr;
    }

    public void setDataItr(List<EmailTableRow> dataItr) {
        this.dataItr = dataItr;
    }
}
