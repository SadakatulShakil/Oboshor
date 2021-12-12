package com.adndiginet.terbb.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.adndiginet.terbb.R;
import com.trncic.library.DottedProgressBar;

public class SplashActivity extends AppCompatActivity {

    protected DottedProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        inItView();
        progressBar.startProgress();
        runSplash();

        if(Build.VERSION.SDK_INT>=19 && Build.VERSION.SDK_INT<21){
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if(Build.VERSION.SDK_INT>=19){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if(Build.VERSION.SDK_INT>=21){
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void runSplash() {
        Thread timer  = new Thread(){
            public void run(){
                try {
                    sleep(3500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                finally{
                    progressBar.stopProgress();
                    finish();
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void inItView() {
        progressBar = findViewById(R.id.progress);
    }

    private static void setWindowFlag(Activity activity, final int Bits, Boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams windowParams = win.getAttributes();
        if(on){
            windowParams.flags |= Bits;
        }else{
            windowParams.flags &= ~Bits;
        }
        win.setAttributes(windowParams);
    }
}