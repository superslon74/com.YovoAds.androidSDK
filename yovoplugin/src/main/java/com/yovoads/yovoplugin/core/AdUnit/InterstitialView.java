package com.yovoads.yovoplugin.core.AdUnit;

import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.EAdUnitAdLoadingFailed;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;
import com.yovoads.yovoplugin.common.EAdUnitPrice;
import com.yovoads.yovoplugin.common.EAdUnitType;
import com.yovoads.yovoplugin.common.IAdUnitOnMethod;
import com.yovoads.yovoplugin.core.Scenario.NextRuleId;
import com.yovoads.yovoplugin.core.Scenario.ScenarioInterstitial;
import com.yovoads.yovoplugin.core.www.WWWRequest;
import com.yovoads.yovoplugin.networks.viewInterstitials.ExampleInterstitial;
import com.yovoads.yovoplugin.networks.viewInterstitials.InterstitialAdmob;
import com.yovoads.yovoplugin.networks.viewInterstitials.InterstitialCrossPromotion;
import com.yovoads.yovoplugin.networks.viewInterstitials.InterstitialExchange;
import com.yovoads.yovoplugin.networks.viewInterstitials.InterstitialFacebook;
import com.yovoads.yovoplugin.networks.viewInterstitials.InterstitialUnityAds;
import com.yovoads.yovoplugin.networks.viewInterstitials.InterstitialYovoAdvertising;

public class InterstitialView implements IAdUnitOnMethod {

    private ExampleInterstitial m_exampleAdUnit = null;
    private EAdNetworkType me_adNetworkType = null;
    private EAdUnitPrice me_adUnitPrice = null;

    private String m_adUnitId = "";
    private int m_idRule = 0;
////////////////////////////////////////////////////////////// ---  Init  --- //////////////////////////////////////////////////////////////

    private InterstitialView() {
    }

    public InterstitialView(EAdNetworkType _adNetworkType, String _adUnitId, EAdUnitPrice _adUnitPrice) {

        me_adUnitPrice = _adUnitPrice;
        me_adNetworkType = _adNetworkType;

        switch (me_adNetworkType)
        {
            case _ADMOB:
                m_exampleAdUnit = new InterstitialAdmob(this, _adUnitId);
                break;
            case _FACEBOOK:
                m_exampleAdUnit = new InterstitialFacebook(this, _adUnitId);
                break;
            case _UNITY_ADS:
                m_exampleAdUnit = new InterstitialUnityAds(this, _adUnitId);
                break;
            case _CROSS_PROMOTION:
                m_exampleAdUnit = new InterstitialCrossPromotion(this, _adUnitId);
                break;
            case _EXCHANGE:
                m_exampleAdUnit = new InterstitialExchange(this, _adUnitId);
                break;
            case _YOVO_ADVERTISING:
                m_exampleAdUnit = new InterstitialYovoAdvertising(this, _adUnitId);
                break;
        }

    }
////////////////////////////////////////////////////////////// ---  Method  --- //////////////////////////////////////////////////////////////

    public void LoadOther() {
        if(m_exampleAdUnit.me_isAdUnitlLoadingResult == EAdUnitLoadingResult._NONE || m_exampleAdUnit.me_isAdUnitlLoadingResult == EAdUnitLoadingResult._FAILED) {
            m_exampleAdUnit.LoadOther();
        }
    }

    public void LoadYovo(int _idRule) {
        if(m_exampleAdUnit.me_isAdUnitlLoadingResult == EAdUnitLoadingResult._NONE || m_exampleAdUnit.me_isAdUnitlLoadingResult == EAdUnitLoadingResult._FAILED) {
            m_exampleAdUnit.LoadYovo(_idRule);
        }
    }

    public EAdUnitLoadingResult IsAdUnitlLoadingResult() {
        return m_exampleAdUnit.me_isAdUnitlLoadingResult;
    }

    public boolean TryShow(int _idRule) {
        if(m_exampleAdUnit.me_isAdUnitlLoadingResult == EAdUnitLoadingResult._READY) {
            m_idRule = _idRule;
            Show();
            return true;
        }
        return false;
    }

    private void Show() {
        Interstitials.getInstance().mc_interstitialViewActive = (InterstitialView) this;
        m_exampleAdUnit.Show();
    }
////////////////////////////////////////////////////////////// ---  Callback --- //////////////////////////////////////////////////////////////

