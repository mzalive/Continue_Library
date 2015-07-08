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
public class UserInfo {

    //login方法，提供用户登录
    //参数：
    //username:用户名称
    //password:用户密码
    public static String login(String username, String password){
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("username");
        keys_name.add("password");
        keys_value.add(username);
        keys_value.add(password);
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_LOGIN);
        return result;
    }

    //getMyBorrowlist方法，获取用户的借阅列表
    //参数说明：
    //user_id:用户ID
    //start:获取结果的Offset
    //count:获取结果的数量
    public static BookList getMyBorrowlist(String user_id, int start, int count){
        BookList myBorrowList = new BookList();
        ArrayList<Book> booksArray = new ArrayList<>();
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("user_id");
        keys_name.add("start");
        keys_name.add("count");
        keys_value.add(user_id);
        keys_value.add(Integer.toString(start));
        keys_value.add(Integer.toString(count));
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_GETMYBORROWLIST);

        try {
            JSONTokener jsonTokener = new JSONTokener(result);
            JSONObject object = (JSONObject) jsonTokener.nextValue();
            myBorrowList.setErrorCode(object.getString("error_code"));
            Log.d("ErrorCode_MyBorrowList", myBorrowList.getErrorCode());
        }catch (JSONException e){
            e.printStackTrace();
        }
        if(!myBorrowList.getErrorCode().equals(String.valueOf(GlobalSettings.RESULT_OK))){
            myBorrowList.setBookStart(0);
            myBorrowList.setBookCount(0);
            myBorrowList.setBookTotal(0);
            myBorrowList.setWishStart(0);
            myBorrowList.setWishCount(0);
            myBorrowList.setWishTotal(0);
            myBorrowList.setBooks(null);
            return myBorrowList;
        }

        try{
            JSONTokener jsonTokener = new JSONTokener(result);
            JSONObject object = (JSONObject)jsonTokener.nextValue();
            myBorrowList.setBookStart(object.getInt("book_start"));
            myBorrowList.setBookCount(object.getInt("book_count"));
            myBorrowList.setBookTotal(object.getInt("book_total"));
            myBorrowList.setWishStart(object.getInt("wish_start"));
            myBorrowList.setWishCount(object.getInt("wish_count"));
            myBorrowList.setWishTotal(object.getInt("wish_total"));
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
            myBorrowList.setBooks(booksArray);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return myBorrowList;
    }

    //getMyWishlist方法，获取用户的心愿单列表
    //参数说明：
    //user_id:用户ID
    //start:获取结果的Offset
    //count:获取结果的数量
    public static BookList getMyWishlist(String user_id, int start, int count){
        BookList myWishList = new BookList();
        ArrayList<Book> booksArray = new ArrayList<>();
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("user_id");
        keys_name.add("start");
        keys_name.add("count");
        keys_value.add(user_id);
        keys_value.add(Integer.toString(start));
        keys_value.add(Integer.toString(count));
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_GETMYWISHLIST);

        try {
            JSONTokener jsonTokener = new JSONTokener(result);
            JSONObject object = (JSONObject) jsonTokener.nextValue();
            myWishList.setErrorCode(object.getString("error_code"));
            Log.d("ErrorCode_getMyWishlist", myWishList.getErrorCode());
        }catch (JSONException e){
            e.printStackTrace();
        }
        if(!myWishList.getErrorCode().equals(String.valueOf(GlobalSettings.RESULT_OK))){
            myWishList.setBookStart(0);
            myWishList.setBookCount(0);
            myWishList.setBookTotal(0);
            myWishList.setWishStart(0);
            myWishList.setWishCount(0);
            myWishList.setWishTotal(0);
            myWishList.setBooks(null);
            return myWishList;
        }
        try{
            JSONTokener jsonTokener = new JSONTokener(result);
            JSONObject object = (JSONObject)jsonTokener.nextValue();
            myWishList.setErrorCode(object.getString("error_code"));
            myWishList.setBookStart(object.getInt("book_start"));
            myWishList.setBookCount(object.getInt("book_count"));
            myWishList.setBookTotal(object.getInt("book_total"));
            myWishList.setWishStart(object.getInt("wish_start"));
            myWishList.setWishCount(object.getInt("wish_count"));
            myWishList.setWishTotal(object.getInt("wish_total"));
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
            myWishList.setBooks(booksArray);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return myWishList;
    }

    //setPassword,设置用户密码
    //参数说明：
    //old_password:原来的密码
    //new_password:修改后的密码
    //user_id:用户ID
    public static int setPassword(String old_password, String new_password, String user_id){
        int errorCode;
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("user_id");
        keys_name.add("old_password");
        keys_name.add("new_password");
        keys_value.add(user_id);
        keys_value.add(old_password);
        keys_value.add(new_password);
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_SETPASSWORD);

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

    //getHasBorrowed，获取用户是否借阅某本书的信息
    //参数说明：
    //user_id:用户ID
    //book_isbn:书籍ISBN
    public static String getHasBorrowed(String user_id, String book_isbn){
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("user_id");
        keys_name.add("book_isbn");
        keys_value.add(user_id);
        keys_value.add(book_isbn);
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_GETHASBORROWED);
        return result;
    }
}
