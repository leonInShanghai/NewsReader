package com.bobo520.eventbusdome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * Created by 求知自学网 on 2019/4/4 Copyright © Leon. All rights reserved.
 *  EventBus : 能够简化各组件间的通信，让我们的代码书写变得简单，
 * 能有效的分离事件发送方和接收方(也就是解耦的意思)，能避免复杂和容易出错的依赖性和生命周期问题。
 * EventBus : 事件总线
 * EventBus 使用举例
 */
public class MainActivity extends AppCompatActivity {

    // otto eventBus 发一个全局的事件（类似于广播） 关心事件的对象就可以接收到了，接收之后可以处理一些业务逻辑

    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn = (Button)findViewById(R.id.btn);

        //监听和关心事件的发送,需要先进行注册（有点类似于广播）。
        EventBus.getDefault().register(this);
    }

    public void click(View view){
        Intent intent = new Intent(MainActivity.this,TwoActivity.class);
        startActivity(intent);
    }

    /**
     * 去关心TwoActivity中发出来的事件
     * 2.4 版本的时候，接收事件的方法必须是onEvent或者onEventMainThread或者onEventBackgroundThread
     * 3.0 版本不在局限方法的名称 但是要添加注解@Subscribe
     */
    @Subscribe
    public void threeEvent(DidiEvent didiEvent){
        String title = didiEvent.title;
        mBtn.setText(title);
    }

    @Override
    protected void onDestroy() {
        //销毁EventBus 合理管理内存
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