    @Override
    public void OnSetLoadingAdUnitId(String _adUnitId) {
        m_adUnitId = _adUnitId;
    }

    @Override
    public void OnAdLoaded() {
        //WWWRequest.getInstance().SendEventAdUnit(Enums.EventAdUnit.Loaded, me_networkType, Enums.AdUnitType.interstitial, me_adUnitPrice, 0);
        YovoSDK.ShowLog("ViewInterstitial", me_adNetworkType + " -> interstitial -> OnAdLoaded -> " + String.valueOf(me_adUnitPrice));
    }

    @Override
    public void OnAdFailedToLoad(String _errorReason) {
        //WWWRequest.getInstance().SendEventAdUnit(EEventAdUnit.LoadFailed, me_adNetworkType, EAdUnitType.interstitial, me_adUnitPrice, 0);
        YovoSDK.ShowLog("ViewInterstitial",me_adNetworkType + " -> interstitial -> OnAdFailedToLoad -> " + String.valueOf(me_adUnitPrice) + "  ERROR=" +_errorReason);
        WWWRequest.getInstance().SendEventLoadFailed(me_adNetworkType, EAdUnitType._INTERSTITIAL, me_adUnitPrice, m_adUnitId, EAdUnitAdLoadingFailed._ERROR_TEMP);
    }

    @Override
    public void OnAdShowing() {
       // WWWRequest.getInstance().SendEventAdUnit(EEventAdUnit.Show, me_adNetworkType, EAdUnitType.interstitial, me_adUnitPrice, 0);
        YovoSDK.ShowLog("ViewInterstitial",me_adNetworkType + " -> interstitial -> OnAdShowing -> " + String.valueOf(me_adUnitPrice));
        ScenarioInterstitial.getInstance().OnShowing(m_idRule);
        Interstitials.getInstance().SetTimeLastShowing();
        WWWRequest.getInstance().SendEventShowing(me_adNetworkType, EAdUnitType._INTERSTITIAL, me_adUnitPrice, m_idRule, m_adUnitId);
    }

    @Override
    public void OnAdClicked() {
        //WWWRequest.getInstance().SendEventAdUnit(EEventAdUnit.Click, me_adNetworkType, EAdUnitType.interstitial, me_adUnitPrice, 0);
        YovoSDK.ShowLog("ViewInterstitial",me_adNetworkType + " -> interstitial -> OnAdClick -> " + String.valueOf(me_adUnitPrice));
        WWWRequest.getInstance().SendEventClick(me_adNetworkType, EAdUnitType._INTERSTITIAL, me_adUnitPrice, m_idRule, m_adUnitId);
    }

    @Override
    public void OnAdClosed() {
        YovoSDK.ShowLog("ViewInterstitial",me_adNetworkType + " -> interstitial -> OnAdClosed -> " + String.valueOf(me_adUnitPrice));
    }

    @Override
    public void OnAdDestroy()
    {
        YovoSDK.ShowLog("ViewInterstitial", String.valueOf(me_adNetworkType) + " -> interstitial -> OnAdDestroy -> " + String.valueOf(me_adUnitPrice));
        m_exampleAdUnit.me_isAdUnitlLoadingResult = EAdUnitLoadingResult._NONE;
        Interstitials.getInstance().mc_interstitialViewActive = null;
        IsLoadAndShowThisAdUnitNow();
    }

    private void IsLoadAndShowThisAdUnitNow()
    {
        if(me_adNetworkType == EAdNetworkType._CROSS_PROMOTION || me_adNetworkType == EAdNetworkType._EXCHANGE || me_adNetworkType == EAdNetworkType._YOVO_ADVERTISING)
        {
            int _nextRuleId = ScenarioInterstitial.getInstance().GetNextAvailableRuleIdYovo(me_adNetworkType, m_idRule);
            if(_nextRuleId > 0) {
                LoadYovo(m_idRule);
            } else if(_nextRuleId == -99) {
                WWWRequest.getInstance().SendScenarioReset(EAdUnitType._INTERSTITIAL);
            }
        } else {
            if(!ScenarioInterstitial.getInstance().IsResetScenarioOther(me_adNetworkType, m_idRule)) {
                LoadOther();
            } else {
                WWWRequest.getInstance().SendScenarioReset(EAdUnitType._INTERSTITIAL);
            }
        }
    }
}

