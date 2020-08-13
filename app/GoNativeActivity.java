package org.golang.app;

import android.app.Activity;
import android.content.DialogInterface;
import android.app.NativeActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.widget.Toast;

public class GoNativeActivity extends NativeActivity {
	private static GoNativeActivity goNativeActivity;

	public GoNativeActivity() {
		super();
		goNativeActivity = this;

		AlertDialog alertDialog = new AlertDialog.Builder(this)
		//set icon 
		.setIcon(android.R.drawable.ic_dialog_alert)
		//set title
		.setTitle("Are you sure to Exit")
		//set message
		.setMessage("Exiting will call finish() method")
		//set positive button
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				//set what would happen when positive button is clicked    
				finish();
			}
		})
		//set negative button
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				//set what should happen when negative button is clicked
				Toast.makeText(getApplicationContext(),"Nothing Happened",Toast.LENGTH_LONG).show();
			}
		})
		.show();
	}

	String getTmpdir() {
		return getCacheDir().getAbsolutePath();
	}

	static int getRune(int deviceId, int keyCode, int metaState) {
		try {
			int rune = KeyCharacterMap.load(deviceId).get(keyCode, metaState);
			if (rune == 0) {
				return -1;
			}
			return rune;
		} catch (KeyCharacterMap.UnavailableException e) {
			return -1;
		} catch (Exception e) {
			Log.e("Go", "exception reading KeyCharacterMap", e);
			return -1;
		}
	}

	private void load() {
		// Interestingly, NativeActivity uses a different method
		// to find native code to execute, avoiding
		// System.loadLibrary. The result is Java methods
		// implemented in C with JNIEXPORT (and JNI_OnLoad) are not
		// available unless an explicit call to System.loadLibrary
		// is done. So we do it here, borrowing the name of the
		// library from the same AndroidManifest.xml metadata used
		// by NativeActivity.
		try {
			ActivityInfo ai = getPackageManager().getActivityInfo(
					getIntent().getComponent(), PackageManager.GET_META_DATA);
			if (ai.metaData == null) {
				Log.e("Go", "loadLibrary: no manifest metadata found");
				return;
			}
			String libName = ai.metaData.getString("android.app.lib_name");
			System.loadLibrary(libName);
		} catch (Exception e) {
			Log.e("Go", "loadLibrary failed", e);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		load();
		super.onCreate(savedInstanceState);
	}
}
