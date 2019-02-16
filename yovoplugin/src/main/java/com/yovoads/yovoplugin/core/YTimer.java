package com.yovoads.yovoplugin.core;

import android.os.SystemClock;

import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.core.AdUnit.Rewards;
import com.yovoads.yovoplugin.core.Scenario.ScenarioBanner;
import com.yovoads.yovoplugin.core.Scenario.ScenarioInterstitial;
import com.yovoads.yovoplugin.core.Scenario.ScenarioReward;
import com.yovoads.yovoplugin.core.www.WWWRequest;
import com.yovoads.yovoplugin.common.dbLocal;
import com.yovoads.yovoplugin.networks.viewBanners.ExampleBanner;


public class YTimer implements Runnable
{
    private static YTimer mc_this = new YTimer();
    private YTimer() {
        m_sessionSendPeriod = dbLocal.getInstance().GetSessionPeriod() - 1;
    }

    public static YTimer getInstance() {
        return mc_this;
    }

    private long m_secMax = 1000;
    public int m_secActive = 0;


    private int m_sessionTime = 0;
    public int m_sessionSendPeriod = 13;


    private int m_loadBannerTimer = 13;
    public int m_loadInterstitilalTimer = 13;
    public int m_loadRewardTimer = 13;


    private boolean m_isRun = true;
    private boolean m_isAppPause = false;
    private boolean m_isStopping = true;

    private boolean m_isBannerPause = true;
    private ExampleBanner m_bannerExample = null;

    @Override
    public void run() {

        while (m_isRun)
        {
            if(m_secMax < SystemClock.currentThreadTimeMillis())
            {
                m_secMax += 1000;
                if(!m_isAppPause) {
                    m_secActive++;
                    //YovoSDK.ShowLog("TIMER ACTIVE", String.valueOf(m_secActive));

                    Rewards.m_rewardNextShowAvailable--;
                    YovoSDK.ShowLog("------>", String.valueOf(Rewards.m_rewardNextShowAvailable));

                    if(m_secActive - m_sessionTime > m_sessionSendPeriod) {
                        m_sessionTime += m_sessionSendPeriod + 1;
                        WWWRequest.getInstance().SendSessionTime(m_sessionTime);
                    }


                    m_loadBannerTimer--;
                    if(m_loadBannerTimer < 1) {
                        RunLoadingAdUnitBanner();
                    }

                    m_loadInterstitilalTimer--;
                    if(m_loadInterstitilalTimer < 1) {
                        RunLoadingAdUnitInterstitial();
                    }

                    m_loadRewardTimer--;
                    if(m_loadRewardTimer < 1) {
                        RunLoadingAdUnitReward();
                    }


                    if(!m_isBannerPause && m_bannerExample != null) {
                        YovoSDK.ShowLog("YTimer", String.valueOf(m_bannerExample.GetShowTimeCur() - 1));
                        if (m_bannerExample.TimeTick() < 1) {
                            m_bannerExample.SetShowTimeEnd();
                            m_isBannerPause = true;
                            m_bannerExample.OnAdDestroy();
                            m_bannerExample = null;
                        }
                    }


                }
            }
        }
    }

    public void SetSessionPeriod(int _sessionSendPeriod) {
        m_sessionSendPeriod = _sessionSendPeriod - 1;
    }

    public void AppQuit() {
        WWWRequest.getInstance().SendSessionTime((m_sessionTime + ((int)m_secActive - m_sessionTime)));
    }



    public void RunLoadingAdUnitBanner()
    {
        m_loadBannerTimer = 34;
        ScenarioBanner.getInstance().RunLoadingAdUnit();
    }

    public void RunLoadingAdUnitInterstitial()
    {
        m_loadInterstitilalTimer = 44;
        ScenarioInterstitial.getInstance().RunLoadingAdUnit();
    }

    public void RunLoadingAdUnitReward()
    {
        m_loadRewardTimer = 55;
        ScenarioReward.getInstance().RunLoadingAdUnit();
    }






    public void BannerStartingTimer(ExampleBanner _bannerExample)
    {
        m_bannerExample = _bannerExample;
        m_bannerExample.ResetTimeCur();
        m_isBannerPause = false;
    }

    public boolean BannerSetPause(boolean _isPause) {
        if(m_bannerExample != null) {
            m_isBannerPause = _isPause;
        }
        return m_isBannerPause;
    }




    public void SetAppPause(boolean _isAppPause)
    {
        m_isAppPause = _isAppPause;
    }





}
