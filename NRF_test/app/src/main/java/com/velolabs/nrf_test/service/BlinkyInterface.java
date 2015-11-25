package com.velolabs.nrf_test.service;

/**
 * The Blinky device interface. The blinky device will also send notifications whenever a button was pressed.
 */
public interface BlinkyInterface {
	/**
	 * Sends the LED state to the target device.
	 * @param onOff the new state. True to enable LED, false to disable it.
	 */
	void send(final boolean onOff);
}
