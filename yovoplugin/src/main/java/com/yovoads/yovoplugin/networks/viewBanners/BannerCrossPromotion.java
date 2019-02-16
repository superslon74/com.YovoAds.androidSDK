package com.yovoads.yovoplugin.networks.viewBanners;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.IAdUnitOnMethod;
import com.yovoads.yovoplugin.common.EGravity;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;
import com.yovoads.yovoplugin.core.YTimer;
import com.yovoads.yovoplugin.implementations.banner.YBanner;

public class BannerCrossPromotion extends ExampleBanner
{
    private BannerCrossPromotion mc_this = null;
    private IAdUnitOnMethod   m_callback = null;
    private YBanner m_banner = null;


    public BannerCrossPromotion(IAdUnitOnMethod _callback, String _empty, EGravity _eGravity, int _showTime)
    {
        m_callback = _callback;
        SetShowTimeMax(_showTime);
        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._NONE;
        YovoSDK.ShowLog("BannerCrossPromotion", "init");
        Create("", _eGravity);
        mc_this = this;
    }

    @Override
    public void Create(final String _adUnitId, final EGravity _eGravity)
    {
        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                m_banner = new YBanner(EAdNetworkType._CROSS_PROMOTION, new IAdUnitOnMethod() {

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
                        //m_callback.OnAdClosed();
                    }

                    @Override
                    public void OnAdDestroy()
                    {
                        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._NONE;
                        m_callback.OnAdDestroy();
                    }

                });
            }
        });
    }

    @Override
    public void SetGravity(final EGravity _eGravity)
    {
        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if(m_banner != null)
                {
                    m_banner.SetGravity(_eGravity);
                }
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

        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                me_isAdUnitlLoadingResult = EAdUnitLoadingResult._LOADING;
                m_banner.Load(_idRule);
            }
        });
    }

    @Override
    public void Show() {
        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                m_banner.Show();
                YTimer.getInstance().BannerStartingTimer(mc_this);
            }
        });
    }

    @Override
    public void Hide() {
        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (m_banner != null) {
                    m_banner.Hide();
                }
            }
        });
    }

    @Override
    public boolean SetPause(boolean _isPause) {
        if (_isPause) {
            if (GetShowTimeCur() > 0) {
                if (YTimer.getInstance().BannerSetPause(_isPause)) {
                    YovoSDK.ShowLog("BannerCrossPromotion", "SetPause= " + GetShowTimeCur());
                    return true;
                }
            }
            return false;
        } else {
            DI.m_activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    m_banner.Show();
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
