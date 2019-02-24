package com.bobo520.newsreader.http;

import java.io.IOException;

/**
 * Created by Leon on 2019/1/20. Copyright © Leon. All rights reserved.
 * Functions: httphelper类中方Callback 回掉接口交给每一个使用它的类来实现
 */
public interface OnResponseListener {

    /**失败的回掉*/
    void onFail(IOException e);

    /**成功的回掉
     * @param response*/
    void onSuccess(String response);

}
