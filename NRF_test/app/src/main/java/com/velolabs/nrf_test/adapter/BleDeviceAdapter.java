package com.velolabs.nrf_test.adapter;

import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.velolabs.nrf_test.R;
import com.velolabs.nrf_test.support.v18.scanner.ScanResult;

public class BleDeviceAdapter extends BaseAdapter {
	private ArrayList<ExtendedBluetoothDevice> mDevices;

	public BleDeviceAdapter() {
		// The list of devices will be cleared after rotation. For simplicity we do not keep the old devices.
		this.mDevices = new ArrayList<>();
	}

	@Override
	public int getCount() {
		return mDevices.size();
	}

	@Override
	public BluetoothDevice getItem(int position) {
		return this.mDevices.get(position).getBluetoothDevice();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void clear() {
		this.mDevices.clear();
	}

	public void addDevice(ExtendedBluetoothDevice device) {
		this.mDevices.add(device);
	}

	public boolean hasDevice(ScanResult result) {
		for (ExtendedBluetoothDevice device : mDevices) {
			if (device.matches(result))
				return true;
		}
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_device, null);
			viewHolder = new ViewHolder();
			viewHolder.deviceAddress = (TextView) convertView.findViewById(R.id.device_address);
			viewHolder.deviceName = (TextView) convertView.findViewById(R.id.device_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final ExtendedBluetoothDevice device = mDevices.get(position);
		final String deviceName = device.getName();

		if (!TextUtils.isEmpty(deviceName))
			viewHolder.deviceName.setText(deviceName);
		else
			viewHolder.deviceName.setText(R.string.unknown_device);
		viewHolder.deviceAddress.setText(device.getAddress());
		return convertView;
	}
}
