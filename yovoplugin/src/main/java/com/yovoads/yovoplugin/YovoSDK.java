package com.yovoads.yovoplugin;

import android.app.Activity;
import android.util.Log;

import com.yovoads.yovoplugin.common.EGravity;
import com.yovoads.yovoplugin.core.AdUnit.AdNetworkData;
import com.yovoads.yovoplugin.core.AdUnit.Banners;
import com.yovoads.yovoplugin.core.AdUnit.Interstitials;
import com.yovoads.yovoplugin.core.AdUnit.Rewards;
import com.yovoads.yovoplugin.core.Scenario.ScenarioBanner;
import com.yovoads.yovoplugin.core.Scenario.ScenarioInterstitial;
import com.yovoads.yovoplugin.core.Scenario.ScenarioReward;
import com.yovoads.yovoplugin.core.YTimer;
import com.yovoads.yovoplugin.core.www.WWWRequest;
import com.yovoads.yovoplugin.core.dbLocal;
import com.yovoads.yovoplugin.implementations.reward.YRewardLoader;
import com.yovoads.yovoplugin.implementations.banner.YFragments;


public class YovoSDK {

    private static YovoSDK mc_this = null;
    private Thread m_threadTimer;

    public static IOnUnitySDK mi_OnUnitySDK;

    public static YovoSDK getInstance(Activity _activity, IOnUnitySDK _iOnUnitySDK, String _yovoAdsAccountId, String _yovoAdsToken, String _packageName, boolean _isCOPPA, boolean _isTesting) {
        if (mc_this == null) {
            mc_this = new YovoSDK();
            mi_OnUnitySDK = _iOnUnitySDK;
            DI.getInstance().Set(_activity, _yovoAdsAccountId, _yovoAdsToken, _packageName, _isCOPPA, _isTesting);
            YRewardLoader.CreateFolder();
        }
        return mc_this;
    }

    public void SetGaid() {
        DI.getInstance().SetGaid();
    }

    private YovoSDK() {
        YovoSDK.ShowLog("YovoSDK", "getInstance");
    }

    public static void ShowLog(String _class, String _method) {
        String[] _blocking = new String[]{"Banner", "sjshdfkjsfhskdfsqoidsalkal"};
        for (String _block : _blocking) {
            if (_class.contains(_block)) {
                return;
            }
        }

        Log.e("YOVO_J:  " + _class, _method);
    }


    public void ScenarioInit(){
        ScenarioBanner.Init();
        ScenarioInterstitial.Init();
        ScenarioReward.Init();
    }

    public void SetNetworksParams(boolean _isAdmob, boolean _isFacebook, boolean _isUnityAds, boolean _isCrossPromotion, boolean _isExchange, boolean _isYovoAdvertising) {
        AdNetworkData.getInstance().SetParams(_isAdmob, _isFacebook, _isUnityAds, _isCrossPromotion, _isExchange, _isYovoAdvertising);
    }

    public void AddNetworkAdmobData(String _admobAppId, String _admobBannerLow, String _admobBannerMedium, String _admobBannerHigh,
                                    String _admobInterstitialLow, String _admobInterstitialMedium, String _admobInterstitialHigh, String _admobRewardLow) {
        AdNetworkData.getInstance().AddAdmob(_admobAppId, _admobBannerLow, _admobBannerMedium, _admobBannerHigh, _admobInterstitialLow, _admobInterstitialMedium, _admobInterstitialHigh, _admobRewardLow);
    }

    public void AddNetworkFacebookData(String _fbAppId, String _fbBannerLow, String _fbBannerMedium, String _fbBannerHigh,
                                       String _fbInterstitialLow, String _fbInterstitialMedium, String _fbInterstitialHigh, String _fbRewardLow) {
        AdNetworkData.getInstance().AddFacebook(_fbAppId, _fbBannerLow, _fbBannerMedium, _fbBannerHigh, _fbInterstitialLow, _fbInterstitialMedium, _fbInterstitialHigh, _fbRewardLow);
    }

    public void AddNetworkUnityAdsData(String _unityGameId) {
        AdNetworkData.getInstance().AddUnityAds(_unityGameId);
    }

    public void CreateBannerXml(boolean _startAwake, int _gravity, boolean _isBackgroundShow, String _color) {
        Banners.getInstance().m_gravity = EGravity.GetName(_gravity);
        Banners.getInstance().m_startAwake = _startAwake;

        YFragments.getInstance().CreateBannerXml(_isBackgroundShow, _color);
    }


    public void Start() {
        dbLocal.getInstance();

        m_threadTimer = new Thread(YTimer.getInstance());
        m_threadTimer.start();

        WWWRequest.getInstance().SendQuratorInit();
    }

    public void ApplicationFocus() {
        YTimer.getInstance().SetAppPause(false);
    }

    public void ApplicationPause() {
        YTimer.getInstance().SetAppPause(true);
    }


    public void BannerShow() {
        Banners.getInstance().Show();
    }

    public void BannerHide() {
        Banners.getInstance().Hide();
    }

    public void BannerSetGravity(final int _gravity) {

        Banners.getInstance().SetGravity(_gravity);
    }

    public void BannerSetBackground(boolean _isBackgroundShow) {
        Banners.getInstance().BannerSetBackground(_isBackgroundShow);
    }

    public void BannerSetBackgroundColor(String _color) {
        Banners.getInstance().BannerSetBackgroundColor(_color);
    }

    public float BannerGetHeight() {
        return DI.getInstance().GetBannerHeight_float();
    }


    public void InterstitialSetPeriodShow(int _periodShow) {
        Interstitials.getInstance().SetPeriodShow(_periodShow);
    }

    public void InterstitialShow() {
        Interstitials.getInstance().Show();
    }


    public void RewardShow(String _key, String _value) {
        Rewards.getInstance().Show(_key, _value);
    }

    public void RewardShowIgnore(String _key, String _value) {
        Rewards.getInstance().ShowIgnore(_key, _value);
    }


    public void ToastShow(int _showTime, String _message) {
        //mc_platform.Call("ToastShow", new object[] { _showTime, _message });
    }

    public void PopupsShow(String _callBackParams, String _title, String _mess, String _butYes) {
        //mc_platform.Call("ToastShow", new object[] { _callBackParams, _title, _mess, _butYes });
    }

    public void PopupsShow(String _callBackParams, String _title, String _mess, String _butYes, String _butNo) {
        //mc_platform.Call("ToastShow", new object[] { _callBackParams, _title, _mess, _butYes, _butNo });
    }

    public void PopupsShow(String _callBackParams, String _title, String _mess, String _butYes, String _butNo, String _butLaiter) {
        //mc_platform.Call("ToastShow", new object[] { _callBackParams, _title, _mess, _butYes, _butNo, _butLaiter });
    }


    public void AppTryQuit() {

    }

    public void AppQuit() {
        YTimer.getInstance().AppQuit();
        ScenarioBanner.getInstance().m_threadBannerStartAwake = null;
        m_threadTimer.interrupt();
        m_threadTimer = null;
    }

}
