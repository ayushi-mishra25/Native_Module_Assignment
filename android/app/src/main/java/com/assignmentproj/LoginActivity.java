package com.assignmentproj;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create UI
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        EditText username = new EditText(this);
        username.setHint("Username");
       

        EditText password = new EditText(this);
        password.setHint("Password");

        Button loginButton = new Button(this);
        loginButton.setText("Login");
        

        loginButton.setOnClickListener(v -> {
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
            finish(); // closes the screen
        });

        layout.addView(username);
        layout.addView(password);
        layout.addView(loginButton);

        setContentView(layout);
    }
}
