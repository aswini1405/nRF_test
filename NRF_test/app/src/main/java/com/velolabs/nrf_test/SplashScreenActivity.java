package com.velolabs.nrf_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class SplashScreenActivity extends Activity {
	private static final int DURATION = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		final View view = findViewById(R.id.relativeSplash);
		final Animation alpha = new AlphaAnimation(1, 0);
		alpha.setDuration(200);

		final Handler handler = new Handler();

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				view.setAnimation(alpha);
			}
		}, 700);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				final Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);
				finish();
			}
		}, DURATION);
	}

	@Override
	public void onBackPressed() {
		// No need of splash screen to be interrupted
	}
}
