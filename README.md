# Vuzix Blade TensorFlow Lite Demo
## Overview
This project is a demo to implement [TensorFlow Lite](https://www.tensorflow.org/lite/android/tutorials/object_detection) onto the Vuzix Blade AR glasses.
This project is capable of producing 2 types of results either overlaying the camera preview or overlaying real life vision.

This project is made up of 3 java classes and 1 kotlin class
- [MainActivity.java](app/src/main/java/com/example/vuzixblademachinelearning/MainActivity.java) - Handles switching between the info and 2 types of views
- [CameraXHandler.java](app/src/main/java/com/example/vuzixblademachinelearning/CameraXHandler.java) - Handles the camera api, binding the camera to a preview, binding the image analyzer to a camera,
- [TFLResultsView.java](app/src/main/java/com/example/vuzixblademachinelearning/TFLResultsView.java) - Handles displaying the results on a view either overlaying the camera preview or as a standalone to overlay real life vision
- [TFLiteHandler.kt](app/src/main/kotlin/com/example/vuzixblademachinelearning/TFLiteHandler.kt) - Handles the TensorFlowLite API, object detector and image analyzer object.
## Setting up
Create a new empty project project and make the minimum SDK API 22 or android 5.1(lolipop)

(optional) Create a virtual device similar to the vuzix blade by importing [this settings](/VuzixBlade.xml) into your 
Virtual Device Configuration and creating a new virtual device based on that configuration

Enable [ADB debugging](https://intercom.help/vuzix/en/articles/5954656-enable-adb-debugging-on-the-vuzix-blade) on the 
vuzix blade and connect it to your computer via wire

Download a tflite **Metadata** file and add it to your assets folder (src/main/assets). This [file](app/src/main/assets/lite-model_efficientdet_lite0_detection_metadata_1.tflite) is recommended for balance between accuracy and performance
## Dependencies
These dependencies are needed in your app build.gradle file to access the various libraries and APIs
```
implementation 'com.google.android.material:material:1.8.0'

implementation 'com.vuzix:hud-actionmenu:2.9.0'

implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
implementation 'androidx.camera:camera-core:1.2.2'
implementation 'androidx.camera:camera-view:1.2.2'
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'androidx.camera:camera-lifecycle:1.2.2'
implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"

implementation 'org.tensorflow:tensorflow-lite-task-vision:0.4.3'
implementation 'org.tensorflow:tensorflow-lite-gpu:2.12.0'
implementation 'org.tensorflow:tensorflow-lite-api:2.12.0'
implementation 'androidx.camera:camera-camera2:1.2.2'

testImplementation 'junit:junit:4.13.2'
androidTestImplementation 'androidx.test.ext:junit:1.1.5'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
```
This block of code is needed in your settings.gradle file for the dependencies to be downloaded
```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
}
```
Keep in mind that Vuzix Blade does not have access to Google Play services so make sure to take API from TensorFlow Lite that does not use these services.
## References
[CameraX](https://developer.android.com/training/camerax)

[Vuzix Blade](https://intercom.help/vuzix/en/collections/3327366-blade-blade-upgraded)

[TensorFlow Lite](https://www.tensorflow.org/lite/android/tutorials/object_detection)
