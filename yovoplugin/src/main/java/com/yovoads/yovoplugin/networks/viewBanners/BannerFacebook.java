package com.yovoads.yovoplugin.networks.viewBanners;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.IAdUnitOnMethod;
import com.yovoads.yovoplugin.common.EGravity;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;
import com.yovoads.yovoplugin.common.EAdUnitPrice;
import com.yovoads.yovoplugin.core.YTimer;

public class BannerFacebook extends ExampleBanner
{
    private BannerFacebook mc_this = null;
    private IAdUnitOnMethod   m_callback = null;
    FrameLayout m_adViewContainer = null;
    private AdView m_adView = null;
    private FrameLayout.LayoutParams m_params = null;

    public BannerFacebook (IAdUnitOnMethod _callback, String _adUnitId, EGravity _eGravity, int _showTime)
    {
        m_callback = _callback;
        SetShowTimeMax(_showTime);
        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._NONE;
        //YovoSDK.ShowLog("BannerFacebook", "init");
        Create(_adUnitId, _eGravity);
        mc_this = this;
    }

    @Override
    public void Create(final String _adUnitId, final EGravity _eGravity)
    {
        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                m_adViewContainer = new FrameLayout(DI.m_activity);

                m_params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                m_params.gravity = GetConvertionGravity(_eGravity);;

                AdSize adSize = (DI._IS_BANNER_HEIGHT_DOUBLE == 50) ? AdSize.BANNER_HEIGHT_50 : AdSize.BANNER_HEIGHT_90;
                m_adView = new AdView(DI.m_activity, _adUnitId, adSize);

                m_adViewContainer.addView(m_adView);
                m_adViewContainer.setVisibility(View.GONE);

                DI.m_activity.addContentView(m_adViewContainer, m_params);

                m_adView.setAdListener(new AdListener() {
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
                });
            }
        });
    }

    private int GetConvertionGravity(EGravity _eGravity)
    {
        switch (_eGravity)
        {
            case _TOP_LEFT:
                return Gravity.TOP + Gravity.LEFT;
            case _TOP:
                return Gravity.TOP + Gravity.CENTER_HORIZONTAL;
            case _TOP_RIGHT:
                return Gravity.TOP + Gravity.RIGHT;
            case _BOTTON_LEFT:
                return Gravity.BOTTOM + Gravity.LEFT;
            case _BOTTON:
                return Gravity.BOTTOM + Gravity.CENTER_HORIZONTAL;
            case _BOTTON_RIGHT:
                return Gravity.BOTTOM + Gravity.RIGHT;
        }
        return Gravity.BOTTOM;
    }

    @Override
    public void SetGravity(final EGravity _eGravity)
    {
        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if(m_adViewContainer != null)
                {
                    m_params.gravity = GetConvertionGravity(_eGravity);
                    m_adViewContainer.setLayoutParams(m_params);
                }
            }
        });
    }

    @Override
    public void LoadOther()
    {
        YovoSDK.ShowLog("BannerFacebook", "Load");
        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if(m_adView != null)
                {
                    me_isAdUnitlLoadingResult = EAdUnitLoadingResult._LOADING;
                    m_adView.loadAd();
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
        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if(m_adViewContainer != null)
                {
                    //YovoSDK.ShowLog("BannerFacebook", "Show");
                    m_adViewContainer.setVisibility(View.VISIBLE);
                    YTimer.getInstance().BannerStartingTimer(mc_this);
                }
            }
        });
    }

    @Override
    public void Hide()
    {
        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if(m_adViewContainer != null)
                {
                    m_adViewContainer.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean SetPause(boolean _isPause) {
        if (_isPause) {
            if (GetShowTimeCur() > 0) {
                if (YTimer.getInstance().BannerSetPause(_isPause)) {
                    YovoSDK.ShowLog("BannerFacebook", "SetPause= " + GetShowTimeCur());
                    return true;
                }
            }
            return false;
        } else {
            DI.m_activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    m_adViewContainer.setVisibility(View.VISIBLE);
                    YTimer.getInstance().BannerSetPause(false);
                }
            });
        }

        return false;
    }

    @Override
    public void OnAdDestroy() {
        m_callback.OnAdDestroy();
    }



}
