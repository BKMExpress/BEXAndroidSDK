# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/bsoykal/Library/Android/sdk/tools/proguard/proguard-android.txt
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

# ------------------------------------------
# BKM
# ------------------------------------------
-keep class com.bkm.** { *; }

# ------------------------------------------
# GSON
# ------------------------------------------

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keepattributes *Annotation*
-keep class com.google.gson.stream.** { *; }


# ------------------------------------------
# RETROFIT
# ------------------------------------------
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

# ------------------------------------------
# OKHTTP
# ------------------------------------------
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn okio.**

-keepattributes Signature
-keepattributes Annotation
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**



-keepclassmembers class * implements javax.net.ssl.SSLSocketFactory {
    private javax.net.ssl.SSLSocketFactory delegate;
}

# ------------------------------------------
# PARCELER
# ------------------------------------------
-keep interface org.parceler.Parcel
#-keep @org.parceler.Parcel class * { *; }
#-keep class **$$Parcelable { *; }
#-keep class org.parceler.Parceler$$Parcels

# ------------------------------------------
# SUPPORT DESIGN
# ------------------------------------------
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

# ------------------------------------------
# SUPPORT V7 APPCOMPAT
# ------------------------------------------
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }

-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}

# ------------------------------------------
# SUPPORT V7 CARDVIEW
# ------------------------------------------
-keep class android.support.v7.widget.RoundRectDrawable { *; }
