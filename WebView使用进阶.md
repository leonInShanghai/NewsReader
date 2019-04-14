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


//----------------------------------↓ 下面是知识点与本项目的使用无关↓-----------------------------------</br>
#用到的知识点 数据本地持久化保存，主线程发消息给子线程。</br>
#依赖三方库的方式：</br>
1.依赖一个文件（jar/aar） aar文件的区别：除了包含jar文件外，还包含了一些资源文件。</br>
    //aar文件在第一次依赖时，需要进行一些配置 首先在project外层build.gradle文件中添加如下代码：

        allprojects {
            repositories {
                jcenter()
                //第一次引入aar文件时,需要配置libs文件夹作为本地的代码仓库
                flatDir {
                    dirs 'libs'
                }
            }
        }

        //然后，将aar文件复制到对应的module的libs文件夹里，然后再module的build.gradle文件当中
        //添加一下依赖：
        //依赖某个aar文件 格式:compile(name:'这个aar文件的文件名(不含后缀)', ext:'aar')
        dependencies {
            //...
            //依赖某个aar文件 格式implementation(name:'这个aar文件的文件名(不含后缀)', ext:'aar')
            implementation(name:'FlycoTabLayout_Lib-debug', ext:'aar')
            // ...
        }


2.依赖一个module（一个Library形式的module）</br>
3.通过远程仓库进行依赖（缺点它的代码无法更改 因为别人通常是打包好上传到远程仓库的打包好的文件代码你怎么修改呢）
比如：implementation  'com.flyco.tablayout:FlycoTabLayout_Lib:2.1.2@aar'</br>
加载图片的几种方法：</br>
1.Glide 2.自己写 3.UIL（老牌的图片加载三方库）</br>

-------------------------------------------------------------------------------------------------------------</br>
1.下拉刷新控件PtrFragmeLayout的触摸逻辑</br>
   有两个方法：</br>
        dispatchTouchEventSupper 默认的super分发方法，该方法会正常的将触摸事件发给子控件</br>
        dispatchTouchEvent 系统的分发方发，内部有自定义的业务逻辑</br>
            自定义的业务逻辑：当能够响应下拉刷新的手势时，就会直接去下拉刷新并return true，</br>
                              触摸事件是不会分发给内部子控件的</br>
                              当不响应下拉刷新时，就调用dispatchTouchEventSuper方法，正常的将触摸事件发下去</br>

2.复写这个控件（PtrFragmeLayout）的分发方法dispatchTouchEvent：</br>
    当手势的X方向偏移大于Y方向的偏移时，说明不是下拉刷新的手势，
    将事件正常的分发下去（return dispatchTouchEventSupper）;</br>

-------------------------------------------------------------------------------------------------------------</br>
三方库SwipeBackActivity 划动关闭（Activity）页面的工作原理:</br>
每个activity都有一个window窗体，窗体当中都会有一个根布局，我们平时写的setContentView方法就是将xml对应的view</br>
添加到整个根布局中。</br>
--------------------------------------------------------------------------------------------------------------</br>
```java
 /**
     *  解决错误
     * All com.android.support libraries must use the exact same version specification
     * (mixing versions can lead to runtime crashes). Found versions 28.0.0-alpha1, 28.0.0. Examples
     * include com.android.support:recyclerview-v7:28.0.0-alpha1 and com.android.support:animated-vector-drawable:28.0.0
     * less... (Ctrl+F1)
     * There are some combinations of libraries, or tools and libraries, that are incompatible, or can lead to bugs.
     * One such incompatibility is compiling with a version of the Android support libraries that is not the latest version
     * (or in particular, a version lower than your targetSdkVersion).  Issue id: GradleCompatible
     */
    //强制让所有模块都用相同的支持库版本
    configurations.all {
        resolutionStrategy.eachDependency { DependencyResolveDetails details ->
            def requested = details.requested
            if (requested.group == 'com.android.support') {
                if (!requested.name.startsWith("multidex")) {
                    details.useVersion '28.0.0'
                }
            }
        }
    }
```