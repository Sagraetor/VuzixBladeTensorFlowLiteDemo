package com.example.vuzixblademachinelearning;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import org.tensorflow.lite.task.vision.detector.Detection;

import java.util.ArrayList;
import java.util.List;

public class TFLResultsView extends View implements TFLiteListener{

    private List<Detection> results = new ArrayList<>();
    private Paint paint;
    private boolean isOverlayingPreview = true;
    private final Integer[] colorList = new Integer[]{Color.RED, Color.BLUE, Color.GREEN};

    public TFLResultsView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setTextSize(50);
    }

    @Override
    protected void onDraw(Canvas canvas){
        if (isOverlayingPreview)
            for (Detection result:results) {
                RectF rect = result.getBoundingBox();
                rect.left -= 90;
                rect.right -= 90;
                canvas.drawText(result.getCategories().get(0).getLabel(), rect.left + 5, rect.top + 40, paint);
                canvas.drawRect(rect, paint);

                rect = Scale(rect, 3.5f);
                canvas.drawRect(rect, paint);

            }
        else {
            for (Detection result:results) {
                int currIndex = results.indexOf(result);
                paint.setColor(colorList[currIndex]);
                RectF rect = result.getBoundingBox();
                rect.left -= 90;
                rect.right -= 90;

                //rect = Scale(rect, 3.5f);
                canvas.drawText(result.getCategories().get(0).getLabel(), 5, 40 * (currIndex + 1), paint);

                //below is method to get general location of object if out of bounds and points to it
                float arrowHeight;

                if (rect.centerY() < 30)
                    arrowHeight = 30;
                else if (rect.centerY() > 450)
                    arrowHeight = 450;
                else
                    arrowHeight = rect.centerY();

                if (rect.centerX() > 480)
                    canvas.drawText(">", 450, arrowHeight, paint);
                else if (rect.centerX() < 0)
                    canvas.drawText("<", 30, arrowHeight, paint);
                else if (rect.centerY() < 0)
                    canvas.drawText("^", rect.centerX(), 30, paint);
                else if (rect.centerY() > 480)
                    canvas.drawText("v", rect.centerX(), 450, paint);
                else
                    canvas.drawRect(rect, paint);
            }
        }

        super.onDraw(canvas);
    }

    @Override
    public void onResults(@Nullable List<Detection> results, long inferenceTime, int imageHeight, int imageWidth) {
        this.results = results;
        postInvalidate();
    }

    public void setIsOverlayingPreview(boolean overlayingPreview) {
        isOverlayingPreview = overlayingPreview;
    }

    private RectF Scale(RectF rect, float scale){
        float middle = 240;
        rect.left = ((rect.left - middle) * scale) + middle;
        rect.right = ((rect.right - middle) * scale) + middle;
        rect.top = ((rect.top - middle) * scale) + middle;
        rect.bottom = ((rect.bottom - middle) * scale) + middle;
        return rect;
    }
}
