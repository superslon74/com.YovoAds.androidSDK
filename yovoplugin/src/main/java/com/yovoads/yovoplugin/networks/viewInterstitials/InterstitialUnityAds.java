package com.yovoads.yovoplugin.networks.viewInterstitials;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.IAdUnitOnMethod;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;

public class InterstitialUnityAds extends ExampleInterstitial
{
    private IAdUnitOnMethod m_callback = null;
    //private InterstitialAd    m_interstitial;

    public InterstitialUnityAds (IAdUnitOnMethod _callback, String _adUnitId)
    {
        m_callback = _callback;
        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._NONE;
        YovoSDK.ShowLog("InterstitialUnityAds","init");
        Create(_adUnitId);
    }

    @Override
    public void Create(final String _adUnitId) {

        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void LoadOther()
    {
        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                //me_isAdUnitlLoadingResult = EAdUnitLoadingResult._LOADING;
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
        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {

            }
        });
    }

}
