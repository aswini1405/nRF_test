package com.velolabs.nrf_test.service;


import com.velolabs.nrf_test.profile.BleManagerCallbacks;

public interface BlinkyManagerCallbacks extends BleManagerCallbacks {

	/**
	 * Called when a button was pressed or released on device
	 * @param state true if the button was pressed, false if released
	 */
	void onDataReceived(final boolean state);

	/**
	 * Called when the data has been sent to the connected device.
	 * @param state true when LED was enabled, false when disabled
	 */
	void onDataSent(final boolean state);
}
