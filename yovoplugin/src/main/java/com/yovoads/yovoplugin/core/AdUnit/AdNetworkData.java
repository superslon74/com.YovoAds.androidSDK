package com.yovoads.yovoplugin.core.AdUnit;

import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.networks.Admob;
import com.yovoads.yovoplugin.networks.Facebook;
import com.yovoads.yovoplugin.networks.UnityAds;

public class AdNetworkData {

    private static AdNetworkData mc_this = null;

    public boolean m_isAdmob = false;
    public String m_admobAppId = "";
    public boolean m_isFacebook = false;
    public String m_facebookAppId = "";
    public boolean m_isUnityAds = false;
    public String m_unityGameId = "";
    public boolean m_isYovoAdvertising = false;
    public boolean m_isCrossPromotion = false;
    public boolean m_isExchange = false;

    private String[] mm_adUnitAdmobBanner = new String[3];
    private String[] mm_adUnitAdmobInterstitial = new String[3];
    private String[] mm_adUnitAdmobReward = new String[1];

    private String[] mm_adUnitFacebookBanner = new String[3];
    private String[] mm_adUnitFacebookInterstitial = new String[3];
    private String[] mm_adUnitFacebookReward = new String[1];


    public static AdNetworkData getInstance() {
        if(mc_this == null) {
            mc_this = new AdNetworkData();
        }

        return mc_this;
    }

    private AdNetworkData() {

    }

    private int m_idRnd = 99;
    public int CreateIdForAdUnit() {
        m_idRnd++;
        return m_idRnd;
    }

    public void SetParams(boolean _isAdmob, boolean _isFacebook, boolean _isUnityAds, boolean _isCrossPromotion, boolean _isExchange, boolean _isYovoAdvertising) {
        m_isAdmob = _isAdmob;
        m_isFacebook = _isFacebook;
        m_isUnityAds = _isUnityAds;
        m_isCrossPromotion = _isCrossPromotion;
        m_isExchange = _isExchange;
        m_isYovoAdvertising = _isYovoAdvertising;
    }

    public void AddAdmob(String _admobAppId, String _admobBannerLow, String _admobBannerMedium, String _admobBannerHigh, String _admobInterstitialLow, String _admobInterstitialMedium, String _admobInterstitialHigh, String _admobRewardLow) {

        m_admobAppId = _admobAppId;
        Admob.AddNetwork(m_admobAppId);
        mm_adUnitAdmobBanner[0] = _admobBannerLow;
        mm_adUnitAdmobBanner[1] = _admobBannerMedium;
        mm_adUnitAdmobBanner[2] = _admobBannerHigh;
        mm_adUnitAdmobInterstitial[0] = _admobInterstitialLow;
        mm_adUnitAdmobInterstitial[1] = _admobInterstitialMedium;
        mm_adUnitAdmobInterstitial[2] = _admobInterstitialHigh;
        mm_adUnitAdmobReward[0] = _admobRewardLow;
    }

    public void AddFacebook(String _fbkAppId, String _fbBannerLow, String _fbBannerMedium, String _fbBannerHigh, String _fbInterstitialLow, String _fbInterstitialMedium, String _fbInterstitialHigh, String _fbRewardLow) {

        m_facebookAppId = _fbkAppId;
        Facebook.AddNetwork(m_facebookAppId);
        mm_adUnitFacebookBanner[0] = _fbBannerLow;
        mm_adUnitFacebookBanner[1] = _fbBannerMedium;
        mm_adUnitFacebookBanner[2] = _fbBannerHigh;
        mm_adUnitFacebookInterstitial[0] = _fbInterstitialLow;
        mm_adUnitFacebookInterstitial[1] = _fbInterstitialMedium;
        mm_adUnitFacebookInterstitial[2] = _fbInterstitialHigh;
        mm_adUnitFacebookReward[0] = _fbRewardLow;
    }

    public void AddUnityAds(String _unityGameId) {
        m_unityGameId = _unityGameId;
        UnityAds.AddNetwork(m_unityGameId);
    }

    public String[] GetAdUnitsBannerAdmob() {
        return mm_adUnitAdmobBanner;
    }

    public String[] GetAdUnitsInterstitialAdmob() {
        return mm_adUnitAdmobInterstitial;
    }

    public String[] GetAdUnitsRewardAdmob() {
        return mm_adUnitAdmobReward;
    }

    public String[] GetAdUnitsBannerFacebook() {
        return mm_adUnitFacebookBanner;
    }

    public String[] GetAdUnitsInterstitialFacebook() {
        return mm_adUnitFacebookInterstitial;
    }

    public String[] GetAdUnitsRewardFacebook() {
        return mm_adUnitFacebookReward;
    }

    public String[] GetAdUnitsBannerUnityAds() {
        return new String[] {""};
    }

    public String[] GetAdUnitsInterstitialUnityAds() {
        return new String[] {""};
    }

    public String[] GetAdUnitsRewardUnityAds() {
        return new String[] {""};
    }

    public String[] GetAdUnitsBannerCrossPromo() {
        return new String[] {""};
    }

    public String[] GetAdUnitsInterstitialCrossPromo() {
        return new String[] {""};
    }

    public String[] GetAdUnitsRewardCrossPromo() {
        return new String[] {""};
    }

    public String[] GetAdUnitsBannerExchange() {
        return new String[] {""};
    }

    public String[] GetAdUnitsInterstitialExchange() {
        return new String[] {""};
    }

    public String[] GetAdUnitsRewardExchange() {
        return new String[] {""};
    }

    public String[] GetAdUnitsBannerYovoAdvertising() {
        return new String[] {""};
    }

    public String[] GetAdUnitsInterstitialYovoAdvertising() {
        return new String[] {""};
    }

    public String[] GetAdUnitsRewardYovoAdvertising() {
        return new String[] {""};
    }


}
