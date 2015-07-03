package org.mzalive.continuelibrary;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.mzalive.continuelibrary.communication.MdEncode;
import org.mzalive.continuelibrary.communication.UserInfo;

import java.util.ArrayList;

/**
 * Created by Trigger on 2015/7/1.
 */
public class LoginActivity  extends Activity {

    private EditText etUsername;
    private EditText etPassword;
    private Button   btnLogin;
    SharedPreferences sp;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText)findViewById(R.id.text_username);
        etPassword = (EditText)findViewById(R.id.text_password);
        btnLogin   = (Button)findViewById(R.id.button_login);
        sp         = this.getSharedPreferences("UserInfo", MODE_PRIVATE);
    }

    //登录按钮点击事件
    public void clickLogin(View v){
        new Login().execute();
    }

    //异步线程实现登录请求
    class Login extends AsyncTask<Integer, Integer,ArrayList<String>>{
        private String username;
        private String password;
        private SharedPreferences sp;

        @Override
        protected void onPreExecute(){
            btnLogin.setClickable(false);
            username = etUsername.getText().toString();
            password = MdEncode.encode(etPassword.getText().toString());
            sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
            Log.d("username", username);
            Log.d("password", password);
        }

        @Override
        protected ArrayList<String> doInBackground(Integer... params){
            ArrayList<String> result = getLoginResult(username, password);
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result){
            SharedPreferences.Editor editor = sp.edit();
            int errorCode = Integer.valueOf(result.get(0));
            String userId = result.get(1);
            if(errorCode == 1000){
                editor.putBoolean("isLogin", true);
                editor.putString("userId", userId);
                editor.putString("username", username);
            }
            else{
                editor.putBoolean("isLogin", false);
            }
            //test sharedPreferences files
//            SharedPreferences t = getSharedPreferences("UserInfo", MODE_PRIVATE);
//            SharedPreferences.Editor e = t.edit();
//            boolean value = t.getBoolean("isLogin", false);
//            Log.d("isLogin", String.valueOf(value));

            btnLogin.setClickable(true);
            switch (errorCode){
                case 0:
                    Toast.makeText(LoginActivity.this, "用户名密码不能为空",Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    Toast.makeText(LoginActivity.this, "未知错误1，请重试！",Toast.LENGTH_SHORT).show();
                    break;
                case 999:
                    Toast.makeText(LoginActivity.this, "未知错误2，请重试！",Toast.LENGTH_SHORT).show();
                    break;
                case 9999:
                    Toast.makeText(LoginActivity.this, "网络错误，请重试！",Toast.LENGTH_SHORT).show();
                    break;
                case 2001:
                    Toast.makeText(LoginActivity.this, "用户名或密码错误，请重试！",Toast.LENGTH_SHORT).show();
                    break;
                case 1000:
                    Toast.makeText(LoginActivity.this, "登录成功！",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                    LoginActivity.this.finish();
                    break;
                default:
                    break;
            }
        }
    }
    private static ArrayList<String> getLoginResult(String username, String password){
        ArrayList<String> result = new ArrayList<>();
        String errorCode = "";
        String userId = "";
        if(username.equals("") || password.equals("")){
            errorCode =  "0";
        }
        else{
            String loginResult = UserInfo.login(username, password);
            try{
                JSONTokener jsonTokener = new JSONTokener(loginResult);
                JSONObject object = (JSONObject) jsonTokener.nextValue();
                errorCode = object.getString("error_code");
                if(errorCode == "1000"){
                    userId = object.getString("user_id");
                }
            }catch (JSONException e){
                e.printStackTrace();
                errorCode = "-1";
            }
        }
        result.add(errorCode);
        result.add(userId);
        return result;
    }

}
