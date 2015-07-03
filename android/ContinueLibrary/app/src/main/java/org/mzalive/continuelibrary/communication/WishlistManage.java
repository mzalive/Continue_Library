package org.mzalive.continuelibrary.communication;

import java.util.ArrayList;

/**
 * Created by Trigger on 2015/6/26.
 */
public class WishlistManage {
    //getWishlist方法，获取所有的心愿单
    //参数说明：
    //start:获取结果的Offset
    //count:获取结果的数量
    public static String getWishlist(String user_id, int start, int count){
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("user_id");
        keys_name.add("start");
        keys_name.add("count");
        keys_value.add(user_id);
        keys_value.add(Integer.toString(start));
        keys_value.add(Integer.toString(count));
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_GETWISHLIST);
        return result;
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
