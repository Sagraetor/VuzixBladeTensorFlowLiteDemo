package com.example.vuzixblademachinelearning

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.SystemClock
import android.util.Log
import androidx.camera.core.AspectRatio
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.Rot90Op
import org.tensorflow.lite.task.vision.detector.*
import org.tensorflow.lite.task.vision.detector.ObjectDetector.ObjectDetectorOptions
import java.util.concurrent.Executors

class TFLiteHandler(var context: Context,
                    var listener : TFLiteListener?
                    ) {
    private val model = "lite-model_efficientdet_lite0_detection_metadata_1.tflite"
    private lateinit var bitmapBuffer : Bitmap
    private lateinit var objectDetector : ObjectDetector
    private var cameraExecutor = Executors.newSingleThreadExecutor()

    var imageAnalyzer: ImageAnalysis

    init {
        //Builds the object detector
        val ODOptions = ObjectDetectorOptions.builder()
            .setScoreThreshold(.6f)
            .setMaxResults(3)
            .build()

        try {
            objectDetector = ObjectDetector.createFromFileAndOptions(context, model, ODOptions)
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "ObjectDetector failed to initialize: " + e.message)
        }

        //Builds the image analyzer
        imageAnalyzer = ImageAnalysis.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor) { image ->
                        if (!::bitmapBuffer.isInitialized) {
                            bitmapBuffer = Bitmap.createBitmap(
                                    image.width,
                                    image.height,
                                    Bitmap.Config.ARGB_8888
                            )
                        }
                        detectObjects(image)
                    }
                }

    }


    private fun detectObjects(image: ImageProxy) {
        // Copy out RGB bits from the image analyzer to the shared bitmap buffer
        image.use { bitmapBuffer.copyPixelsFromBuffer(image.planes[0].buffer) }
        val imageRotation = image.imageInfo.rotationDegrees
        detect(bitmapBuffer, imageRotation)
    }


    private fun detect(image: Bitmap, imageRotation: Int) {
        // Inference time is the difference between the system time at the start and finish of the process
        var inferenceTime = SystemClock.uptimeMillis()

        // Create preprocessor for the image.
        // See https://www.tensorflow.org/lite/inference_with_metadata/lite_support#imageprocessor_architecture
        val imageProcessor = ImageProcessor.Builder().add(Rot90Op(-imageRotation / 90)).build()

        // Preprocess the image and convert it into a TensorImage for detection.
        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(image))

        val results = objectDetector.detect(tensorImage)
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime

        // Sends results to a listener (TFLResultsView object)
        listener?.onResults(
                results,
                inferenceTime,
                tensorImage.height,
                tensorImage.width)
    }

}


interface TFLiteListener {
    fun onResults(
        results: MutableList<Detection>?,
        inferenceTime: Long,
        imageHeight: Int,
        imageWidth: Int
    )
}