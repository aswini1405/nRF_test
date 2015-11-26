package com.velolabs.nrf_test.support.v18.scanner;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.SystemClock;
import android.support.annotation.RequiresPermission;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* package */ class BluetoothLeScannerImplJB extends BluetoothLeScannerCompat implements BluetoothAdapter.LeScanCallback {
	private final BluetoothAdapter mBluetoothAdapter;
	private final Map<ScanCallback, ScanCallbackWrapper> mWrappers;

	public BluetoothLeScannerImplJB() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		mWrappers = new HashMap<>();
	}

	@Override
	@RequiresPermission(allOf = {Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH})
	@SuppressWarnings("deprecation")
	/* package */ void startScanInternal(final List<ScanFilter> filters, final ScanSettings settings, final ScanCallback callback) {
		BluetoothLeUtils.checkAdapterStateOn(mBluetoothAdapter);

		if (mWrappers.containsKey(callback)) {
			throw new IllegalArgumentException("scanner already started with given callback");
		}

		boolean shouldStart;
		synchronized (mWrappers) {
			shouldStart = mWrappers.isEmpty();

			final ScanCallbackWrapper wrapper = new ScanCallbackWrapper(filters, settings, callback);
			mWrappers.put(callback, wrapper);
		}

		if (shouldStart) {
			mBluetoothAdapter.startLeScan(this);
		}
	}

	@Override
	@RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
	@SuppressWarnings("deprecation")
	public void stopScan(final ScanCallback callback) {
		synchronized (mWrappers) {
			final ScanCallbackWrapper wrapper = mWrappers.get(callback);
			if (wrapper == null)
				return;

			mWrappers.remove(callback);
			wrapper.close();
		}

		if (mWrappers.isEmpty()) {
			mBluetoothAdapter.stopLeScan(this);
		}
	}

	@Override
	@RequiresPermission(Manifest.permission.BLUETOOTH)
	public void flushPendingScanResults(final ScanCallback callback) {
		BluetoothLeUtils.checkAdapterStateOn(mBluetoothAdapter);
		if (callback == null) {
			throw new IllegalArgumentException("callback cannot be null!");
		}
		mWrappers.get(callback).flushPendingScanResults();
	}

	@Override
	public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
		final ScanResult scanResult = new ScanResult(device, ScanRecord.parseFromBytes(scanRecord), rssi, SystemClock.elapsedRealtimeNanos());

		synchronized (mWrappers) {
			final Collection<ScanCallbackWrapper> wrappers = mWrappers.values();
			for (final ScanCallbackWrapper wrapper : wrappers) {
				wrapper.handleScanResult(scanResult);
			}
		}
	}
}
