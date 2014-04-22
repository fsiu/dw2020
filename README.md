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

[OAUTH SignPost - Wrapper for OAUTH signing](https://code.google.com/p/oauth-signpost/)

[LMAX-Disruptor - High Performance Inter-thread Messaging Library](https://github.com/LMAX-Exchange/disruptor)

[Netflix RxJava - Reactive Java for Observables](https://github.com/Netflix/RxJava)

[Square Otto EventBus - Similar to Guava EventBus](http://square.github.io/otto/)

[Square OkHttp - A better HTTP stack](http://square.github.io/okhttp/)

[Square Picasso - Image Cache and Loader](http://square.github.io/picasso/)

[Square Retrofit - REST Client (JAX-RS like)](http://square.github.io/retrofit/)

[Robospice - A Generic Service Management framework](https://github.com/stephanenicolas/robospice)

[ButterKnife - View injection](http://jakewharton.github.io/butterknife/)

[Dagger - Compile-time dependency injection](http://square.github.io/dagger/)

[NineOldAndroids - Back port of Android 3.0 animation](http://nineoldandroids.com/)

[ListViewAnimations - ListView animations using NineOldAndroids](https://github.com/nhaarman/ListViewAnimations/wiki)

[SVG-Android - Forked branch of the unmaintained SVG-Android](https://github.com/japgolly/svg-android)

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
* ./gradlew clean assemble[BuildType]

e.g.
./gradlew clean assembleDebug

Command line install
--------------------
* ./gradlew install[BuildType]

e.g.  (Unplug/Re-plug device if it cannot see the device)
./gradlew installDebug



