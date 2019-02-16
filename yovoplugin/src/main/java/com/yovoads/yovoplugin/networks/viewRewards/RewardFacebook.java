package com.yovoads.yovoplugin.networks.viewRewards;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.IAdUnitOnMethodReward;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;

public class RewardFacebook extends ExampleReward
{
    private IAdUnitOnMethodReward m_callback = null;

    private RewardedVideoAd m_reward;
    public RewardFacebook (IAdUnitOnMethodReward _callback, String _adUnitId)
    {
        m_callback = _callback;
        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._NONE;
        YovoSDK.ShowLog("RewardFacebook", "init");
        Create(_adUnitId);
    }

    @Override
    public void Create(final String _adUnitId) {

        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                m_reward = new RewardedVideoAd(DI.m_activity, _adUnitId);

                m_reward.setAdListener(new RewardedVideoAdListener() {
                    @Override
                    public void onAdLoaded(Ad ad) {
                        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._READY;
                        m_callback.OnAdLoaded();
                    }

                    @Override
                    public void onError(Ad ad, AdError adError) {
                        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._FAILED;
                        m_callback.OnAdFailedToLoad(adError.getErrorMessage());
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._SHOWING_NOW;
                        m_callback.OnAdShowing();
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        m_callback.OnAdClicked();
                    }

                    @Override
                    public void onRewardedVideoClosed() {
                        m_callback.OnAdClosed();
                    }

                    @Override
                    public void onRewardedVideoCompleted() {
                        m_callback.OnAdRewarded();
                    }

                });
            }
        });
    }

    @Override
    public void LoadOther()
    {
        YovoSDK.ShowLog("RewardFacebook", "Load");
        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if (!m_reward.isAdLoaded())
                {
                    me_isAdUnitlLoadingResult = EAdUnitLoadingResult._LOADING;
                    m_reward.loadAd();
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
        YovoSDK.ShowLog("RewardFacebook", "Show");
        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run() {

                m_reward.isAdInvalidated();
                m_reward.show();

            }
        });
    }

}
