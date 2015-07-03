package org.mzalive.continuelibrary.communication;

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
    public static String getMyBorrowlist(String user_id, int start, int count){
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("user_id");
        keys_name.add("start");
        keys_name.add("count");
        keys_value.add(user_id);
        keys_value.add(Integer.toString(start));
        keys_value.add(Integer.toString(count));
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_GETMYBORROWLIST);
        return result;
    }

    //getMyWishlist方法，获取用户的心愿单列表
    //参数说明：
    //user_id:用户ID
    //start:获取结果的Offset
    //count:获取结果的数量
    public static String getMyWishlist(String user_id, int start, int count){
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("user_id");
        keys_name.add("start");
        keys_name.add("count");
        keys_value.add(user_id);
        keys_value.add(Integer.toString(start));
        keys_value.add(Integer.toString(count));
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_GETMYWISHLIST);
        return result;
    }

    //setPassword,设置用户密码
    //参数说明：
    //old_password:原来的密码
    //new_password:修改后的密码
    //user_id:用户ID
    public static String setPassword(String old_password, String new_password, String user_id){
        ArrayList<String> keys_name = new ArrayList<>();
        ArrayList<String> keys_value = new ArrayList<>();
        keys_name.add("user_id");
        keys_name.add("old_password");
        keys_name.add("new_password");
        keys_value.add(user_id);
        keys_value.add(old_password);
        keys_value.add(new_password);
        String result = BaseFunctions.httpConnection(keys_name, keys_value, GlobalSettings.ACTION_SETPASSWORD);
        return result;
    }
}
