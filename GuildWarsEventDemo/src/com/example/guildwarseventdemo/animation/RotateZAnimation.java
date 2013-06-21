package com.example.guildwarseventdemo.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class RotateZAnimation extends Animation {
    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private final boolean mReverse;
    private Camera mCamera;
    
    
    public RotateZAnimation (float fromDegress,float toDegress, 
            float centerX, float centerY, float depthZ, boolean reverse) {
        mFromDegrees = fromDegress;
        mToDegrees = toDegress;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
        mReverse = reverse;
    }
    

    @Override
    public void initialize(int width, int height, int parentWidth,
            int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }


    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + (mToDegrees - fromDegrees)*interpolatedTime;
        
        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;
        
        final Matrix matrix = t.getMatrix();
        
        camera.save();
        if(!mReverse) {
            camera.translate(0, 0, mDepthZ*interpolatedTime);
        }
        else {
            camera.translate(0, 0, mDepthZ*(1-interpolatedTime));
        }
        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();
        
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
    
    
}
