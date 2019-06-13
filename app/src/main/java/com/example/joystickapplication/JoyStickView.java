package com.example.joystickapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.DisplayMetrics;
import android.view.View;


public class JoyStickView extends View {

    public float centerX;
    public float centerY;

    public float baseRadius;
    public float hatRadius;

    public float getHatRadius() {
        return hatRadius;
    }

    public float newX;
    public float newY;

    public void setNewX(float newX) {
        this.newX = newX;
    }

    public void setNewY(float newY) {
        this.newY = newY;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public JoyStickView(Context context) {
        super(context);

        DisplayMetrics dm = getResources().getDisplayMetrics();

        newX = centerX = (float) dm.widthPixels / 2; // the width
        newY = centerY = (float) dm.heightPixels / 2; // the height
        // ensures that the joystick will always fit inside the surface view even if one dimension is greatly smaller than the other.
        baseRadius = (float) Math.min(dm.widthPixels, dm.heightPixels) / 3;
        hatRadius = (float) Math.min(dm.widthPixels, dm.heightPixels) / 6;

    }


    // todo : create view when flipping the screen

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawJoystick(canvas, this.newX, this.newY);
    }


    //todo : NICE TO HAVE : to change color ASAP !
    // todo : MUST : to fix the position of the big and small circles on the screen !!!

    // drawing the joystick on the screen
    private void drawJoystick(Canvas myCanvas, float newX, float newY) {
        Paint colors = new Paint();
        myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // Clearing the BG

        // todo : NICE TO HAVE : to move the coloring to constant params

        colors.setColor(Color.argb(250, 50, 50, 50));// color of the joystick base
        myCanvas.drawCircle(centerX, centerY, baseRadius, colors); // draw the joystick base
        colors.setColor(Color.argb(255, 0, 0, 255));// colo of the joystick itself
        myCanvas.drawCircle(newX, newY, hatRadius, colors); // draw the joystick hat

    }
}