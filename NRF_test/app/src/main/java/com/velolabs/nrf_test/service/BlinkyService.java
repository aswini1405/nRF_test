package com.velolabs.nrf_test.service;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.velolabs.nrf_test.profile.BleManager;
import com.velolabs.nrf_test.profile.BleProfileService;

public class BlinkyService extends BleProfileService implements BlinkyManagerCallbacks {
	private static final String TAG = "BlinkyService";

	public static final String BROADCAST_LED_STATE_CHANGED = "no.nordicsemi.android.nrfblinky.BROADCAST_LED_STATE_CHANGED";
	public static final String BROADCAST_BUTTON_STATE_CHANGED = "no.nordicsemi.android.nrfblinky.BROADCAST_BUTTON_STATE_CHANGED";
	public static final String EXTRA_DATA = "no.nordicsemi.android.nrfblinky.EXTRA_DATA";

	private BlinkyManager mManager;

	private final BlinkyBinder mBinder = new BlinkyBinder();

	public class BlinkyBinder extends BleProfileService.LocalBinder implements BlinkyInterface {
		private boolean mLEDState;
		private boolean mButtonState;

		@Override
		public void send(final boolean onOff) {
			mManager.send(mLEDState = onOff);
		}

		public boolean isOn() {
			return mLEDState;
		}

		public boolean isButtonPressed() {
			return mButtonState;
		}
	}

	@Override
	protected LocalBinder getBinder() {
		return mBinder;
	}

	@Override
	protected BleManager<BlinkyManagerCallbacks> initializeManager() {
		return mManager = new BlinkyManager(this);
	}

	@Override
	public void onDataReceived(final boolean state) {
		mBinder.mButtonState = state;

		final Intent broadcast = new Intent(BROADCAST_BUTTON_STATE_CHANGED);
		broadcast.putExtra(EXTRA_DATA, state);
		LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast);
	}

	@Override
	public void onDataSent(boolean state) {
		mBinder.mLEDState = state;

		final Intent broadcast = new Intent(BROADCAST_LED_STATE_CHANGED);
		broadcast.putExtra(EXTRA_DATA, state);
		LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast);
	}
}
