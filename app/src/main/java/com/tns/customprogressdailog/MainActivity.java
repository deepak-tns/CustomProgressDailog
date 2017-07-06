package com.tns.customprogressdailog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dailog);
        ProgressBar bar = (ProgressBar) findViewById(R.id.progressdialog);
        bar.setVisibility(View.VISIBLE);
    }
}
