package com.example.joystickapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        configureConnectButton();
    }

    // connecting in a new thread
    private void configureConnectButton() {
        Button connect = findViewById(R.id.buttonConnect);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ip = findViewById(R.id.ipText);
                EditText port = findViewById(R.id.portText);

                String ipSText = ip.getText().toString();
                int portNum = Integer.parseInt(port.getText().toString());
                // connect to tcpClient.
                TcpClient client = new TcpClient(ipSText, portNum);
                // create intent
                Intent intent = new Intent(LoginActivity.this, JoystickActivity.class);
                // move client to joystick.
                intent.putExtra("Client", client);
                startActivity(intent);
            }
        });
    }
}
