package com.odyssey.apps.cutpastephoto;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;



import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
//                .withSplashTimeOut(10000)
                .withBackgroundColor(Color.parseColor("#424143"))
                .withLogo(R.drawable.splash);

        View view = config.create();
        setContentView(view);
    }
}
