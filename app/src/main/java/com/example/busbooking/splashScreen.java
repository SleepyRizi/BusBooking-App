package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN=2000;

    Animation topAnimation, bottomAnimation;
    ImageView ivlogo;
    TextView tvLogoText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);


       // getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        topAnimation= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //hooks
        ivlogo= (ImageView)findViewById(R.id.ivlogosplash);
        tvLogoText = (TextView) findViewById(R.id.tvLogoText);


        ivlogo.setAnimation(topAnimation);
        tvLogoText.setAnimation(bottomAnimation);


        //
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
               /* Pair[] pairs= new Pair[2];
                pairs[0]= new Pair<View,String>(ivlogo,"ivlogo");
                pairs[1]= new Pair<View,String>(tvLogoText,"tvLogoText");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(splashScreen.this,pairs);
                    startActivity(intent,options.toBundle());
                }*/


            }
        },SPLASH_SCREEN);

    }
}