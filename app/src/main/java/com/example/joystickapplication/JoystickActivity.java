package com.example.joystickapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class JoystickActivity extends AppCompatActivity {
    private TcpClient client;
    private JoyStickView joyStickView;
    private boolean isTouchingJoystick = false;
    private String alironPath = "set /controls/flight/aileron ";
    private String elevatorPath = "set /controls/flight/elevator ";


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

        int centerX = this.joyStickView.getCenterX();
        int centerY = this.joyStickView.getCenterY();

        float xTouch = event.getX();
        float yTouch = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: // the current gesture has been aborted
                this.joyStickView.setNewX(centerX);
                this.joyStickView.setNewY(centerY);
                this.isTouchingJoystick = false;
                break;

            case MotionEvent.ACTION_DOWN:
                if (Math.sqrt(Math.pow(centerX - xTouch, 2) + Math.pow(centerY - yTouch, 2))
                        > this.joyStickView.getHatRadius()) {
                    this.isTouchingJoystick = false;
                    return false;
                } else
                    this.isTouchingJoystick = true;
                break;

            case MotionEvent.ACTION_MOVE:
                if (this.isTouchingJoystick) {
                    double angle = angle(xTouch - centerX, yTouch - centerY);
                    double distance = this.joyStickView.distance(xTouch, yTouch, centerX, centerY);
                    double magnitude = distance / (this.joyStickView.getBaseRadius() - this.joyStickView.getHatRadius());

                    // if out of base circle
                    if (magnitude >= 1) {
                        magnitude = 1;
                        this.joyStickView.setNewPos(angle);
                    } else {
                        this.joyStickView.setNewX((int) (xTouch));
                        this.joyStickView.setNewY((int) (yTouch));
                    }

                    // normalize the x,y points
                    float xNorm = (float) (magnitude * Math.cos(Math.toRadians(angle)));
                    float yNorm = (float) (magnitude * Math.sin(Math.toRadians(angle))) * (-1);

                    String alironValueMsg = alironPath + xNorm + "\r\n";
                    String elevatorValueMsg = elevatorPath + yNorm + "\r\n";

                    client.sendMessage(alironValueMsg);
                    client.sendMessage(elevatorValueMsg);

//                    client.sendMessage("this x : " + String.valueOf(xNorm) + " this y : " + String.valueOf(yNorm));
                }
                break;
        }
        this.joyStickView.postInvalidate(); // "recall" the view draw again
        return true;
    }


    public double angle(float dx, float dy) {
        if (dx >= 0 && dy >= 0) return Math.toDegrees(Math.atan(dy / dx));
        else if (dx < 0 && dy >= 0) return Math.toDegrees(Math.atan(dy / dx)) + 180;
        else if (dx < 0 && dy < 0) return Math.toDegrees(Math.atan(dy / dx)) + 180;
        else if (dx >= 0 && dy < 0) return Math.toDegrees(Math.atan(dy / dx)) + 360;
        return 0;
    }
}