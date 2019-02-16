package com.yovoads.yovoplugin.networks.viewInterstitials;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.IAdUnitOnMethod;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;


public class InterstitialAdmob extends ExampleInterstitial {
    private IAdUnitOnMethod m_callback = null;
    private InterstitialAd m_interstitial;

    public InterstitialAdmob(IAdUnitOnMethod _callback, String _adUnitId) {
        m_callback = _callback;
        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._NONE;
        YovoSDK.ShowLog("InterstitialAdmob", "init");
        Create(_adUnitId);
    }

    @Override
    public void Create(final String _adUnitId) {

        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                m_interstitial = new InterstitialAd(DI.m_activity);
                m_interstitial.setAdUnitId(_adUnitId);
                m_interstitial.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._READY;
                        m_callback.OnAdLoaded();
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._FAILED;
                        m_callback.OnAdFailedToLoad(String.valueOf(errorCode));
                    }

                    @Override
                    public void onAdImpression() {
//                        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._SHOWING_NOW;
//                        m_callback.OnAdShowing();
                    }

                    @Override
                    public void onAdOpened() {
                        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._SHOWING_NOW;
                        m_callback.OnAdShowing();
                    }

                    @Override
                    public void onAdLeftApplication() {
                        m_callback.OnAdClicked();
                    }

                    @Override
                    public void onAdClosed() {
                        m_callback.OnAdClosed();
                        m_callback.OnAdDestroy();
                    }
                });
            }
        });
    }

    @Override
    public void LoadOther() {
        //YovoSDK.ShowLog("InterstitialAdmob", "Load");
        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!m_interstitial.isLoading() && !m_interstitial.isLoaded()) {
                    me_isAdUnitlLoadingResult = EAdUnitLoadingResult._LOADING;
                    m_interstitial.loadAd(new AdRequest.Builder().build());
                }
            }
        });
    }

    @Override
    public void LoadYovo(final int _empty) {
        // empty
    }

    @Override
    public void Show() {
        //YovoSDK.ShowLog("InterstitialAdmob", "Show");
        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (m_interstitial != null && m_interstitial.isLoaded()) {
                    m_interstitial.show();
                }
            }
        });
    }

}
