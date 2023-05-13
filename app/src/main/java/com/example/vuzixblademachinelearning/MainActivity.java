package com.example.vuzixblademachinelearning;

import android.hardware.Camera;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.camera.core.ImageAnalysis;
import androidx.camera.view.PreviewView;

import com.vuzix.hud.actionmenu.ActionMenuActivity;

public class MainActivity extends ActionMenuActivity{

    CameraXHandler cameraXHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected boolean onCreateActionMenu(Menu menu) {
        super.onCreateActionMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem mainScreenMI = menu.findItem(R.id.item1);
        MenuItem camXViewMI = menu.findItem(R.id.item2);
        MenuItem trueViewMI = menu.findItem(R.id.item3);
        return true;
    }

    @Override
    protected boolean alwaysShowActionMenu() {
        return false;
    }

    public void showHello(MenuItem MI){
        setContentView(R.layout.activity_main);

        if (cameraXHandler != null)
            cameraXHandler.stopCamera();
    }

    public void showCameraX(MenuItem MI){
        setContentView(R.layout.camx_view);

        PreviewView previewView = findViewById(R.id.cameraX_preview);
        TFLResultsView tflResultsView = findViewById(R.id.overlay_view);

        if (cameraXHandler == null)
            cameraXHandler = new CameraXHandler(this, previewView);
        else
            cameraXHandler.stopCamera();

        TFLiteHandler tfLiteHandler = new TFLiteHandler(this, tflResultsView);
        ImageAnalysis analysis = tfLiteHandler.getImageAnalyzer();

        tflResultsView.setIsOverlayingPreview(true);
        cameraXHandler.startPreview(analysis);
    }

    public void showTrue(MenuItem MI){
        setContentView(R.layout.camx_view);
        closeActionMenu(true);

        PreviewView previewView = findViewById(R.id.cameraX_preview);
        TFLResultsView tflResultsView = findViewById(R.id.overlay_view);

        if (cameraXHandler == null)
            cameraXHandler = new CameraXHandler(this, previewView);
        else
            cameraXHandler.stopCamera();

        TFLiteHandler tfLiteHandler = new TFLiteHandler(this, tflResultsView);
        ImageAnalysis analysis = tfLiteHandler.getImageAnalyzer();

        tflResultsView.setIsOverlayingPreview(false);
        cameraXHandler.startAnalysis(analysis);
    }

}