package org.mzalive.continuelibrary.Base;

import java.util.ArrayList;

/**
 * Created by Trigger on 2015/7/3.
 */
public class BookBase {
    private String title;
    private String subTitle;
    private String isbn;
    private String publisher;
    private String image;
    private String summary;
    private String publishDate;
    private ArrayList<String> author = new ArrayList<>();

    public BookBase(){};

    public BookBase(String title, String subTitle, String isbn, String publisher, String image, String summary, String publishDate, ArrayList<String>author){
        this.title = title;
        this.subTitle = subTitle;
        this.isbn = isbn;
        this.publisher = publisher;
        this.image = image;
        this.summary = summary;
        this.publishDate = publishDate;
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getIsbn() {
        return isbn;
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

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAuthor(ArrayList<String> author) {
        this.author = author;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
}
