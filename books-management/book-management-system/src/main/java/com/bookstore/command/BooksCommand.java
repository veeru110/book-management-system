package com.bookstore.command;

public class BooksCommand {
    private String bookName;
    private Integer edition;
    private String authorNames;
    private Long stockInward;
    private String bookCategory;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    public String getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(String authorNames) {
        this.authorNames = authorNames;
    }

    public Long getStockInward() {
        return stockInward;
    }

    public void setStockInward(Long stockInward) {
        this.stockInward = stockInward;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BooksCommand{");
        sb.append("bookName='").append(bookName).append('\'');
        sb.append(", edition=").append(edition);
        sb.append(", authorNames='").append(authorNames).append('\'');
        sb.append(", availableStock=").append(stockInward);
        sb.append(", bookCategory='").append(bookCategory).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
