package com.yovoads.yovoplugin.networks.viewRewards;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.IAdUnitOnMethodReward;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;

public class RewardAdmob extends ExampleReward
{
    private IAdUnitOnMethodReward m_callback = null;

    private RewardedVideoAd m_reward;
    private String  m_adUnitId;

    public RewardAdmob (IAdUnitOnMethodReward _callback, String _adUnitId)
    {
        m_callback = _callback;
        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._NONE;
        YovoSDK.ShowLog("RewardAdmob", "init");
        m_adUnitId = _adUnitId;
        Create("");
    }

    @Override
    public void Create(final String _adUnitId) {

        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                m_reward = MobileAds.getRewardedVideoAdInstance(DI.m_activity);
                m_reward.setRewardedVideoAdListener(new RewardedVideoAdListener() {
                    @Override
                    public void onRewardedVideoAdLoaded() {
                        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._READY;
                        m_callback.OnAdLoaded();
                    }

                    @Override
                    public void onRewardedVideoAdOpened() {
                        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._SHOWING_NOW;
                        m_callback.OnAdShowing();
                    }

                    @Override
                    public void onRewardedVideoAdFailedToLoad(int _errorCode) {
                        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._FAILED;
                        m_callback.OnAdFailedToLoad(String.valueOf(_errorCode));
                    }

                    @Override
                    public void onRewardedVideoStarted() {
                        //m_callback.OnAdStarted();
                    }

                    @Override
                    public void onRewardedVideoAdLeftApplication() {
                        m_callback.OnAdClicked();
                    }

                    @Override
                    public void onRewardedVideoAdClosed() {
                        m_callback.OnAdClosed();
                    }
                    @Override
                    public void onRewarded(RewardItem reward) {
                        m_callback.OnAdRewarded();
                    }

                    @Override
                    public void onRewardedVideoCompleted() {
                        m_callback.OnAdCompleted();
                    }
                });
            }
        });
    }

    @Override
    public void LoadOther()
    {
        YovoSDK.ShowLog("RewardAdmob", "Load");
        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if (!m_reward.isLoaded())
                {
                    me_isAdUnitlLoadingResult = EAdUnitLoadingResult._LOADING;
                    m_reward.loadAd(m_adUnitId, new AdRequest.Builder().build());
                }
            }
        });
    }

    @Override
    public void LoadYovo(final int _empty)
    {
        // empty
    }

    @Override
    public void Show()
    {
        YovoSDK.ShowLog("RewardAdmob", "Show");
        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if(m_reward.isLoaded())
                {
                    m_reward.show();
                }
            }
        });
    }

}
