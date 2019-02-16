package com.yovoads.yovoplugin.networks.viewBanners;

import android.view.View;

import com.unity3d.services.banners.IUnityBannerListener;
import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.common.IAdUnitOnMethod;
import com.yovoads.yovoplugin.common.EGravity;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;
import com.yovoads.yovoplugin.common.EAdUnitPrice;
import com.yovoads.yovoplugin.core.YTimer;
import com.yovoads.yovoplugin.networks.UnityAdsListener;
import com.yovoads.yovoplugin.YovoSDK;


public class BannerUnityAds extends ExampleBanner
{
    private BannerUnityAds mc_this = null;
    private IAdUnitOnMethod   m_callback = null;

    public static View m_banner;
    public static IAdUnitOnMethod m_callbackStatic;
    private static String m_placementId;
    private static UnityAdsListener m_unityAdsListener;
    private IUnityBannerListener m_unityBannerListener;
    public static BannerUnityAds m_bannerUnityAds;

    public BannerUnityAds (IAdUnitOnMethod _callback, String _placementId, EGravity _eGravity, int _showTime)
    {
        m_callback = _callback;
        SetShowTimeMax(_showTime);
        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._NONE;
        m_bannerUnityAds = this;
        m_callbackStatic = _callback;
        m_placementId = _placementId;
        YovoSDK.ShowLog("BannerUnityAds", "init");
        Create("", _eGravity);
        mc_this = this;
    }

    @Override
    public void Create(final String _placementId, final EGravity _eGravity) {

        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                m_unityAdsListener = new UnityAdsListener(null, "BANNER_CONST");
               // if(!com.unity3d.ads.UnityAds.isInitialized()) {
                com.unity3d.ads.UnityAds.setDebugMode(true);
                com.unity3d.ads.UnityAds.setListener(m_unityAdsListener);

                //UnityBanners.loadBanner();
                    //com.unity3d.ads.UnityAds.initialize(YovoSDK.m_activity, com.yovoads.yovoplugin.Main.UnityAds.m_gameId, m_unityAdsListener, YovoSDK.m_isTesting);
                //com.unity3d.ads.UnityAds.initialize(YovoSDK.m_activity, com.yovoads.yovoplugin.Main.UnityAds.m_gameId, m_unityAdsListener, YovoSDK.m_isTesting);
                //UnityAdsImplementation.initialize(YovoSDK.m_activity, com.yovoads.yovoplugin.Main.UnityAds.m_gameId, m_unityAdsListener, YovoSDK.m_isTesting);
                //}
//                m_unityBannerListener = new UnityBannerListener ();
//                UnityBanners.setBannerListener (m_unityBannerListener);

            }
        });
    }

    @Override
    public void SetGravity(final EGravity _eGravity)
    {

    }

    @Override
    public void LoadOther()
    {
//        YovoSDK.m_activity.runOnUiThread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                //if (m_banner == null) {
//                    YovoSDK.ShowLog("BannerUnityAds", "Load");
//                    UnityBanners.loadBanner(YovoSDK.m_activity, m_placementId);
//                //}
//            }
//        });
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
                //if(m_banner != null) {
                YovoSDK.ShowLog("BannerUnityAds", "Show");
                m_banner.setVisibility(View.VISIBLE);
                YTimer.getInstance().BannerStartingTimer(mc_this);
//                }
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
//                if(m_banner != null) {
                    YovoSDK.ShowLog("BannerUnityAds", "Hide");
                    m_banner.setVisibility(View.GONE);
//                }
            }
        });
    }

    @Override
    public boolean SetPause(boolean _isPause) {
        if (_isPause) {
            if (GetShowTimeCur() > 0) {
                if (YTimer.getInstance().BannerSetPause(_isPause)) {
                    YovoSDK.ShowLog("BannerUnityAds", "SetPause= " + GetShowTimeCur());
                    return true;
                }
            }
            return false;
        } else {
            DI.m_activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    m_banner.setVisibility(View.VISIBLE);
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

class UnityBannerListener implements IUnityBannerListener {

    @Override
    public void onUnityBannerLoaded (String placementId, View view) {
        YovoSDK.ShowLog("UnityBannerListener-onUnityBannerLoaded", placementId);
        BannerUnityAds.m_banner = view;
        //((ViewGroup) findViewById (R.id.unityads_example_layout_root)).addView (view);
        BannerUnityAds.m_callbackStatic.OnAdLoaded();
    }

    @Override
    public void onUnityBannerUnloaded (String placementId) {
        YovoSDK.ShowLog("UnityBannerListener-onUnityBannerUnloaded", placementId);
        BannerUnityAds.m_banner = null;
        BannerUnityAds.m_callbackStatic.OnAdClosed();
    }

    @Override
    public void onUnityBannerShow (String placementId)
    {
        YovoSDK.ShowLog("UnityBannerListener-onUnityBannerShow", placementId);
        BannerUnityAds.m_callbackStatic.OnAdShowing();
    }

    @Override
    public void onUnityBannerClick (String placementId) {
        YovoSDK.ShowLog("UnityBannerListener-onUnityBannerClick", placementId);
        BannerUnityAds.m_callbackStatic.OnAdClicked();
    }

    @Override
    public void onUnityBannerHide (String placementId) {
        YovoSDK.ShowLog("UnityBannerListener-onUnityBannerHide", placementId);
        BannerUnityAds.m_callbackStatic.OnAdClosed();
    }

    @Override
    public void onUnityBannerError (String message) {
        YovoSDK.ShowLog("UnityBannerListener-onUnityBannerError", message);
        BannerUnityAds.m_callbackStatic.OnAdFailedToLoad("error load");
    }
}
