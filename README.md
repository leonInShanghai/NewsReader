# NewsReader
波波新闻-仿网易新闻

#使用OK HTTP 框架网络请求 自定义SkipView 广告页面常用 有需要的伙伴直接 import Module  skipview
使用了第三方开源框架FlycoTabLayout ：https://github.com/H07000223/FlycoTabLayout














//********************************↓ 下面是知识点与本项目的使用无关↓******************************************
#用到的知识点 数据本地持久化保存，主线程发消息给子线程。
#依赖三方库的方式：
1.依赖一个文件（jar/aar） aar文件的区别：除了包含jar文件外，还包含了一些资源文件。
    //aar文件在第一次依赖时，需要进行一些配置
        //首先在project外层build.gradle文件中添加如下代码：

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


2.依赖一个module（一个Library形式的module）
3.通过远程仓库进行依赖（缺点它的代码无法更改 因为别人通常是打包好上传到远程仓库的打包好的文件代码你怎么修改呢）
比如：implementation  'com.flyco.tablayout:FlycoTabLayout_Lib:2.1.2@aar'