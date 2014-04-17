android-example
===============

This is an example project that uses the 500px api to populate an animated list view with the latest frameworks.

This project follows a typical Android structure and can be built with gradle.

The recommended IDE is Android Studio available at http://developer.android.com/sdk/installing/studio.html
Always update Android Studio to the latest version before importing the project.

Android Studio
1. Import into Android Studio.
2. Allow it to fix any plugin and environmental settings (gradle and sdk).
3. Tools -> Android -> SDK Manager to resolve any dependencies it cannot resolve on it's own.  The bottom strip of the IDE will tell you what you are missing.

500px Test console
https://apigee.com/vova/embed/console/api500px

1.  See secrets.properties to change it to your own account, it is set to a general mailinator test account.

Build Types
1.  Debug
2.  Release

Command line build
1.  ./gradlew clean assemble<BuildType>

e.g.
./gradlew clean assembleDebug

Command line install
1.  ./gradlew install<BuildType>

e.g.  (Unplug/Re-plug device if it cannot see the device)
./gradlew installDebug



