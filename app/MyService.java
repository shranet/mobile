package org.golang.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service ;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.net.Socket;
import android.os.SystemClock;
import java.lang.Thread;

public class MyService extends Service  {
//     static {
//         System.loadLibrary("abu-saxiy");
//     }

    private native void printText();

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Go", "MyService onBind");
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Go", "MyService onStartCommand");


        new Thread(new Runnable() {
            public void run() {
                SystemClock.sleep(3000);

                final Intent dialogIntent = new Intent(getApplicationContext(), WViewActivity.class);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dialogIntent);

                stopSelf();
            }
          }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onStart() {
        Log.d("Go", "MyService onStart");
    }

    public void onDestroy() {
        Log.d("Go", "MyService onDestroy");
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        Log.d("Go", "MyService onCreate");
        super.onCreate();

        Log.d("Go", "MyService before call");
        printText();
        Log.d("Go", "MyService after call");
    }
}