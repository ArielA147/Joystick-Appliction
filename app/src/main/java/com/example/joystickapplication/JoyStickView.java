package com.example.joystickapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView; // the base of the joystick that we will build upon

import java.text.AttributedCharacterIterator;

public class JoyStickView extends SurfaceView {

    private float centerX;
    private float centerY;

    public JoyStickView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public JoyStickView(Context context, AttributeSet attributes, int style) {
        super(context, attributes, style);
        getHolder().addCallback(this);
    }

    public JoyStickView(Context context, AttributeSet attributes) {
        super(context, attributes);
        getHolder().addCallback(this);
    }


    //now we will implement the callbacks.
    // callback = a way that an object within a class can notify the class that a specific event has occurred within it


    //this method will be called when the SurfaceView has been fully created and has all the dimensions laid out and is ready for drawing.
    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        //set the callback methods in this class to be the ones to be called when those events happen.
        setupDimensions();
        drawJoystick(centerX, centerY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //set the callback methods in this class to be the ones to be called when those events happen.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //set the callback methods in this class to be the ones to be called when those events happen.
    }


    private void drawJoystick(float newX , float newY){

        if(getHolder().getSurface().isValid()){
            Canvas myCanvas = this.getHolder().lockCanvas(); // stuff to draw
            Paint colors = new Paint();
            myCanvas.drawColor(Color.TRANSPARENT , PorterDuff.Mode.CLEAR); // Clearing the BG
            colors.setARGB(25,50,50,50);// color of the joystick base
            myCanvas.drawCircle(centerX, centerY, baseRadius, colors); // draw the joystick base
            colors.setARGB(255,0,0,255); // colo of the joystick itself
            myCanvas.drawCircle(newX,newY, hatRadius, colors); // draw the joystick hat
            getHolder().unlockCanvasAndPost(myCanvas); // write the new drawing to the SurfaceView



        }

    }



}
