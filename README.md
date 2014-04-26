android-example
===============

Overview
--------
This is an example project that uses the 500px api to populate an animated list view with the latest frameworks.

Technologies
------------

The Necessary
-------------
[Logback](http://tony19.github.io/logback-android/)

[JodaTime](http://www.joda.org/joda-time/)

[Google Guava](https://code.google.com/p/guava-libraries/)

[Google GSON](https://code.google.com/p/google-gson/)

[JacksonJSON](http://wiki.fasterxml.com/JacksonHome)

[OAUTH SignPost - Wrapper for OAUTH signing](https://code.google.com/p/oauth-signpost/)

[Square OkHttp - A smarter, better HTTP stack](http://square.github.io/okhttp/)

The Fun
-------
[LMAX-Disruptor - High Performance Inter-thread Messaging Library](https://github.com/LMAX-Exchange/disruptor)

[Netflix RxJava - Reactive Java for Observables](https://github.com/Netflix/RxJava)

[Square Otto EventBus - Similar to Guava EventBus](http://square.github.io/otto/)

[Square Picasso - Image Cache and Loader](http://square.github.io/picasso/)

[Square Retrofit - REST Client (JAX-RS like)](http://square.github.io/retrofit/)

[Robospice - A Generic Service Management framework](https://github.com/stephanenicolas/robospice)

[ButterKnife - View injection](http://jakewharton.github.io/butterknife/)

[Dagger - Compile-time dependency injection](http://square.github.io/dagger/)

The Eye Candy
-------------
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
* See /secrets.properties.sample to change it to your own account.
* Manually copy /secrets.properties.sample to /src/main/java/profiles/dev/resources/secrets.properties and /src/main/java/profiles/release/resources/secrets.properties
* This application will purposefully "Force Quit" if you don't have the file.

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

License
-------

    Copyright 2014 Frederick C. Siu

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
