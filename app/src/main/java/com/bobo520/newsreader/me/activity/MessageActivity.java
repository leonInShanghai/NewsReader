package com.bobo520.newsreader.me.activity;

import android.app.Activity;
import android.os.Bundle;
import com.bobo520.newsreader.R;

import com.bobo520.newsreader.util.LETrtStBarUtil;

public class MessageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        LETrtStBarUtil.setTransparentToolbar(this);
    }
}
