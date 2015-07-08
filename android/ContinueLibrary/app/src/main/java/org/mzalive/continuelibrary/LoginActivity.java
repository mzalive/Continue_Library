package org.mzalive.continuelibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.mzalive.continuelibrary.communication.GlobalSettings;
import org.mzalive.continuelibrary.communication.MdEncode;
import org.mzalive.continuelibrary.communication.UserInfo;

import java.util.ArrayList;

/**
 * Created by Trigger on 2015/7/1.
 */
public class LoginActivity  extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText etUsername;
    private EditText etPassword;
    private Button   btnLogin;
    SharedPreferences sp;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);


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
            sp = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
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
            if(errorCode == GlobalSettings.RESULT_OK){
                editor.putBoolean("isLogin", true);
                editor.putString("userId", userId);
                editor.putString("username", username);
                Log.d("userId_writeSp", userId);
                Log.d("username_writeSp", username);
            }
            else{
                editor.putBoolean("isLogin", false);
                Log.d("isLogin_write", "false");
            }
            editor.commit();
            //test sharedPreferences files
//            SharedPreferences t = getSharedPreferences("UserInfo", MODE_PRIVATE);
//            SharedPreferences.Editor e = t.edit();
//            boolean value = t.getBoolean("isLogin", false);
//            Log.d("isLogin", String.valueOf(value));

            btnLogin.setClickable(true);
            switch (errorCode){
                case GlobalSettings.USER_NAME_PASSWORD_NULL:
                    Toast.makeText(LoginActivity.this, "用户名密码不能为空",Toast.LENGTH_SHORT).show();
                    break;
                case GlobalSettings.JSON_EXCEPTION_ERROR:
                    Toast.makeText(LoginActivity.this, "未知错误1，请重试！",Toast.LENGTH_SHORT).show();
                    break;
                case GlobalSettings.UNKNOWN_ERROR:
                    Toast.makeText(LoginActivity.this, "未知错误2，请重试！",Toast.LENGTH_SHORT).show();
                    break;
                case GlobalSettings.NETWORK_ERROR:
                    Toast.makeText(LoginActivity.this, "网络错误，请重试！",Toast.LENGTH_SHORT).show();
                    break;
                case GlobalSettings.AUTHORIZATION_ERROR:
                    Toast.makeText(LoginActivity.this, "用户名或密码错误，请重试！",Toast.LENGTH_SHORT).show();
                    break;
                case GlobalSettings.RESULT_OK:
                    Toast.makeText(LoginActivity.this, "登录成功！",Toast.LENGTH_SHORT).show();
                    LoginActivity.this.finish();
                    break;
                default:
                    break;
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
                if(errorCode.equals("1000")){
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
