package com.example.game;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button playButton;
    Button settingsButton;
    Button aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        playButton = (Button) findViewById(R.id.play);
        settingsButton = (Button) findViewById(R.id.settings);
        aboutButton = (Button) findViewById(R.id.about);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.play:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Intent homeIntent = new Intent(MenuActivity.this, Camera2Activity.class);
                            startActivity(homeIntent);
                            //finish();
                        } else{
                            Intent homeIntent = new Intent(MenuActivity.this, CameraActivity.class);
                            startActivity(homeIntent);
                            //finish();
                        }
                        break;
                    case R.id.settings:
                        Intent homeIntent1 = new Intent(MenuActivity.this, SettingsActivity.class);
                        startActivity(homeIntent1);
                        break;
                    case R.id.about:
                        break;
                }
            }
        } ;

        playButton.setOnClickListener(onClickListener);
        settingsButton.setOnClickListener(onClickListener);
        aboutButton.setOnClickListener(onClickListener);
    }
}
