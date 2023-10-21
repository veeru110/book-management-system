package com.bookstore.command;

public class BooksCommand {
    private String bookName;
    private Integer edition;
    private String authorNames;
    private Integer stockInward;
    private String genre;

    private Double totalPrice;

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

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

    public Integer getStockInward() {
        return stockInward;
    }

    public void setStockInward(Integer stockInward) {
        this.stockInward = stockInward;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BooksCommand{");
        sb.append("bookName='").append(bookName).append('\'');
        sb.append(", edition=").append(edition);
        sb.append(", authorNames='").append(authorNames).append('\'');
        sb.append(", stockInward=").append(stockInward);
        sb.append(", genre='").append(genre).append('\'');
        sb.append(", totalPrice=").append(totalPrice);
        sb.append('}');
        return sb.toString();
    }
}
