package org.mzalive.continuelibrary.Base;

import java.util.ArrayList;

/**
 * Created by Trigger on 2015/7/3.
 */
public class Book {
    private String isbn;
    private String title;
    private String subTitle;
    private String publisher;
    private String image;
    private String summary;
    private String publishDate;
    private ArrayList<String> author = new ArrayList<>();

    private boolean isInStock;
    private int     amountTotal;
    private int     heat;
    private boolean isWanted;

    public Book(){}

    public Book(String isbn, String title, String subTitle, String publisher, String image, String summary, String publishDate, ArrayList<String> author, boolean isInStock, int amountTotal, int heat, boolean isWanted) {
        this.isbn = isbn;
        this.title = title;
        this.subTitle = subTitle;
        this.publisher = publisher;
        this.image = image;
        this.summary = summary;
        this.publishDate = publishDate;
        this.author = author;
        this.isInStock = isInStock;
        this.amountTotal = amountTotal;
        this.heat = heat;
        this.isWanted = isWanted;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getImage() {
        return image;
    }

    public String getSummary() {
        return summary;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public ArrayList<String> getAuthor() {
        return author;
    }

    public boolean isInStock() {
        return isInStock;
    }

    public int getAmountTotal() {
        return amountTotal;
    }

    public int getHeat() {
        return heat;
    }

    public boolean isWanted() {
        return isWanted;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public void setAuthor(ArrayList<String> author) {
        this.author = author;
    }

    public void setIsInStock(boolean isInStock) {
        this.isInStock = isInStock;
    }

    public void setAmountTotal(int amountTotal) {
        this.amountTotal = amountTotal;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

    public void setIsWanted(boolean isWanted) {
        this.isWanted = isWanted;
    }
}
