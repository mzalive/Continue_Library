package org.mzalive.continuelibrary.communication;

import java.util.ArrayList;

/**
 * Created by Trigger on 2015/6/26.
 */
public class BookManage {
    //borrowBook方法，从书库中借阅书籍
    //参数说明：
    //book_isbn:书籍的ISBN
    //user_id:用户ID
    public static String borrowBook(String book_isbn, String user_id){
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("user_id");
        keys_name.add("book_isbn");
        keys_value.add(user_id);
        keys_value.add(book_isbn);
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_BORROWBOOK);
        return result;
    }

    //returnBook方法，还书
    //参数说明：
    //book_isbn:书籍的ISBN
    //user_id:用户ID
    public static String returnBook(String book_isbn, String user_id){
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("user_id");
        keys_name.add("book_isbn");
        keys_value.add(user_id);
        keys_value.add(book_isbn);
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_RETURNBOOK);
        return result;
    }

    //getBooklist方法，获取书库的书籍信息
    //参数说明：
    //start:获取结果的Offset
    //count:获取结果的数量
    public static String getBooklist(int start, int count){
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("start");
        keys_name.add("count");
        keys_value.add(Integer.toString(start));
        keys_value.add(Integer.toString(count));
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_GETBOOKLIST);
        return result;
    }

    //getBookCount方法，获取书籍的库存
    //参数说明：
    //book_id:书籍的ID
    public static String getBookCount(String book_isbn){
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("book_isbn");
        keys_value.add(book_isbn);
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_GETBOOKCOUNT);
        return result;
    }


}
