# UrQA-Client-Android history
- UrQA android client sdk which is changed project for Eclipse into Android Studio
- SDK only checks the READ_LOGS permission to capture the log only below jellybean version

# Android Client SDK
This is the client sdk for android application to report the crash to the UrQA server when the runtime exceptions or errors are occurred

# To build & release the android client sdk for java
1. set the library version inside the below files
 - update the variable of "SDKVersion" in "UrQA-Client-Android/app/src/main/java/com/urqa/common/StateData.java"<br>
  <img src="readmeImages/sdkversion.png" alt="SDK Version" width="300" height="150"/>
 - update the jar library name at the task "exportJar" in the "UrQA-Client-Android/app/build.gradle"<br>
  <img src="readmeImages/sdklibraryname.png" alt="SDK name" width="300" height="150"/>
2. click Gradle on the right side of the IDE window, then The Gradle tasks panel appears<br>
  <img src="readmeImages/gradletaskspanel.png" alt="SDK name" width="200" height="150"/>
3. double-click the "UrQA-Profile-Client-Android/app/Tasks/other/exportJar" task<br>
  <img src="readmeImages/exportJar.png" alt="SDK name" width="200" height="150"/>
4. the build generates an jar library file named "file name.jar" in the "UrQA-Profiler-Client-Android/app/release” directory<br>
  <img src="readmeImages/sdklibrary.png" alt="SDK name" width="200" height="150"/>
5. release the jar file<br>

# To build & release the android client sdk for ndk
1. define the APP_STL as "gnustl_static" in the "UrQA-Profiler-Client-Android/app/src/main/jni/Application.mk"
2. click Gradle on the right side of the IDE window, then The Gradle tasks panel appears
3. double-click the "UrQA-Client-Android/app/Tasks/other/ndkBuild" task
4. the build generates two static library files below in the directory of "UrQA-Client-Android/app/src/main/obj/local"
 - armeabi/liburqanative.a
 - armeabi-v7a/liburqanative.a
5. rename two static library files as below
 - armeabi/liburqanative_gnustl.a
 - armeabi-v7a/liburqanative_gnustl.a
6. define the APP_STL as "stlport_static" in the "UrQA-Client-Android/app/src/main/jni/Application.mk"<br>
  <img src="readmeImages/application_mk.png" alt="SDK name" width="200" height="150"/>
7. click Gradle on the right side of the IDE window, then The Gradle tasks panel appears
8. double-click the "UrQA-Client-Android/app/Tasks/other/ndkBuild" task
9. the build generates two static library files below in the directory of "UrQA-Client-Android/app/src/main/obj/local”
 - armeabi/liburqanative.a
 - armeabi-v7a/liburqanative.a<br>
   <img src="readmeImages/staticlibrary.png" alt="SDK name" width="200" height="350"/>
10. copy the directory of “UrQA-Client-Android/app/src/main/jni/header”
11. delete *.cc files in the directory and and all levels of subdirectories
12. create a zip archive of the below directory
  - armeabi
  - armeabi-v7a
  - header<br>
   <img src="readmeImages/tree.png" alt="SDK name" width="200" height="400"/>&nbsp;&nbsp;
   <img src="readmeImages/tree2.png" alt="SDK name" width="200" height="400"/>&nbsp;&nbsp;
   <img src="readmeImages/tree3.png" alt="SDK name" width="200" height="400"/>&nbsp;&nbsp;
   <img src="readmeImages/tree4.png" alt="SDK name" width="200" height="400"/>&nbsp;&nbsp;
   <img src="readmeImages/tree5.png" alt="SDK name" width="200" height="400"/>&nbsp;&nbsp;
13. release the zip file<br>

# How to use the function

1. join the site of 'http://ur-qa.com/urqa'

2. create a project by clicking the '+' button on the right side

3. download the lastest version of the UrQA client library
 - https://github.com/UrQA/UrQA-Profiler-Client-Android/tree/master/app/release 

4. import the UrQA client library
    1. copy the library file in the libs folder. Make sure you are in the Project view mode (Top left corver of the Project window)
    2. Right click on the UrQA client library file and select the last option "Add as library" on the pop up window

5. Add the “Internet” and "READ_LOGS" permission to your manifest file to capture and upload the crash log data file to the server
    -  <uses-permission android:name="android.permission.INTERNET" />
    -  <uses-permission android:name="android.permission.READ_LOGS" />

6. write the code
    1. import com.urqa.clientinterface.URQAController
    2. initialize the function <br>
       URQAController.InitializeAndStartSession(getApplicationContext(),/* APIKey */);
       you can get the "APIKey" when you create a project at the step 2


