package com.bobo520.buildertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by 求知自学网 on 2019/3/3 Copyright © Leon. All rights reserved.
 * Functions: 建造者模式举例
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GirlFriend girlFriend = new GirlFriend.Builder().setAge("100")
                .setWeight("99")
                .build();

        Log.e("建造者模式：",girlFriend.toString());

        //log输出如下：
        //GirlFriend{name='大乔', age='100', height='175', weight='99', sanwei='80 60 90', isBeauty=true}
    }
}
