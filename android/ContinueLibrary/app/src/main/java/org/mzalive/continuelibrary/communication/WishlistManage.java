package org.mzalive.continuelibrary.communication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.mzalive.continuelibrary.Base.Book;
import org.mzalive.continuelibrary.Base.BookList;

import java.util.ArrayList;

/**
 * Created by Trigger on 2015/6/26.
 */
public class WishlistManage {
    //getWishlist方法，获取所有的心愿单
    //参数说明：
    //start:获取结果的Offset
    //count:获取结果的数量
    public static BookList getWishlist(String user_id, int start, int count){
        BookList bookList = new BookList();

        ArrayList<Book> booksArray = new ArrayList<>();
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("user_id");
        keys_name.add("start");
        keys_name.add("count");
        keys_value.add(user_id);
        keys_value.add(Integer.toString(start));
        keys_value.add(Integer.toString(count));
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_GETWISHLIST);

        try{
            JSONTokener jsonTokener = new JSONTokener(result);
            JSONObject object = (JSONObject)jsonTokener.nextValue();
            bookList.setErrorCode(object.getString("error_code"));
            bookList.setBookStart(object.getInt("book_start"));
            bookList.setBookCount(object.getInt("book_count"));
            bookList.setBookTotal(object.getInt("book_total"));
            bookList.setWishStart(object.getInt("wish_start"));
            bookList.setWishCount(object.getInt("wish_count"));
            bookList.setWishTotal(object.getInt("wish_total"));
            JSONArray books = object.getJSONArray("books");
            for(int i = 0; i<books.length(); i++){
                JSONObject book = (JSONObject)books.get(i);
                String isbn = book.getString("isbn");
                String title = book.getString("title");
                String subTitle = book.getString("subtitle");
                ArrayList<String> author = new ArrayList<>();
                JSONArray authors = book.getJSONArray("author");
                for(int j = 0; j < authors.length(); j++){
                    author.add(authors.get(j).toString());
                }
                String summary = book.getString("summary");
                String image = book.getString("image");
                String publisher = book.getString("publisher");
                String publishDate = book.getString("pubdate");
                JSONObject status = book.getJSONObject("status");
                boolean isInStock = status.getBoolean("is_in_stock");
                int amountTotal = status.getInt("amount_total");
                int heat = status.getInt("heat");
                boolean isWanted = status.getBoolean("is_wanted");

                Book newBook = new Book(isbn, title, subTitle, publisher, image, summary, publishDate, author, isInStock, amountTotal, heat, isWanted);
                booksArray.add(newBook);
            }
            bookList.setBooks(booksArray);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return bookList;
    }

    //addHeat 方法，增加心愿单中书籍的热度
    //参数说明：
    //user_id:用户ID
    //book_isbn:ISBN
    //is_owned:wishlist是否已经有这本书了
    public static String addHeat(String user_id, String book_isbn, boolean is_owned){
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("user_id");
        keys_name.add("book_isbn");
        keys_name.add("is_owned");
        keys_value.add(user_id);
        keys_value.add(book_isbn);
        keys_value.add(Boolean.toString(is_owned));
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_ADDHEAT);
        return result;
    }

}
