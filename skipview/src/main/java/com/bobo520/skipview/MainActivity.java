package com.bobo520.skipview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Leon on 2019/1/6 Copyright © Leon. All rights reserved.
 * Functions: 自定义控件SkipView 即 splash 广告页面 跳过视图。
 */
public class MainActivity extends AppCompatActivity {

    private SkipView mSkipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSkipView = (SkipView)findViewById(R.id.skipView);

        mSkipView.setmOnSkipListener(new SkipView.OnSkipListener() {
            @Override
            public void onSkip() {
                startActivity(new Intent(MainActivity.this,TwoActivity.class));
            }
        });
    }


    public void click(View view) {
        mSkipView.start();
    }
}
