package com.example.joystickapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Button;

public class JoystickActivity extends AppCompatActivity {
    private TcpClient client;
    private JoyStickView joyStickView;

    private boolean isTouchingJoystick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // get client from login.
        client = (TcpClient) getIntent().getSerializableExtra("Client");
        try {
            client.Connect();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        this.joyStickView = new JoyStickView(this);
        setContentView(joyStickView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_UP:

                // the current gesture has been aborted
            case MotionEvent.ACTION_CANCEL:
                this.joyStickView.setNewX(joyStickView.getCenterX());
                this.joyStickView.setNewY(joyStickView.getCenterY());
                this.isTouchingJoystick=false;
                break;

            case MotionEvent.ACTION_DOWN:
                if (Math.sqrt(Math.pow(this.joyStickView.getCenterX() - event.getX(), 2)
                        + Math.pow(this.joyStickView.getCenterY() - event.getY(), 2))
                        > this.joyStickView.getHatRadius()) {
                    this.isTouchingJoystick = false;
                    return false;
                } else
                    this.isTouchingJoystick = true;
                break;

            case MotionEvent.ACTION_MOVE:
//                this.joyStickView.setNewX(event.getX());
//                this.joyStickView.setNewY(event.getY());
                if (this.isTouchingJoystick) {
                    this.joyStickView.setNewPos(event.getX(), event.getY());
                }
                break;

        }

        this.joyStickView.postInvalidate(); // "recall" the view draw again
        return true;

    }
}