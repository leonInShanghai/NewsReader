package com.bobo520.eventbusdome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

public class TwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
    }

    public void click(View view) {
        //发一个事件出去
        DidiEvent didiEvent = new DidiEvent();
        didiEvent.source = "magret?=daldnadlenndkl";
        didiEvent.title = "一段不可告人的视频";
        EventBus.getDefault().post(didiEvent);
    }
}
