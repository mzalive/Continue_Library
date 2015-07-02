package org.mzalive.communication;

import java.util.ArrayList;

/**
 * Created by Trigger on 2015/6/30.
 */
public class Search {
    public static String search(String user_id, String keyword, int book_start, int book_count, int wish_start, int wish_count){
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
        return result;
    }
}
