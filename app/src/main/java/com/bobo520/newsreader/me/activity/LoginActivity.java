package com.bobo520.newsreader.me.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bobo520.newsreader.R;
import com.bobo520.newsreader.util.LELog;
import com.bobo520.newsreader.util.LETrtStBarUtil;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Leon on 2019/4/18 Copyright  Leon. All rights reserved.
 * Functions:  AppCompatActivity
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /**注册的入口这里先不做*/
    private TextView tvRegisterFront;

    /**手机号（账号）输入框*/
    private EditText edtLoginId;

    /**密码输入框*/
    private EditText edtPassword;

    /**用户输入账号或密码错误的提示*/
    private TextView tvPasswordError;

    /**登录按钮*/
    private Button btnLogin;


    /**
     * 初始化UI控件
     */
    private void findViews() {
        tvRegisterFront = (TextView)findViewById( R.id.tv_register_front );
        edtLoginId = (EditText)findViewById( R.id.edt_login_id );
        edtPassword = (EditText)findViewById( R.id.edt_password );
        tvPasswordError = (TextView)findViewById( R.id.tv_password_error );
        btnLogin = (Button)findViewById( R.id.btn_login );

        btnLogin.setOnClickListener( this );
    }

    /**
     * 点击事件的处理
     */
    @Override
    public void onClick(View v) {
        if ( v == btnLogin ) {

            String loginId = edtLoginId.getText().toString();
            String password = edtPassword.getText().toString();
            if (loginId != null && loginId.length() > 0) {
            } else {
                Toast.makeText(LoginActivity.this,"请输入手机号码",Toast.LENGTH_SHORT).show();
                return;
            }

            if (password != null && password.length() > 0) {
            } else {
                Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                return;
            }

            /**登录网络请求*/
            login(loginId, password);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //设置沉浸式导航栏
        LETrtStBarUtil.setTransparentToolbar(this);

        findViews();
    }

    //登陆的业务逻辑处理
    private void login(String mobile, String password) {

        //TODO: 将POST请求封装到 http 工具类中

        HashMap<String, Object> params = new HashMap<>();
        //MD5Util mdt = new MD5Util();
        params.put("loginId", mobile);
        // params.put("password", mdt.MD5Encode(password, false));

        Interceptor tokenInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJxbXgxNjMiLCJzdWIiOiIzYmVmZTY3OTEyMjM0MTc3" +
                        "YWFiZjUzZDFhZWY0YTZmNTQ4N2M4MWI5NGNlNzhkODQ2OTAxY2ExYjciLCJpYXQiOjE1NTU2NjkwOTAsImV4cCI6MTU" +
                        "1NjI3Mzg5MH0.RsupR9QW40kVE9uFAiGYZJqDZZEuPefcKzG4rtvcQ08";
                String accessToken = "173dbe94010f4d4290fb32ee3fd81c8b";
                String deviceId = "ca2a11f4d18fa0723b9a7a80d6e972cb";
                String language = "zh";
                String lan = "0";

                request = request.newBuilder()
                        .header("access-token", accessToken)
                        .header("token", token)
                        .header("deviceId", deviceId)
                        .header("language", lan)
                        .build();
                return chain.proceed(request);
            }
        };

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .build();


        RequestBody requestBody = new FormBody.Builder()
                .add("loginId",mobile)
                .add("password","13742771cb45adc99fa18991a2518402")
                .build();

        // 构造Request->call->执行
        final Request request = new Request.Builder()
                .url("http://47.99.135.241:8086/chengfang-api/api/login")
                .post(requestBody)//参数放在body体里
                .build();
        Call call = httpClient.newCall(request);

        //这里用的异步加载
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call,final IOException e) {

                //让onFailure在主线程中执行避免异常
                LELog.showLogWithLineNum(5,"响应未成功");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求不成功的处理
                if (!response.isSuccessful()){
                    LELog.showLogWithLineNum(5,"响应未成功"+response.toString());
                    return;
                }
                //后续的业务需要交给不同的页面来实现-自定义接口来实现
                final String string = response.body().string();
                //让onSuccess方法直接运行在主线程
                LELog.showLogWithLineNum(5,"66666-------------------------"+string);
            }
        });



    }


}
