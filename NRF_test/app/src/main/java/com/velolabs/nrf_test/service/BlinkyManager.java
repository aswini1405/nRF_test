package com.velolabs.nrf_test.service;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import com.velolabs.nrf_test.profile.BleManager;

public class BlinkyManager extends BleManager<BlinkyManagerCallbacks> {
	/** Nordic Blinky Service UUID */
	private final static UUID LBS_UUID_SERVICE = UUID.fromString("00001523-1212-efde-1523-785feabcd123");
	/** BUTTON characteristic UUID */
	private final static UUID LBS_UUID_BUTTON_CHAR = UUID.fromString("00001524-1212-efde-1523-785feabcd123");
	/** LED characteristic UUID */
	private final static UUID LBS_UUID_LED_CHAR = UUID.fromString("00001525-1212-efde-1523-785feabcd123");

	private BluetoothGattCharacteristic mButtonCharacteristic, mLedCharacteristic;

	public BlinkyManager(final Context context) {
		super(context);
	}

	@Override
	protected BleManagerGattCallback getGattCallback() {
		return mGattCallback;
	}

	/**
	 * BluetoothGatt callbacks for connection/disconnection, service discovery, receiving indication, etc
	 */
	private final BleManagerGattCallback mGattCallback = new BleManagerGattCallback() {

		@Override
		protected Queue<Request> initGatt(final BluetoothGatt gatt) {
			final LinkedList<Request> requests = new LinkedList<>();
			requests.push(Request.newEnableNotificationsRequest(mButtonCharacteristic));
			return requests;
		}

		@Override
		public boolean isRequiredServiceSupported(final BluetoothGatt gatt) {
			final BluetoothGattService service = gatt.getService(LBS_UUID_SERVICE);
			if (service != null) {
				mButtonCharacteristic = service.getCharacteristic(LBS_UUID_BUTTON_CHAR);
				mLedCharacteristic = service.getCharacteristic(LBS_UUID_LED_CHAR);
			}

			boolean writeRequest = false;
			if (mLedCharacteristic != null) {
				final int rxProperties = mLedCharacteristic.getProperties();
				writeRequest = (rxProperties & BluetoothGattCharacteristic.PROPERTY_WRITE) > 0;
			}

			return mButtonCharacteristic != null && mLedCharacteristic != null && writeRequest;
		}

		@Override
		protected void onDeviceDisconnected() {
			mButtonCharacteristic = null;
			mLedCharacteristic = null;
		}

		@Override
		public void onCharacteristicWrite(final BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
			mCallbacks.onDataSent(characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0) == 0x01);
		}

		@Override
		public void onCharacteristicNotified(final BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
			int data = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0);
			mCallbacks.onDataReceived(data == 0x01);
		}
	};

	public void send(final boolean onOff) {
		// Are we connected?
		if (mLedCharacteristic == null)
			return;

		byte [] command;
		if (onOff){
			command = new byte [] {1};
		} else {
			command = new byte [] {0};
		}
		mLedCharacteristic.setValue(command);
		writeCharacteristic(mLedCharacteristic);
	}
}
