package com.example.parstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

public class SettingsActivity extends AppCompatActivity {

    Button btnLogout;
    BottomNavigationView navbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        navbar = findViewById(R.id.navbar);
        btnLogout = findViewById(R.id.btnLogout);


        // Button Listeners
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                startLoginActivity();
            }
        });

        // Navigation Bar implementation
        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.miMain:
                        startTimeLineActivity();
                        return true;
                    case R.id.miCapture:
                        startMainActivity();
                        return true;
                    case R.id.miSettings:
                        return true;
                }
                return true;
            }
        });

    }

    private void startLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void startTimeLineActivity() {
        Intent i = new Intent(this, TimelineActivity.class);
        startActivity(i);
        finish();
    }

    private void startSettingsActivity() {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
        finish();
    }

    private void startMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
