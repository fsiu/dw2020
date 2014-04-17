android-example
===============

Overview
--------
This is an example project that uses the 500px api to populate an animated list view with the latest frameworks.

IDE
---
* The recommended IDE is Android Studio available the following [location](http://developer.android.com/sdk/installing/studio.html)
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
* ./gradlew clean assemble<BuildType>

e.g.
./gradlew clean assembleDebug

Command line install
*./gradlew install<BuildType>

e.g.  (Unplug/Re-plug device if it cannot see the device)
./gradlew installDebug



