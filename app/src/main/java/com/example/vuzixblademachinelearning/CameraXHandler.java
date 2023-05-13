package com.example.vuzixblademachinelearning;

import android.content.Context;

import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

public class CameraXHandler {
    private final Context context;
    private final PreviewView previewView;
    private final ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    public CameraXHandler(Context context, PreviewView previewView){
        this.context = context;
        this.previewView = previewView;
        this.previewView.setImplementationMode(PreviewView.ImplementationMode.COMPATIBLE);

        cameraProviderFuture = ProcessCameraProvider.getInstance(context);
    }

    public void startPreview() {
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder()
                        .build();

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .build();

                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)context, cameraSelector, preview);

            } catch ( Exception e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(context));
    }

    public void startAnalysis(ImageAnalysis imageAnalyzer) {
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .build();

                Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)context, cameraSelector, imageAnalyzer);
                CameraControl cameraControl = camera.getCameraControl();
                cameraControl.setZoomRatio(3.5f);

            } catch ( Exception e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(context));
    }

    public void startPreview(ImageAnalysis imageAnalyzer) {
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder()
                        .build();

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .build();

                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)context, cameraSelector, preview, imageAnalyzer);
            } catch ( Exception e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(context));
    }

    public void stopCamera(){
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                cameraProvider.unbindAll();
            } catch ( Exception e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(context));
    }

}
