package com.newnergy.para_client;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class Client_Further_Info_sub extends AppCompatActivity {

    private Animator mCurrentAnimator;
    private int mLongAnimationDuration;
    private PinchZoomImageView pinchZoomImageView;
    private ImageView imageView;
    private ScaleGestureDetector scaleGestureDetector;
    private Matrix matrix = new Matrix();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_furtherinfo_sublayout);

       // imageView = (ImageView) findViewById(R.id.imageView_furtherInfo_imageViewer);
        //scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

       // pinchZoomImageView = (PinchZoomImageView) findViewById(R.id.imageView_furtherInfo_imageViewer);
    }

    public  boolean onTouchEvent(MotionEvent ev) {
        scaleGestureDetector.onTouchEvent(ev);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));

            matrix.setScale(scaleFactor, scaleFactor);
            imageView.setImageMatrix(matrix);
            return true;
        }
    }

    private void zoomImageFromThumb() {
        if(mCurrentAnimator != null)
            mCurrentAnimator.cancel();

            //load image res---empty

        Rect startBounds = new Rect();
        Rect finalBounds = new Rect();
        Point globalOffset = new Point();
        pinchZoomImageView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if((float) finalBounds.width() / finalBounds.height() >
                (float) startBounds.width() / startBounds.height()){
            startScale = (float) startBounds.height() / finalBounds.height();

            float startWidth = (float) startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = (float) startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= startHeight;
            startBounds.bottom += startHeight;
        }

        pinchZoomImageView.setAlpha(0f);

        pinchZoomImageView.setPivotX(0f);
        pinchZoomImageView.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(pinchZoomImageView, View.X, startBounds.left, finalBounds.left ))
                .with(ObjectAnimator.ofFloat(pinchZoomImageView, View.Y, startBounds.top, finalBounds.top ))
                .with(ObjectAnimator.ofFloat(pinchZoomImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(pinchZoomImageView, View.SCALE_Y, startScale, 1f));
        set.setDuration(mLongAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        set.start();
        mCurrentAnimator = set;

    }

}
