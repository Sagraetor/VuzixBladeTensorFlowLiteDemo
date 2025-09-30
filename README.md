# Vuzix Blade TensorFlow Lite Demo ![Kotlin](https://img.shields.io/badge/Kotlin-FF69B4?logo=kotlin\&logoColor=white) ![Android](https://img.shields.io/badge/Android-3DDC84?logo=android\&logoColor=white) ![TensorFlow Lite](https://img.shields.io/badge/TensorFlow%20Lite-FF6F00?logo=tensorflow\&logoColor=white)

## Overview

This project demonstrates how to implement [TensorFlow Lite](https://www.tensorflow.org/lite/android/tutorials/object_detection) on the **Vuzix Blade AR glasses**.

The demo supports **two modes of results**:

1. Overlaying object detection on the **camera preview**
2. Overlaying object detection on **real-life vision**

This project was developed as part of my work as a **Research Assistant**, exploring **augmented reality object detection** on wearable devices.

---

## Project Structure

The project consists of **3 Java classes** and **1 Kotlin class**:

* **[MainActivity.java](app/src/main/java/com/example/vuzixblademachinelearning/MainActivity.java)** – Handles switching between info and the two display modes.
* **[CameraXHandler.java](app/src/main/java/com/example/vuzixblademachinelearning/CameraXHandler.java)** – Manages the CameraX API, binding the camera to a preview and the image analyzer.
* **[TFLResultsView.java](app/src/main/java/com/example/vuzixblademachinelearning/TFLResultsView.java)** – Displays TensorFlow Lite detection results either overlaying the camera preview or as a standalone view.
* **[TFLiteHandler.kt](app/src/main/kotlin/com/example/vuzixblademachinelearning/TFLiteHandler.kt)** – Handles TensorFlow Lite API integration, object detection, and image analysis.

---

## Getting Started

### 1. Create the Project

* Start a **new empty Android project**.
* Set **minimum SDK** to API 22 (Android 5.1 Lollipop).

### 2. Optional: Setup Virtual Device

* Import the [Vuzix Blade virtual device settings](/VuzixBlade.xml) to create a device similar to the Vuzix Blade for testing.

### 3. Connect the Vuzix Blade

* Enable [ADB debugging](https://intercom.help/vuzix/en/articles/5954656-enable-adb-debugging-on-the-vuzix-blade).
* Connect your Vuzix Blade to your computer via USB.

### 4. Add TensorFlow Lite Model

* Download a `.tflite` **metadata file** and place it in `src/main/assets`.
* Recommended model for balance between performance and accuracy:
  `[lite-model_efficientdet_lite0_detection_metadata_1.tflite](app/src/main/assets/lite-model_efficientdet_lite0_detection_metadata_1.tflite)`

---

## Dependencies

### **App-level `build.gradle`**

Add the following dependencies:

```gradle
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

### **Project-level `settings.gradle`**

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
}
```

> **Note:** Vuzix Blade does **not** have access to Google Play Services, so make sure to use TensorFlow Lite APIs that do not require them.

---

## What I Learned

* Integrating TensorFlow Lite with **AR wearable devices**
* Working with **CameraX API** for live video processing
* Combining **Java and Kotlin** in Android projects
* Managing **performance vs. accuracy** in ML models
* Debugging and deploying apps on **AR hardware**

---

## References

* [CameraX Documentation](https://developer.android.com/training/camerax)
* [Vuzix Blade Official Guide](https://intercom.help/vuzix/en/collections/3327366-blade-blade-upgraded)
* [TensorFlow Lite Object Detection](https://www.tensorflow.org/lite/android/tutorials/object_detection)
