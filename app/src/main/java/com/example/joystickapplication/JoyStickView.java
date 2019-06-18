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
//
//    private float sin;
//    private float cos;
//    private float hypotenuse;
//
//    private void updateSinCos(int newx, int newy) {
//        //First determine the sin and cos of the angle that the touched point is at relative to the center of the joystick
//        float hypotenuse = (float) Math.sqrt(Math.pow(newX - centerX, 2) + Math.pow(newY - centerY, 2));
//        float sin = (newY - centerY) / hypotenuse; //sin = o/h
//        float cos = (newX - centerX) / hypotenuse; //cos = a/h
//    }

    public void setNewPos(float newX, float newY) {
        double distance = this.distance(newX, newY, this.centerX, this.centerY);
        // if the point is inside the region of the joystick
        if (distance <= this.baseRadius) {
            this.newX = newX;
            this.newY = newY;
        } else {
            float dx = newX - centerX;
            float dy = newY - centerY;
            float dR = (float) (distance - baseRadius);

            // using Thales' theorem to calculate the lengths that are outside the joystick
            float deltaX = (float) (dR * dx / distance);
            float deltaY = (float) (dR * dy / distance);

            this.newX = newX - deltaX;
            this.newY = newY - deltaY;
        }
    }


    public void setNewX(float newX) {
//        if (this.distance(newX, this.))
//            if (centerX - baseRadius < newX && newX < centerX + baseRadius)
//                this.newX = newX;
//            else {
//                float cos = (newX - centerX) / (float) Math.sqrt(Math.pow(newX - centerX, 2) + Math.pow(newY - centerY, 2));
//                this.newX = cos * baseRadius;
//            }
        this.newX = newX;
    }

    public void setNewY(float newY) {
//        if (centerY - baseRadius < newY && newY < centerY + baseRadius)
//            this.newY = newY;
//        else {
//            float sin = (newY - centerY) / (float) Math.sqrt(Math.pow(newX - centerX, 2) + Math.pow(newY - centerY, 2));
//            this.newY = sin * baseRadius;
//        }
        this.newY = newY;
    }

    public double distance(float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
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
        hatRadius = (float) Math.min(dm.widthPixels, dm.heightPixels) / 7;

    }

    private int getStatusbarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0)
            return getResources().getDimensionPixelSize(resourceId);
        return 0;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawJoystick(canvas, this.newX, this.newY);
    }


    //todo : NICE TO HAVE : to change color ASAP !

    // drawing the joystick on the screen
    private void drawJoystick(Canvas myCanvas, float newX, float newY) {
        Paint colors = new Paint();
        myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // Clearing the BG

        // todo : NICE TO HAVE : to move the coloring to constant params

        colors.setColor(Color.argb(250, 50, 50, 50));// color of the joystick base
        myCanvas.drawCircle(centerX, centerY - getStatusbarHeight(), baseRadius, colors); // draw the joystick base
        colors.setColor(Color.argb(255, 0, 0, 255));// colo of the joystick itself
        myCanvas.drawCircle(newX, newY - getStatusbarHeight(), hatRadius, colors); // draw the joystick hat


    }
}