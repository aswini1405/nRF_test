package com.velolabs.nrf_test;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class PermissionRationaleFragment extends DialogFragment {
	private static final String ARG_PERMISSION = "ARG_PERMISSION";
	private static final String ARG_TEXT = "ARG_TEXT";

	private PermissionDialogListener mListener;

	public interface PermissionDialogListener {
		void onRequestPermission(final int permissionType);
		void onCancelRequestPermission();
	}

	@Override
	public void onAttach(final Context context) {
		super.onAttach(context);

		if (context instanceof PermissionDialogListener) {
			mListener = (PermissionDialogListener) context;
		} else {
			throw new IllegalArgumentException("The activity must implement PermissionDialogListener interface");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public static PermissionRationaleFragment getInstance(final int aboutResId, final int permissionType) {
		final PermissionRationaleFragment fragment = new PermissionRationaleFragment();

		final Bundle args = new Bundle();
		args.putInt(ARG_TEXT, aboutResId);
		args.putInt(ARG_PERMISSION, permissionType);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	@NonNull
	public Dialog onCreateDialog(final Bundle savedInstanceState) {
		final Bundle args = getArguments();
		final StringBuilder text = new StringBuilder(getString(args.getInt(ARG_TEXT)));
		return new AlertDialog.Builder(getActivity()).setTitle(R.string.rationale_title).setMessage(text)
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mListener.onCancelRequestPermission();
					}
				})
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog, final int which) {
						if (getParentFragment() instanceof PermissionDialogListener)
							mListener = (PermissionDialogListener) getParentFragment();
						mListener.onRequestPermission(args.getInt(ARG_PERMISSION));
					}
				}).create();
	}
}
