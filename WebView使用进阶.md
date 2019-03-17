# WebView使用进阶

## WebView

android中提供的控件，它能够加载网页和html的数据。使用时注意配置网络使用权限。常用方法有：

- loadUrl(String url)加载一个网页或加载一段js。

  - 加载远程网页：使用`webView.loadUrl("http://qq.com")`
  - 也可以是本地的网页，本地assets文件夹中的网页加载通过使用`webView.loadUrl("file:///android_asset/xxx.html")`来加载。
  - 加载js：比如调用一个名为changeImage的js方法`webView.loadUrl("javascript:changeImage()");`

- loadData(String data, String mimeType, String encoding)

- loadDataWithBaseURL(String baseUrl, String data,String mimeType, String encoding, String historyUrl)
  加载一段html代码，比如：

  ```java
      //loadData 加载String格式的html内容时,里面的特殊字符(比如#)需要转码
      mWebView.loadDataWithBaseURL(null, "<head><body>aaa</body></head>","text/html", "utf-8", null);
  ```

- reload()：刷新当前页面

- getTitle()：获得网页的标题

- canGoBack：能否后退返回到先前的上一级页面

- goBack：后退返回到先前的上一级页面

- addJavascriptInterface(Object object, String name)：增加一个对象作为桥梁来让java代码与js交互

## WebViewClient

WebViewClient是WebView的一个辅助类，经常使用它来监听webView加载网页的状态。

常用方法如下：

* onLoadResource：加载网页中的各种资源（比如：图片、css）时的回调
* onPageStarted：网页开始加载时的回调
* onPageFinished：网页结束加载时的回调
* onReceiverdError：网页记载失败时的回调
* shouldOverrideUrlLoading(WebView view, String url)：处理网页内部的跳转，比如请求www.bbbb.com，网页会请求到其他的页面，此时通过该方法就能进行处理。
* shouldInterceptRequest(WebView view, String url)：拦截网页中的请求。

## WebChromeClient

WebChromeClient是WebView的另一个辅助类，常用方法有：

* onJsAlert(WebView view, String url, String message, JsResult result)：接收网页中的alert事件
* onJsConfirm(WebView view, String url, String message, JsResult result)：接收网页中的confirm事件
* onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result)：接收网页中的prompt事件
* onReceivedIcon：接收网页的图标
* onReceivedTitle：接收网页的标题
* onProgressChanged(WebView view, int newProgress)：加载网页的进度发生变化时的回调

## WebViewSettings

webView的设置类，能够去设置webView的各种详细参数

* setAllowFileAccess(boolean allow)：设置是否使用file协议来访问网页，默认是可以访问。常用于访问assets和raw文件夹下面的网页

* setBuiltInZoomControls(boolean enabled)：设置是否使用webView自带的缩放功能

* setDefaultFontSize(int size)：设置页面的文字大小

* setJavaScriptEnabled(boolean flag)：设置是否执行页面中的js方法

* setCacheMode( int mode)：是否使用缓存加载页面。可以配置LOAD_DEFAULT（只有缓存不存在或者已经过期时才会从网络加载）, LOAD_CACHE_ELSE_NETWORK（不论缓存是否过期，只有缓存不存在时才会从网络加载）, LOAD_NO_CACHE（不用缓存一直走网络加载）, LOAD_CACHE_ONLY（只加载缓存不走网络）。这些缓存模式


## 补充

在Android4.2版本（API 17）以下的手机中有一个问题：webView会被javaScript注入，造成手机出现信息安全的问题，比如javaScript中操作手机去下载木马软件，发送付费短信等等。这个问题的关键就出在addJavascriptInterface方法。javaScript通过调用这个接口可以直接操作系统的java方法。

google在4.2版本中解决了这个问题，方案是在被js调用的方法上加一个声明`@JavascriptInterface`，但是4.2版本一下的手机就无法应用这个方案了。