package org.mzalive.continuelibrary.Base;

import java.util.ArrayList;

/**
 * Created by Trigger on 2015/7/3.
 */
public class Book extends BookBase{
    private int amountTotal;

    public Book(int amountTotal, String title, String subTitle, String isbn, String publisher, String image, String summary, String publishDate, ArrayList<String> author){
        this.amountTotal = amountTotal;
        this.setTitle(title);
        this.setSubTitle(subTitle);
        this.setIsbn(isbn);
        this.setPublisher(publisher);
        this.setImage(image);
        this.setSubTitle(summary);
        this.setPublishDate(publishDate);
        this.setAuthor(author);
    }

    public int getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(int amountTotal) {
        this.amountTotal = amountTotal;
    }
}
