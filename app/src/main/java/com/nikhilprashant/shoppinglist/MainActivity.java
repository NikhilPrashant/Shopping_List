package com.nikhilprashant.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button home, mobile, electronics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        home = findViewById(R.id.home);
        mobile = findViewById(R.id.mobile);
        electronics = findViewById(R.id.electronics);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenHomeActivity();
            }
        });
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenMobileActivity();
            }
        });
        electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenElectronicsActivity();
            }
        });
    }
    public void OpenHomeActivity() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
    public void OpenMobileActivity() {
        Intent intent2 = new Intent(MainActivity.this, MobileActivity.class);
        startActivity(intent2);
    }
    public void OpenElectronicsActivity() {
        Intent intent3 = new Intent(MainActivity.this, ElectronicsActivity.class);
        startActivity(intent3);
    }
}