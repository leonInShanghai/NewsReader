package com.bobo520.newsreader.me.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.util.Constant;
import com.bobo520.newsreader.util.LETrtStBarUtil;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class HelpCenterActivity extends SwipeBackActivity implements View.OnClickListener {

    private ImageButton helpBack;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);

        //设置沉浸式导航栏
        LETrtStBarUtil.setTransparentToolbar(this);

        helpBack = (ImageButton)findViewById(R.id.help_back_button);
        helpBack.setOnClickListener(this);
        content = (TextView)findViewById(R.id.tv_content);

        content.setText("  "+"波波新闻："+Constant.DESCRIBE);
    }

    @Override
    public void onClick(View v) {
        if (v == helpBack){
            finish();
        }
    }
}
