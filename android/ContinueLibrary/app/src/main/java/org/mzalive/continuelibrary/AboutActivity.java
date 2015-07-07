package org.mzalive.continuelibrary;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Trigger on 2015/7/6.
 */
public class AboutActivity extends Activity {
    private ImageView ivLogo;
    private TextView tvVersion;
    private TextView tvCopyright;

    @Override
    public void onCreate(Bundle savedInstanceStatus){
        super.onCreate(savedInstanceStatus);
        setContentView(R.layout.activity_about);
    }
}
