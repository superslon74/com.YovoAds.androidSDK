package com.yovoads.androidSDK;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.core.YTimer;


public class MainActivity extends Activity
{

    public static MainActivity mc_this = null;
    public static Activity m_activity = null;

    public  static YovoSDK m_yovoSDK;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mc_this = this;
        m_activity = this;

        MyButtons.getInstance();

        Init.initYovoSDK(MainActivity.this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        YTimer.getInstance().SetAppPause(true);
        YovoSDK.ShowLog("TIMER", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        YTimer.getInstance().SetAppPause(false);
        YovoSDK.ShowLog("TIMER", "onResume");
    }

}

