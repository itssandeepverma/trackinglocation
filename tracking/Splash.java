package com.apkglobal.tracking;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
  ImageView iv;
  TextView Share,Here;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        iv=findViewById(R.id.icon);
        Share=findViewById(R.id.shre);
        Here=findViewById(R.id.here);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.animation);
        Animation animation2= AnimationUtils.loadAnimation(this,R.anim.animation2);
        Animation animation3= AnimationUtils.loadAnimation(this,R.anim.animation3);
        iv.startAnimation(animation);
        Share.startAnimation(animation);
        Here.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            public void run() {

                Intent i = new Intent(Splash.this, MapsActivity.class);
                startActivity(i);
                finish();
            }
        }, 4000);
    }
}
