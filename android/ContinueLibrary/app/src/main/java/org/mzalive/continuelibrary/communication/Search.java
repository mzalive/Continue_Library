package org.mzalive.continuelibrary.communication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.mzalive.continuelibrary.Base.Book;
import org.mzalive.continuelibrary.Base.BookList;

import java.util.ArrayList;

/**
 * Created by Trigger on 2015/6/30.
 */
public class Search {
    public static BookList search(String user_id, String keyword, int book_start, int book_count, int wish_start, int wish_count){
        BookList searchList = new BookList();
        ArrayList<Book> bookArray = new ArrayList<>();
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("user_id");
        keys_name.add("keyword");
        keys_name.add("book_start");
        keys_name.add("book_count");
        keys_name.add("wish_start");
        keys_name.add("wish_count");
        keys_value.add(user_id);
        keys_value.add(keyword);
        keys_value.add(Integer.toString(book_start));
        keys_value.add(Integer.toString(book_count));
        keys_value.add(Integer.toString(wish_start));
        keys_value.add(Integer.toString(wish_count));
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_SEARCH);
       try{
           JSONTokener jsonTokener = new JSONTokener(result);
           JSONObject object = (JSONObject) jsonTokener.nextValue();
           searchList.setErrorCode(object.getString("error_code"));
           Log.d("ErrorCode_SearchResult", searchList.getErrorCode());
       }catch (JSONException e){
           e.printStackTrace();
       }
        if(!searchList.getErrorCode().equals("1000")){
            searchList.setBookStart(0);
            searchList.setBookCount(0);
            searchList.setBookTotal(0);
            searchList.setWishStart(0);
            searchList.setWishCount(0);
            searchList.setWishTotal(0);
            searchList.setBooks(null);
            return searchList;
        }
        try{
            JSONTokener jsonTokener = new JSONTokener(result);
            JSONObject object = (JSONObject)jsonTokener.nextValue();
            searchList.setBookStart(object.getInt("book_start"));
            searchList.setBookCount(object.getInt("book_count"));
            searchList.setBookTotal(object.getInt("book_total"));
            searchList.setWishStart(object.getInt("wish_start"));
            searchList.setWishCount(object.getInt("wish_count"));
            searchList.setWishTotal(object.getInt("wish_total"));
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
                int loction = Book.LOCATION_WISHLIST;
                if(isInStock) loction = Book.LOCATION_CONTINUE;
                int amountTotal = status.getInt("amount_total");
                int heat = status.getInt("heat");
                boolean isWanted = status.getBoolean("is_wanted");

                Book newBook = new Book(isbn, title, subTitle, publisher, image, summary, publishDate, author, loction, amountTotal, heat, isWanted);
                bookArray.add(newBook);
            }
            searchList.setBooks(bookArray);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return searchList;
    }

    public static BookList searchNet(String q, int start, int count){
        BookList netResult = new BookList();
        ArrayList<Book> bookArray = new ArrayList<>();
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("q");
        keys_name.add("start");
        keys_name.add("count");
        keys_value.add(q);
        keys_value.add(Integer.toString(start));
        keys_value.add(Integer.toString(count));
        String result = BaseFunctions.httpUrlConnectionGet(GlobalSettings.REQUEST_GET_URL, keys_name, keys_value);

//        String result = GlobalSettings.FADE_JSON;

        try{
            JSONTokener jsonTokener = new JSONTokener(result);
            JSONObject object = (JSONObject)jsonTokener.nextValue();
            netResult.setBookStart(object.getInt("start"));
            netResult.setBookCount(object.getInt("count"));
            netResult.setBookTotal(object.getInt("total"));
            JSONArray books = object.getJSONArray("books");
            for(int i = 0; i<books.length(); i++){
                JSONObject book = (JSONObject)books.get(i);
                String isbn = book.getString("isbn13");
                String title = book.getString("title");
                String subTitle = book.getString("subtitle");
                ArrayList<String> author = new ArrayList<>();
                JSONArray authors = book.getJSONArray("author");
                for(int j = 0; j < authors.length(); j++){
                    author.add(authors.get(j).toString());
                }
                String summary = book.getString("summary");
                JSONObject images = book.getJSONObject("images");
                String imageUrl = images.getString("large");
                String publisher = book.getString("publisher");
                String publishDate = book.getString("pubdate");
                int location = Book.LOCATION_DOUBAN;
                int amountTotal = 0;
                int heat = 0;
                boolean isWanted = false;

                Book newBook = new Book(isbn, title, subTitle, publisher, imageUrl, summary, publishDate, author, location, amountTotal, heat, isWanted);
                bookArray.add(newBook);
            }
            netResult.setBooks(bookArray);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return netResult;

    }
}
