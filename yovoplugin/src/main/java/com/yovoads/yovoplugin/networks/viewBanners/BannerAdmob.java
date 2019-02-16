package com.yovoads.yovoplugin.networks.viewBanners;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.EScreenOrientation;
import com.yovoads.yovoplugin.common.IAdUnitOnMethod;
import com.yovoads.yovoplugin.common.EGravity;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;
import com.yovoads.yovoplugin.common.EAdUnitPrice;
import com.yovoads.yovoplugin.core.YTimer;


public class BannerAdmob extends ExampleBanner
{
    private BannerAdmob mc_this = null;
    private IAdUnitOnMethod   m_callback = null;
    private AdView m_adView = null;
    private FrameLayout.LayoutParams m_AdParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

    public BannerAdmob (IAdUnitOnMethod _callback, String _adUnitId, EGravity _eGravity, int _showTime)
    {
        m_callback = _callback;
        SetShowTimeMax(_showTime);
        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._NONE;
        //YovoSDK.ShowLog("BannerAdmob", "init");
        Create(_adUnitId, _eGravity);
        mc_this = this;
    }

    @Override
    public void Create(final String _adUnitId, final EGravity _eGravity) {

        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                m_adView = new AdView(DI.m_activity);
                m_adView.setAdSize((DI._SCREEN_ORIENTATION == EScreenOrientation._LANDSCAPE && DI._DISPLAY_HEIGHT_DPI < 401) ? AdSize.BANNER : AdSize.SMART_BANNER);
                m_adView.setAdUnitId(_adUnitId);
                m_AdParams.gravity = GetConvertionGravity(_eGravity);

                DI.m_activity.addContentView(m_adView, m_AdParams);
                Hide();

                m_adView.setAdListener(new AdListener() {
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
                        YovoSDK.ShowLog("BannerAdmob", "onAdImpression");
//                        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._SHOWING_NOW;
//                        m_callback.OnAdShowing();
                    }

                    @Override
                    public void onAdOpened() {

                    }

                    @Override
                    public void onAdLeftApplication() {
                        m_callback.OnAdClicked();
                    }

                    @Override
                    public void onAdClosed() {
                        //m_callback.OnAdClosed();
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
                if(m_adView != null)
                {
                    m_AdParams.gravity = GetConvertionGravity(_eGravity);
                    m_adView.setLayoutParams(m_AdParams);
                }
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
                if(m_adView != null)
                {
                    me_isAdUnitlLoadingResult = EAdUnitLoadingResult._LOADING;
                    m_adView.loadAd(new AdRequest.Builder().build());
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
                if(m_adView != null)
                {
                    //YovoSDK.ShowLog("BannerAdmob", "Show" );
                    m_adView.setVisibility(View.VISIBLE);
                    m_adView.resume();
                    me_isAdUnitlLoadingResult = EAdUnitLoadingResult._SHOWING_NOW;
                    m_callback.OnAdShowing();
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
                if(m_adView != null)
                {
                    m_adView.pause();
                    m_adView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean SetPause(boolean _isPause) {
        if (_isPause) {
            if (GetShowTimeCur() > 0) {
                if (YTimer.getInstance().BannerSetPause(_isPause)) {
                    YovoSDK.ShowLog("BannerAdmob", "SetPause= " + GetShowTimeCur());
                    return true;
                }
            }
            return false;
        } else {
            DI.m_activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    m_adView.setVisibility(View.VISIBLE);
                    m_adView.resume();
                    me_isAdUnitlLoadingResult = EAdUnitLoadingResult._SHOWING_NOW;
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
