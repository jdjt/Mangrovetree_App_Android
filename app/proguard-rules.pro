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
-keepattributes Signature
-keepattributes *Annotation*
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


-keep class **$DatabaeField
-keep class **$DatabaseTable
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

-dontwarn  com.jdjt.mangrovetreelibray.**
-keep class com.jdjt.mangrovetreelibray.** { *;}
#-keep class com.jdjt.mangrovetreelibray.ioc.** { *; }
#-keep class com.jdjt.mangrovetreelibray.utils.** { *; }
#-keep class com.jdjt.mangrovetreelibray.views.** { *; }
-keep class  com.jdjt.mangrove.http.** {*;}
#-keepnames class com.jdjt.mangrove$* {
#    public <fields>;
#    public <methods>;
#}
#-keepclassmembers class * extends android.app.Application {
#    public void *();
#}
-keepnames class com.jdjt.mangrovetreelibray.ioc.net$IocHttp {
    public <fields>;
    public <methods>;
}
#-keep class **$$InView{*;}

# # -------------------------------------------
# #  ######## 内部类混淆配置  ##########
# # -------------------------------------------
-keepclassmembers class com.jdjt.mangrove.application.MangrovetreeApplication$*{*;}

-keep class com.jdjt.mangrove.fragment.SearchFragment$*{
        public <fields>;
        public <methods>;
}