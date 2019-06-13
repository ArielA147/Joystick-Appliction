package com.example.joystickapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Button;

public class JoystickActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JoyStickView joyStickView = new JoyStickView(this);
        setContentView(joyStickView);

    }
}
