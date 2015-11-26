<b>nRF_test</b>
***
A Simple Blink LED application built in on Arduino board (i.e. <a href="https://www.arduino.cc/en/ArduinoCertified/IntelGalileoGen2">Intel Galileo Generation 2</a>) using <a href="https://github.com/NordicSemiconductor/nRF-Master-Control-Panel">nRF master control panel</a> and tested using <a href="https://en.wikipedia.org/wiki/Android_(operating_system)">Android</a>.
***
```
Requirements:

JDK 1.8 (Java 8)
Android Studio 1.5
Gradle build
Minimum API Level: 18
Android Phone (Version 4.3 or 4.4 or 5 or 5.1 or 6)

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