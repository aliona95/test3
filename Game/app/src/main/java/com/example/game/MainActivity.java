package com.example.game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){
                  /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Intent homeIntent = new Intent(MainActivity.this, Camera2Activity.class);
                        startActivity(homeIntent);
                        finish();
                    } else{
                        Intent homeIntent = new Intent(MainActivity.this, CameraActivity.class);
                        startActivity(homeIntent);
                        finish();
                    }*/
                    Intent homeIntent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            },SPLASH_TIME_OUT);
        }
/*
        Intent i;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            i = new Intent(this, Camera2Activity.class);
        } else {
            i = new Intent(this, CameraActivity.class);
        }
        startActivity(i);
*/

}
