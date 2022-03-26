package com.example.royalsoftapk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static int SPLASH_SCREEN = 4000;
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView im2;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Hooks
        image = findViewById(R.id.imageView);
       // logo = findViewById(R.id.Welcome);
        im2=findViewById(R.id.lm2);
        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        image.setAnimation(topAnim);
        //logo.setAnimation(bottomAnim);
        im2.setAnimation(bottomAnim);




        //Calling New Activity after SPLASH_SCREEN seconds 1s = 1000
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          Intent intent = new Intent(MainActivity.this, Login.class);
                                          startActivity(intent);
                                          finish();

                                      }
                                  }, //Pass time here
                SPLASH_SCREEN);
    }





}
