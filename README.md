android-example
===============

Overview
--------
This is an example project that uses the 500px api to populate an animated list view with the latest frameworks.

Technologies
------------

[Logback](http://tony19.github.io/logback-android/)

[JodaTime](http://www.joda.org/joda-time/)

[Google Guava](https://code.google.com/p/guava-libraries/)

[Google GSON](https://code.google.com/p/google-gson/)

[OAUTH SignPost](https://code.google.com/p/oauth-signpost/)

[Netflix RxJava](https://github.com/Netflix/RxJava)

[Square](http://square.github.io/otto/) - Otto EventBus - Similar to Guava EventBus

[Square OkHttp](http://square.github.io/okhttp/) - A better Http stack

[Square Picasso](http://square.github.io/picasso/) - Image Cache and Loader

[Square Retrofit](http://square.github.io/retrofit/) - REST Client (JAX-RS like)

[Robospice](https://github.com/stephanenicolas/robospice) - A Generic Service Management framework

[ButterKnife](http://jakewharton.github.io/butterknife/)  - View injection

[Dagger](http://square.github.io/dagger/) - Compile-time dependency injection

[NineOldAndroids](http://nineoldandroids.com/) - Backport of Android 3.0 animation

[ListViewAnimations](https://github.com/nhaarman/ListViewAnimations/wiki) - ListView animations using NineOldAndroids

[SVG-Android](https://github.com/japgolly/svg-android) - Forked branch of the unmaitained SVG-Android

IDE
---
* The recommended IDE is [Android Studio](http://developer.android.com/sdk/installing/studio.html).
* Always update Android Studio to the latest version before importing the project.

Android Studio
--------------
* Import into Android Studio.
* Allow it to fix any plugin and environmental settings (gradle and sdk).
* Tools -> Android -> SDK Manager to resolve any dependencies it cannot resolve on it's own.  The bottom strip of the IDE will tell you what you are missing.

500px Test console
------------------
* [Test Console](https://apigee.com/vova/embed/console/api500px)
* See secrets.properties to change it to your own account, it is set to a general mailinator test account.

Build Types
-----------
* Debug
* Release

Command line build
------------------
* ./gradlew clean assemble<BuildType>

e.g.
./gradlew clean assembleDebug

Command line install
--------------------
*./gradlew install<BuildType>

e.g.  (Unplug/Re-plug device if it cannot see the device)
./gradlew installDebug



