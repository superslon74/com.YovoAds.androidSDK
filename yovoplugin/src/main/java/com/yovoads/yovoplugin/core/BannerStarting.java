package com.yovoads.yovoplugin.core;

import android.os.SystemClock;

import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.core.Scenario.ScenarioBanner;


public class BannerStarting implements Runnable {

    private static BannerStarting mc_this = null;

    private boolean m_isRun = true;
    private int m_timePeriodMiliSecund = 144;



   /* public static BannerStarting getInstance() {
        if (mc_this == null) {
            mc_this = new BannerStarting();
        }
        return mc_this;
    }*/

    public BannerStarting() {
        SetTimePeriodMiliSecund(144);
    }

    public BannerStarting(int _timePeriodMiliSecund) {
        SetTimePeriodMiliSecund(_timePeriodMiliSecund);
    }

    @Override
    public void run() {

        while (m_isRun) {

            SystemClock.sleep(m_timePeriodMiliSecund);
            YovoSDK.ShowLog("BannerStartAwake", "FING");
            m_isRun = !ScenarioBanner.getInstance().Starting();
        }

        YovoSDK.ShowLog("BannerStartAwake", "STOPING");
        ScenarioBanner.getInstance().m_threadBannerStartAwake = null;
    }

    private void SetTimePeriodMiliSecund(int _timePeriodMiliSecund) {
        m_timePeriodMiliSecund = _timePeriodMiliSecund;
    }
}
