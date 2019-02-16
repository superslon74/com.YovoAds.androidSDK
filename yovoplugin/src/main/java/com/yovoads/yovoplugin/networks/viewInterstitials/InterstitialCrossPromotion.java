package com.yovoads.yovoplugin.networks.viewInterstitials;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.IAdUnitOnMethod;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;
import com.yovoads.yovoplugin.implementations.interstitial.YInterstitial;


public class InterstitialCrossPromotion extends ExampleInterstitial
{
    private IAdUnitOnMethod m_callback = null;

    private YInterstitial m_interstitial;

    public InterstitialCrossPromotion (IAdUnitOnMethod _callback, String _empty)
    {
        m_callback = _callback;
        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._NONE;
        YovoSDK.ShowLog("InterstitialCrossPromotion","init");
        Create(_empty);
    }

    @Override
    public void Create(final String _adUnitId) {

        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                m_interstitial = new YInterstitial(EAdNetworkType._CROSS_PROMOTION, new IAdUnitOnMethod() {

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
                    public void OnAdDestroy()
                    {
                        m_callback.OnAdDestroy();
                    }

                });
            }
        });
    }

    @Override
    public void LoadOther()
    {
        // empty
    }

    @Override
    public void LoadYovo(final int _idRule)
    {
        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._LOADING;
        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                m_interstitial.Load(_idRule);
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
                m_interstitial.Show();
            }
        });
    }

}
