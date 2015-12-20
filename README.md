<b>nRF_test</b>
***
A Simple BLE Scanner application built in for Arduino boards (i.e. <a href="https://www.arduino.cc/en/ArduinoCertified/IntelGalileoGen2">Intel Galileo Generation 2</a> and others) using <a href="https://github.com/NordicSemiconductor/nRF-Master-Control-Panel">nRF master control panel</a> and tested using <a href="https://en.wikipedia.org/wiki/Android_(operating_system)">Android</a>.
***
***
How it works? <br>
<br>
Make sure to keep arduino stuffed-up ready. <br>
Connect to mobile device. <br>
Start the application and Start Scanning. <br>
Stop scanning if it's done.
***
```
Requirements for using the app:

Android phone (minimum 4.3)
Arduino board, breadboard, wires
```
```
Requirements for building from source:

JDK 1.8 (Java 8)
Android Studio IDE 1.5
Gradle build
Minimum API Level: 18
Android Phone (Version 4.3 or 4.4 or 5 or 5.1 or 6)
Arduino board: Intel Galileo Generation 2
Arduino IDE 1.6.5

nRF master control panel dependency used:
Android Scanner Compat Library
```
``` java
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    testCompile 'junit:junit:4.12'
    compile 'com.android.support:appcompat-v7:23.0.1'
    compile 'com.google.android.gms:play-services:8.1.0'
    compile 'com.android.support:design:23.0.1'
}
```