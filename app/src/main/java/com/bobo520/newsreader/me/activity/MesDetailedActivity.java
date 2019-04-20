package com.bobo520.newsreader.me.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.util.LETrtStBarUtil;
import static com.bobo520.newsreader.me.activity.MessageActivity.JPUSH_MESSGAE;

public class MesDetailedActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView bodyMessage;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_msg);

        //设置沉浸式导航栏
        LETrtStBarUtil.setTransparentToolbar(this);

        bodyMessage = (TextView)findViewById(R.id.only_message);
        backButton = (ImageButton)findViewById(R.id.details_back_button);
        backButton.setOnClickListener(this);

        if (getIntent() != null) {
            String jpushMessage = getIntent().getStringExtra(JPUSH_MESSGAE);
            bodyMessage.setText(jpushMessage);
        }

    }

    @Override
    public void onClick(View v) {
        if (v == backButton){
            finish();
        }
    }
}
