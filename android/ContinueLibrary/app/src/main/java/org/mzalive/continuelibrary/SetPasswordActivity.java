package org.mzalive.continuelibrary;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextWatcher;
import android.widget.Toast;

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

    private String   userId;
    private String   oldPassword;
    private String   newPassword;

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
        userId = sp.getString("userId", "");
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
            boolean sign1 = false;
            boolean sign2 = false;

            if(etOldPasswordContent.getText().length() < 6 && etOldPasswordContent.getText().length() > 0){
                tvWarningOld.setText("请输入6-16位密码，字母区分大小写");
                sign1 = false;
            }
            else{
                tvWarningOld.setText("");
                sign1 = true;
            }
            if(etNewPasswordContent.getText().length() < 6 && etNewPasswordContent.getText().length() > 0 && sign1){
                tvWarningNew.setText("请输入6-16位密码，字母区分大小写");
                sign2 = false;
            }
            else{
                tvWarningNew.setText("");
                sign2 = true;
            }
            if((!etConfirmPasswordContent.getText().equals(etNewPasswordContent.getText())) && sign1 && sign2){
                tvWarningConfirm.setText("两次输入的密码不一致，请重新输入");
            }
            else{
                tvWarningConfirm.setText("");
            }
        }
    }
    public void clickModify(View v){
        etOldPasswordContent.setText("");
        etNewPasswordContent.setText("");
        etConfirmPasswordContent.setText("");
//        oldPassword = etOldPasswordContent.getText().toString();
//        newPassword = etNewPasswordContent.getText().toString();
//        String confirm = etConfirmPasswordContent.getText().toString();
//        if(oldPassword.equals("") || newPassword.equals("")||confirm.equals("")){
//            Toast.makeText(SetPasswordActivity.this, "密码不能为空，请重试！", Toast.LENGTH_SHORT).show();
//            etOldPasswordContent.clearComposingText();
//            etNewPasswordContent.clearComposingText();
//            etConfirmPasswordContent.clearComposingText();
//        }
//
//        if(!confirm.equals(newPassword)){
//            Toast.makeText(SetPasswordActivity.this, "两次输入密码不匹配，请重试！", Toast.LENGTH_SHORT).show();
//            etOldPasswordContent.clearComposingText();
//            etNewPasswordContent.clearComposingText();
//            etConfirmPasswordContent.clearComposingText();
//        }
    }

}
