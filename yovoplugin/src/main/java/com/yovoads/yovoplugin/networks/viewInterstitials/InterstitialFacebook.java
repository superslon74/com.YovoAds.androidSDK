package com.yovoads.yovoplugin.networks.viewInterstitials;


import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.IAdUnitOnMethod;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;

public class InterstitialFacebook extends ExampleInterstitial
{
    private IAdUnitOnMethod m_callback = null;
    private InterstitialAd m_interstitial;

    public InterstitialFacebook (IAdUnitOnMethod _callback, String _adUnitId)
    {
        m_callback = _callback;
        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._NONE;
        YovoSDK.ShowLog("InterstitialFacebook", "init");
        Create(_adUnitId);
    }

    @Override
    public void Create(final String _adUnitId) {

        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                m_interstitial = new InterstitialAd(DI.m_activity, _adUnitId);
                m_interstitial.setAdListener(new InterstitialAdListener() {
                    @Override
                    public void onInterstitialDisplayed(Ad ad) {
                        //m_callback.OnAdStarted();
                    }

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
                    public void onAdClicked(Ad ad) {
                        m_callback.OnAdClicked();
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._SHOWING_NOW;
                        m_callback.OnAdShowing();
                    }

                    @Override
                    public void onInterstitialDismissed(Ad ad) {
                        m_callback.OnAdClosed();
                        m_callback.OnAdDestroy();
                    }
                });
            }
        });
    }

    @Override
    public void LoadOther()
    {
        YovoSDK.ShowLog("InterstitialFacebook", "Load");
        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if (!m_interstitial.isAdLoaded())
                {
                    me_isAdUnitlLoadingResult = EAdUnitLoadingResult._LOADING;
                    m_interstitial.loadAd();
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
        YovoSDK.ShowLog("InterstitialFacebook", "Show");
        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad
                //if(!m_interstitial.isAdInvalidated()) {
                m_interstitial.isAdInvalidated();
                m_interstitial.show();
               // }
            }
        });
    }

}
