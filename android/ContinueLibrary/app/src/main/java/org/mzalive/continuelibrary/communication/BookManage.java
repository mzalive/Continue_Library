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
 * Created by Trigger on 2015/6/26.
 */
public class BookManage {
    //borrowBook方法，从书库中借阅书籍
    //参数说明：
    //book_isbn:书籍的ISBN
    //user_id:用户ID
    public static int borrowBook(String book_isbn, String user_id){
        int errorCode;
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("user_id");
        keys_name.add("book_isbn");
        keys_value.add(user_id);
        keys_value.add(book_isbn);
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_BORROWBOOK);
        try{
            JSONTokener jsonTokener = new JSONTokener(result);
            JSONObject object = (JSONObject)jsonTokener.nextValue();
            errorCode = object.getInt("error_code");
        }catch (JSONException e){
            errorCode = GlobalSettings.JSON_EXCEPTION_ERROR;
            e.printStackTrace();
        }
        return errorCode;
    }

    //returnBook方法，还书
    //参数说明：
    //book_isbn:书籍的ISBN
    //user_id:用户ID
    public static int returnBook(String book_isbn, String user_id){
        int errorCode;
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("user_id");
        keys_name.add("book_isbn");
        keys_value.add(user_id);
        keys_value.add(book_isbn);
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_RETURNBOOK);
        try{
            JSONTokener jsonTokener = new JSONTokener(result);
            JSONObject object = (JSONObject)jsonTokener.nextValue();
            errorCode = object.getInt("error_code");
        }catch (JSONException e){
            errorCode = GlobalSettings.JSON_EXCEPTION_ERROR;
            e.printStackTrace();
        }
        return errorCode;
    }

    //getBooklist方法，获取书库的书籍信息
    //参数说明：
    //start:获取结果的Offset
    //count:获取结果的数量
    public static BookList getBooklist(int start, int count){
        BookList bookList = new BookList();

        ArrayList<Book> booksArray = new ArrayList<>();
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("start");
        keys_name.add("count");
        keys_value.add(Integer.toString(start));
        keys_value.add(Integer.toString(count));
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_GETBOOKLIST);

        try {
            JSONTokener jsonTokener = new JSONTokener(result);
            JSONObject object = (JSONObject) jsonTokener.nextValue();
            bookList.setErrorCode(object.getString("error_code"));
            Log.d("ErrorCode_Booklist", bookList.getErrorCode());
        }catch (JSONException e){
            e.printStackTrace();
        }
        if(!bookList.getErrorCode().equals(String.valueOf(GlobalSettings.RESULT_OK))){
            bookList.setBookStart(0);
            bookList.setBookCount(0);
            bookList.setBookTotal(0);
            bookList.setWishStart(0);
            bookList.setWishCount(0);
            bookList.setWishTotal(0);
            bookList.setBooks(null);
            return bookList;
        }
        try{
            JSONTokener jsonTokener = new JSONTokener(result);
            JSONObject object = (JSONObject) jsonTokener.nextValue();
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
                int location = Book.LOCATION_WISHLIST;
                if(isInStock) location = Book.LOCATION_CONTINUE;
                int amountTotal = status.getInt("amount_total");
                int heat = status.getInt("heat");
                boolean isWanted = status.getBoolean("is_wanted");

                Book newBook = new Book(isbn, title, subTitle, publisher, image, summary, publishDate, author, location, amountTotal, heat, isWanted);
                booksArray.add(newBook);
            }
            bookList.setBooks(booksArray);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return bookList;
    }

    //getBookCount方法，获取书籍的库存
    //参数说明：
    //book_id:书籍的ID
    //如果返回结果不成功，则结果为-1
    public static int getBookCount(String book_isbn){
        int count = -1;
        int errorCode;
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("book_isbn");
        keys_value.add(book_isbn);
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_GETBOOKCOUNT);

        try{
            JSONTokener jsonTokener = new JSONTokener(result);
            JSONObject object = (JSONObject)jsonTokener.nextValue();
            errorCode = object.getInt("error_code");
            if(errorCode == GlobalSettings.RESULT_OK){
                count = object.getInt("amount_available");
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return count;
    }


}
