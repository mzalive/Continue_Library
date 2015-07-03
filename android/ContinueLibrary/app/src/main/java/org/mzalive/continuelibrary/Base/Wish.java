package org.mzalive.continuelibrary.Base;

import java.util.ArrayList;

/**
 * Created by Trigger on 2015/7/3.
 */
public class Wish extends BookBase {
    public boolean isWanted;
    public int heat;

    public Wish(boolean isWanted, int heat, String title, String subTitle, String isbn, String publisher, String image, String summary, String publishDate, ArrayList<String> author){
        this.isWanted = isWanted;
        this.heat = heat;
        this.setTitle(title);
        this.setSubTitle(subTitle);
        this.setIsbn(isbn);
        this.setPublisher(publisher);
        this.setImage(image);
        this.setSubTitle(summary);
        this.setPublishDate(publishDate);
        this.setAuthor(author);
    }

    public void setIsWanted(boolean isWanted) {
        this.isWanted = isWanted;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

    public boolean isWanted() {
        return isWanted;
    }

    public int getHeat() {
        return heat;
    }
}
