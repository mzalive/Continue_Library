package org.mzalive.continuelibrary;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextWatcher;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.mzalive.continuelibrary.communication.MdEncode;
import org.mzalive.continuelibrary.communication.UserInfo;

/**
 * Created by Trigger on 2015/7/6.
 */
public class SetPasswordActivity extends Activity{
    private EditText etOldPasswordContent;
    private EditText etNewPasswordContent;
    private EditText etConfirmPasswordContent;
    private Button   btnModify;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        etOldPasswordContent     = (EditText)findViewById(R.id.text_old_password_content);
        etNewPasswordContent     = (EditText)findViewById(R.id.text_new_password_content);
        etConfirmPasswordContent = (EditText)findViewById(R.id.text_confirm_password_content);
        btnModify                = (Button)findViewById(R.id.button_modify);

//        SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
//        String username = sp.getString("username", "");
//        tvUsernameContent.setText(username);


    }
    public void clickModify(View v){
        if(etOldPasswordContent.getText().toString().equals("")){
            Toast.makeText(SetPasswordActivity.this, "原始密码不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!etNewPasswordContent.getText().toString().equals(etConfirmPasswordContent.getText().toString())){
            Toast.makeText(SetPasswordActivity.this, "两次输入的密码不一致！",Toast.LENGTH_SHORT).show();
            return;
        }
        new setPassword().execute();
    }

    class setPassword extends AsyncTask<Integer, Integer, String>{
        private String userId;
        private String oldPassword;
        private String newPassword;
        @Override
        protected void onPreExecute(){
            btnModify.setClickable(false);
            SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
            userId = sp.getString("userId", "");
            oldPassword = MdEncode.encode(etOldPasswordContent.getText().toString());
            newPassword = MdEncode.encode(etNewPasswordContent.getText().toString());
            Log.d("userId", userId);
            Log.d("oldPassword", oldPassword);
            Log.d("newPassword", newPassword);
            etOldPasswordContent.setText("");
            etNewPasswordContent.setText("");
            etConfirmPasswordContent.setText("");
        }
        @Override
        protected String doInBackground(Integer...params){
            String result = getSetPassword(userId, oldPassword, newPassword);
            return result;
        }
        @Override
        protected void onPostExecute(String result){
            btnModify.setClickable(true);
            int errorCode = Integer.valueOf(result);
            switch (errorCode){
                case -1:
                    Toast.makeText(SetPasswordActivity.this, "未知错误，请重试！",Toast.LENGTH_SHORT).show();
                    break;
                case 1000:
                    Toast.makeText(SetPasswordActivity.this, "修改成功！",Toast.LENGTH_SHORT).show();
                    break;
                case 2001:
                    Toast.makeText(SetPasswordActivity.this, "修改失败！",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private static String getSetPassword(String userId, String oldPassword, String newPassword){
        String result = "-1";
        String setResult = UserInfo.setPassword(oldPassword, newPassword,userId);
        try{
            JSONTokener jsonTokener = new JSONTokener(setResult);
            JSONObject object = (JSONObject)jsonTokener.nextValue();
            result = object.getString("error_code");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }

}
