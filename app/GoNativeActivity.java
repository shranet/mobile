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
import android.provider.Settings.Secure;
import android.content.Intent;
import android.os.SystemClock;

public class GoNativeActivity extends NativeActivity {
	private static GoNativeActivity goNativeActivity;

	public GoNativeActivity() {
		super();
		goNativeActivity = this;
	}

	String getTmpdir() {
		return getCacheDir().getAbsolutePath();
	}

	String getAssetsPath() {
	    Log.d("Go", "GoNativeActivity getAssetsPath");
		return getFilesDir().getAbsolutePath();
    }

    String getAndroidId() {
        Log.d("Go", "GoNativeActivity getAndroidId");
        return Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
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

// 		Intent intent = new Intent("org.golang.app.MyService");
//         this.startService(intent);

        new Thread(new Runnable() {
            public void run() {
                SystemClock.sleep(3000);

                final Intent dialogIntent = new Intent(getApplicationContext(), WViewActivity.class);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dialogIntent);
            }
          }).start();
	}
}
