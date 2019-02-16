package com.yovoads.Android;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.core.YTimer;
import com.yovoads.yovoplugin.implementations.reward.YRewardLoader;
import com.yovoads.yovoplugin.implementations.reward.YRewardView;


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

        Init.initYovoSDK(MainActivity.this);
        m_yovoSDK.Start();
        MyButtons.getInstance();



        //new YRewardLoader().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, EAdNetworkType.GetValue(EAdNetworkType._CROSS_PROMOTION));





//        Date now = new Date();
//        Long longTime = new Long(now.getTime()/1000);
        //YovoSDK.ShowLog("qw", longTime.intValue());


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

        //DI.m_activity.startActivity(new Intent(DI.m_activity, YRewardView.class));
    }

}

