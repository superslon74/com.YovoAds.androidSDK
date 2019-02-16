package com.yovoads.yovoplugin.networks.viewRewards;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.IAdUnitOnMethodReward;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;
import com.yovoads.yovoplugin.implementations.reward.YReward;


public class RewardYovoAdvertising extends ExampleReward
{
    private IAdUnitOnMethodReward m_callback = null;
    private YReward m_reward;

    public RewardYovoAdvertising (IAdUnitOnMethodReward _callback, String _empty)
    {
        m_callback = _callback;
        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._NONE;
        YovoSDK.ShowLog("RewardYovoAdvertising", "init");
        Create("");
    }

    @Override
    public void Create(final String _adUnitId) {

        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                m_reward = new YReward(EAdNetworkType._YOVO_ADVERTISING, new IAdUnitOnMethodReward() {

                    @Override
                    public void OnSetLoadingAdUnitId(String _adUnitId)
                    {
                        m_callback.OnSetLoadingAdUnitId(_adUnitId);
                    }

                    @Override
                    public void OnAdLoaded() {
                        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._READY;
                        m_callback.OnAdLoaded();
                    }

                    @Override
                    public void OnAdFailedToLoad(String _errorCode) {
                        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._FAILED;
                        m_callback.OnAdFailedToLoad(String.valueOf(_errorCode));
                    }

                    @Override
                    public void OnAdShowing() {
                        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._SHOWING_NOW;
                        m_callback.OnAdShowing();
                    }

                    @Override
                    public void OnAdClicked() {
                        m_callback.OnAdClicked();
                    }

                    @Override
                    public void OnAdClosed() {
                        m_callback.OnAdClosed();
                    }

                    @Override
                    public void OnAdRewarded() {
                        m_callback.OnAdRewarded();
                    }

                    @Override
                    public void OnAdCompleted() {

                    }

                    @Override
                    public void OnAdDestroy()
                    {
                        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._NONE;
                        m_callback.OnAdDestroy();
                    }

                });

                //m_reward.RunProcess(1);
            }
        });
    }

    @Override
    public void LoadOther()
    {
        // empty
    }

    @Override
    public void LoadYovo(final int _idRule) {
        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._LOADING;
        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                m_reward.Load(_idRule);
            }
        });
    }

    @Override
    public void Show()
    {
        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                m_reward.Show();
            }
        });
    }

}