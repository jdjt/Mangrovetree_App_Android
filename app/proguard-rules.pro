# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /tools/java/android_studio/android-sdk-macosx/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


-keep public class * extends com.sanders.db.IDColumn

# http client
-keep class org.apache.http.** {*; }
#gson
#如果用用到Gson解析包的，直接添加下面这几行就能成功混淆，不然会报错。
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.** { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
# 如果使用了Gson之类的工具要使被它解析的JavaBean类即实体类不被混淆。
-keep class com.matrix.app.entity.json.** { *; }
-keep class com.matrix.appsdk.network.model.** { *; }
#mangrovetreelibray
#######
# 其它第三方库
#-libraryjars libs/fengmap-2.0.60.jar

#universalimageloader图片加载框架不混淆
-keep class com.nostra13.universalimageloader.** { *; }
-dontwarn com.nostra13.universalimageloader.**



# OrmLite uses reflection
-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }
-keepclassmembers class com.package.bo.** { *; }
-keepclassmembers class * {
@com.j256.ormlite.field.DatabaseField *;
}
#-keepclassmembers class * {
#    @com.j256.ormlite.field.DatabaseField*;
##    @com.j256.ormlite.table.DatabaseTable*;
#}

#OrmLite uses reflection
#第三方Tab
-keep class com.flyco.tablayout.** {
   *;
}
#######
-keep class java.lang.** {*;}
-keep class java..** {*;}
-keep class org.** {*;}
-keep class  java.util.** {*;}
#--------------------------------------------#
#Warning:library class android.webkit.WebView depends on program class android.net.http.SslCertificate

-dontwarn android.net.http.**
-keep class android.net.http.** { *;}
#Warning:library class org.apache.http.conn.ssl.SSLSocketFactory depends on program class org.apache.http.conn.scheme.HostNameResolver
-dontwarn org.apache.http.**
-keep class org.apache.http.** { *;}

#-dontwarn  com.jdjt.mangrovetreelibray.**
-keep class com.android.dx.** { *;}
-keep interface com.android.dx.** { *;}
-keep class com.google.dexmaker.** { *;}
-keepnames class com.google.dexmaker$* {
    public <fields>;
    public <methods>;
}
-keepnames class com.android.dx$* {
    public <fields>;
    public <methods>;
}
#-keep class com.jdjt.mangrovetreelibray.ioc.** { *; }
#-keep class com.jdjt.mangrovetreelibray.utils.** { *; }
#-keep class com.jdjt.mangrovetreelibray.views.** { *; }
#-keep class  com.jdjt.mangrove.http.** {*;}
#-keepnames class com.jdjt.mangrove$* {
#    public <fields>;
#    public <methods>;
#}
#-keepclassmembers class * extends android.app.Application {
#    public void *();
#}
#-keepclassmembers class com.jdjt.mangrovetreelibray.ioc.net$IocHttp {
#    public <fields>;
#    public <methods>;
#}
#-keepclassmembers class com.jdjt.mangrovetreelibray.ioc.net.** {
#    public <fields>;
#    public <methods>;
#}

#-keep class **$$InView{*;}

# # -------------------------------------------
# #  ######## 内部类混淆配置  ##########
# # -------------------------------------------
-keepclassmembers class com.jdjt.mangrove.application.MangrovetreeApplication$*{*;}

-keep class com.jdjt.mangrove.fragment.SearchFragment$*{
        public <fields>;
        public <methods>;
        private <fields>;
        private <methods>;
}

-keep @com.jdjt.mangrovetreelibray.ioc.annotation.NotProguard class * {*;}
-keep class * {
    @com.jdjt.mangrovetreelibray.ioc.annotation.NotProguard <fields>;
}
-keepclassmembers class * {
    @com.jdjt.mangrovetreelibray.ioc.annotation.NotProguard <methods>;
    public static <fields>;
    public static class *;
}

-keepclasseswithmembernames class com.jdjt.mangrovetreelibray.ioc.net.** {
*;
}

-keep @com.jdjt.mangrovetreelibray.ioc.annotation.Ignore class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InAfter class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InAll class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InBack class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InBean class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InBefore class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InBinder class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InDestroy class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InForm class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InGet class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.Init class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InLayer class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InListener class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InModule class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InNet class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InNewIntent class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InHttp class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InParam class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InPause class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InPLayer class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InPullRefresh class * {*;}
-keep @com.jdjt.mangrovetreelibray.ioc.annotation.InView class * {*;}

-keep class * {
@com.jdjt.mangrovetreelibray.ioc.annotation.NotProguard <fields>;
@com.jdjt.mangrovetreelibray.ioc.annotation.Ignore <fields>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InBean <fields>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InModule <fields>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InSource <fields>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InVa <fields>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InView <fields>;

}
-keepclassmembers class * {
@com.jdjt.mangrovetreelibray.ioc.annotation.NotProguard <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.Ignore <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InPost <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InGet <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.Init <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InAfter <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InBack <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InBefore <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InBinder <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InDestroy <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InForm <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InListener <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InNet <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InNewIntent <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InHttp <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InPause <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InPullRefresh <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InPost <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InRestart <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InResume <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InStart <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InStop <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InUI <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InVaER <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InVaOK <methods>;
@com.jdjt.mangrovetreelibray.ioc.annotation.InWeb <methods>;

}

#不混淆android-async-http(这里的与你用的httpClient框架决定)
-keep class com.loopj.android.http.**{*;}

 #不混淆org.apache.http.legacy.jar
 -dontwarn android.net.compatibility.**
 -dontwarn android.net.http.**
 -dontwarn com.android.internal.http.multipart.**
 -dontwarn org.apache.commons.**
 -dontwarn org.apache.http.**
 -keep class android.net.compatibility.**{*;}
 -keep class android.net.http.**{*;}
 -keep class com.android.internal.http.multipart.**{*;}
 -keep class org.apache.commons.**{*;}
 -keep class org.apache.http.**{*;}

#-keep class com.jeremyfeinstein.slidingmenu.lib.** { *; }
#-keep interface com.jeremyfeinstein.slidingmenu.lib.** { *; }
# 地图模块
-keep class com.fengmap.** { *; }
-keep class com.fengmap.android.**{*;}
#-keepclassmembers class com.fengmap.drpeng.** {
#     *;
#}
