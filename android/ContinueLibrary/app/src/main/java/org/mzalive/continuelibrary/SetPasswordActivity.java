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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.mzalive.continuelibrary.communication.UserInfo;

/**
 * Created by Trigger on 2015/7/6.
 */
public class SetPasswordActivity extends Activity{
    private TextView tvUsername;
    private TextView tvUsernameContent;
    private TextView tvOldPassword;
    private EditText etOldPasswordContent;
    private TextView tvWarningOld;
    private TextView tvNewPassword;
    private EditText etNewPasswordContent;
    private TextView tvWarningNew;
    private TextView tvConfirmPassword;
    private EditText etConfirmPasswordContent;
    private TextView tvWarningConfirm;
    private Button   btnModify;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        tvUsername               = (TextView)findViewById(R.id.text_userid);
        tvUsernameContent        = (TextView)findViewById(R.id.text_userid_content);
        tvOldPassword            = (TextView)findViewById(R.id.text_old_password);
        etOldPasswordContent     = (EditText)findViewById(R.id.text_old_password_coontent);
        tvWarningOld             = (TextView)findViewById(R.id.text_warning_old);
        tvNewPassword            = (TextView)findViewById(R.id.text_new_password);
        etNewPasswordContent     = (EditText)findViewById(R.id.text_new_password_content);
        tvWarningNew             = (TextView)findViewById(R.id.text_warning_new);
        tvConfirmPassword        = (TextView)findViewById(R.id.text_confirm_password);
        etConfirmPasswordContent = (EditText)findViewById(R.id.text_confirm_password_content);
        tvWarningConfirm         = (TextView)findViewById(R.id.text_warning_confirm);
        btnModify                = (Button)findViewById(R.id.button_modify);

        SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String username = sp.getString("username", "");
        tvUsernameContent.setText(username);

        textChange change = new textChange();
        etOldPasswordContent.addTextChangedListener(change);
        etNewPasswordContent.addTextChangedListener(change);
        etConfirmPasswordContent.addTextChangedListener(change);

    }
    class textChange implements TextWatcher{
        @Override
        public void afterTextChanged(Editable arg0){

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before, int count){
            if((!etConfirmPasswordContent.getText().toString().equals(etNewPasswordContent.getText().toString()))){
                tvWarningConfirm.setText("两次输入的密码不一致，请重新输入");
            }
            else{
                tvWarningConfirm.setText("");
            }
            if((etNewPasswordContent.getText().length() < 6) && (etNewPasswordContent.getText().length() > 0)){
                tvWarningNew.setText("请输入6-16位密码，字母区分大小写");
            }
            else{
                tvWarningNew.setText("");
            }
            if((etOldPasswordContent.getText().length() > 0) && (etOldPasswordContent.getText().length() < 6)){
                tvWarningOld.setText("请输入6-16位密码，字母区分大小写");
            }
            else{
                tvWarningOld.setText("");
            }
        }
    }
    public void clickModify(View v){
        Log.d("click", "modify clicked");

        boolean sign1 = true;
        boolean sign2 = true;
        boolean sign3 = true;
        boolean sign4 = etNewPasswordContent.getText().toString().equals(etConfirmPasswordContent.getText().toString());
        if(etOldPasswordContent.getText().length() < 1){
            sign1 = false;
            tvWarningOld.setText("请输入原始密码！");
        }
        if(etNewPasswordContent.getText().length() <1){
            sign2 = false;
            tvWarningNew.setText("请输入新密码！");
        }
        if(etConfirmPasswordContent.getText().length() <1){
            sign3 = false;
            tvWarningConfirm.setText("请确认密码！");
        }
        if(sign1 && sign2 && sign3 && sign4){
            new setPassword().execute();
        }
    }

    class setPassword extends AsyncTask<Integer, Integer, String>{
        private String userId;
        private String oldPassword;
        private String newPassword;
        @Override
        protected void onPreExecute(){
            btnModify.setClickable(false);
            SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
            String username = sp.getString("username", "");
            userId = sp.getString("userId", "");
            userId = "2";
            oldPassword = etOldPasswordContent.getText().toString();
            newPassword = etNewPasswordContent.getText().toString();
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
