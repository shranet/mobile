package org.golang.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import java.util.concurrent.TimeUnit;
import android.widget.LinearLayout;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.content.pm.ActivityInfo;
import android.webkit.WebView;
import android.graphics.Color;
import android.widget.RelativeLayout;
import android.webkit.WebViewClient;
import android.os.Build;
import android.webkit.WebSettings;
import android.util.Log;

public class WViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        Log.d("Go", "WViewActivity start");
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
// 		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
// 				WindowManager.LayoutParams.FLAG_FULLSCREEN);

        WebView webView = new WebView(this);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        webView.setLayoutParams(params);
        webView.setBackgroundColor(Color.WHITE);
        // to enable javascripts
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//         webView.getSettings().setBuiltInZoomControls(true);
        // zoom if you want
//         webView.getSettings().setSupportZoom(true);
        // to support url redirections
        webView.setWebViewClient(new WebViewClient());
        // extra settings
        webView.getSettings().setLoadWithOverviewMode(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollContainer(true);
        // setting for lollipop and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.loadUrl("http://127.0.0.1:8558");

        Log.d("Go", "WViewActivity end");
        setContentView(webView);
    }

    protected void onStart(Bundle savedInstanceState) {

		Log.d("Go", "MainActivity onStart");

    }

}