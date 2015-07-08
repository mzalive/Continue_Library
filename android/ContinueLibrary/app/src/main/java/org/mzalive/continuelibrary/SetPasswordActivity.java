package org.mzalive.continuelibrary;

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

/**
 * Created by Trigger on 2015/7/6.
 */
public class SetPasswordActivity extends AppCompatActivity {
    private Toolbar toolbarSP;
    private EditText etOldPasswordContent;
    private EditText etNewPasswordContent;
    private EditText etConfirmPasswordContent;
    private Button   btnModify;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        toolbarSP                = (Toolbar) findViewById(R.id.toolbar_setpw);
        etOldPasswordContent     = (EditText)findViewById(R.id.text_old_password_content);
        etNewPasswordContent     = (EditText)findViewById(R.id.text_new_password_content);
        etConfirmPasswordContent = (EditText)findViewById(R.id.text_confirm_password_content);
        btnModify                = (Button)findViewById(R.id.button_modify);

        toolbarSP.setTitle("");
        setSupportActionBar(toolbarSP);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    class setPassword extends AsyncTask<Integer, Integer, Integer>{
        private String userId;
        private String oldPassword;
        private String newPassword;
        @Override
        protected void onPreExecute(){
            btnModify.setClickable(false);
            btnModify.setText(getString(R.string.modify_status));
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
        protected Integer doInBackground(Integer...params){
            int result = UserInfo.setPassword(oldPassword, newPassword, userId);
            return result;
        }
        @Override
        protected void onPostExecute(Integer result){
            btnModify.setClickable(true);
            btnModify.setText(getString(R.string.modify));
            switch (result){
                case GlobalSettings.JSON_EXCEPTION_ERROR:
                    Toast.makeText(SetPasswordActivity.this, "未知错误，请重试！",Toast.LENGTH_SHORT).show();
                    break;
                case GlobalSettings.RESULT_OK:
                    Toast.makeText(SetPasswordActivity.this, "修改成功！",Toast.LENGTH_SHORT).show();
                    break;
                case GlobalSettings.AUTHORIZATION_ERROR:
                    Toast.makeText(SetPasswordActivity.this, "修改失败！",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
