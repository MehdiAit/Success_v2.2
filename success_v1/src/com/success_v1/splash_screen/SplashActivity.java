package com.success_v1.splash_screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.success_v1.main.Main;
import com.success_v1.successCar.R;

public class SplashActivity extends Activity {
	
	private static int SPLASH_TIME_OUT = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		//getActionBar().setDisplayShowHomeEnabled(false);
		//getActionBar().setTitle("");
		new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, Main.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
	}
	
	

}
