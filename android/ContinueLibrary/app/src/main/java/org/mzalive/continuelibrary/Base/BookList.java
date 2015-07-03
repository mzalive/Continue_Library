package org.mzalive.continuelibrary.Base;

import java.util.ArrayList;

/**
 * Created by Trigger on 2015/7/3.
 */
public class BookList {
    private String errorCode;
    private int    bookStart;
    private int    bookCount;
    private int    bookTotal;
    private int    wishStart;
    private int    wishCount;
    private int    wishTotal;

    private ArrayList<Book> books = new ArrayList<>();

    public BookList(){
        this.errorCode = "6001"; //JSON解析错误
    };

    public BookList(String errorCode, int bookStart, int bookCount, int bookTotal, int wishStart, int wishCount, int wishTotal, ArrayList<Book> books) {
        this.errorCode = errorCode;
        this.bookStart = bookStart;
        this.bookCount = bookCount;
        this.bookTotal = bookTotal;
        this.wishStart = wishStart;
        this.wishCount = wishCount;
        this.wishTotal = wishTotal;
        this.books = books;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setBookStart(int bookStart) {
        this.bookStart = bookStart;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public void setBookTotal(int bookTotal) {
        this.bookTotal = bookTotal;
    }

    public void setWishStart(int wishStart) {
        this.wishStart = wishStart;
    }

    public void setWishCount(int wishCount) {
        this.wishCount = wishCount;
    }

    public void setWishTotal(int wishTotal) {
        this.wishTotal = wishTotal;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public int getBookStart() {
        return bookStart;
    }

    public int getBookCount() {
        return bookCount;
    }

    public int getBookTotal() {
        return bookTotal;
    }

    public int getWishStart() {
        return wishStart;
    }

    public int getWishCount() {
        return wishCount;
    }

    public int getWishTotal() {
        return wishTotal;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}
