package com.yovoads.yovoplugin.core.AdUnit;

import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.EAdUnitAdLoadingFailed;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;
import com.yovoads.yovoplugin.common.EAdUnitPrice;
import com.yovoads.yovoplugin.common.EAdUnitType;
import com.yovoads.yovoplugin.common.IAdUnitOnMethod;
import com.yovoads.yovoplugin.common.IAdUnitOnMethodReward;
import com.yovoads.yovoplugin.common.dbLocal;
import com.yovoads.yovoplugin.core.Scenario.ScenarioInterstitial;
import com.yovoads.yovoplugin.core.Scenario.ScenarioReward;
import com.yovoads.yovoplugin.core.www.WWWRequest;
import com.yovoads.yovoplugin.networks.viewRewards.ExampleReward;
import com.yovoads.yovoplugin.networks.viewRewards.RewardAdmob;
import com.yovoads.yovoplugin.networks.viewRewards.RewardCrossPromotion;
import com.yovoads.yovoplugin.networks.viewRewards.RewardExchange;
import com.yovoads.yovoplugin.networks.viewRewards.RewardFacebook;
import com.yovoads.yovoplugin.networks.viewRewards.RewardUnityAds;
import com.yovoads.yovoplugin.networks.viewRewards.RewardYovoAdvertising;

import java.util.Date;

public class RewardView implements IAdUnitOnMethod, IAdUnitOnMethodReward {

    private ExampleReward m_exampleAdUnit = null;
    private EAdNetworkType me_adNetworkType = null;
    private EAdUnitPrice me_adUnitPrice = null;

    private String m_adUnitId = "";
    private int m_idRule = 0;
    ////////////////////////////////////////////////////////////// ---  Init  --- //////////////////////////////////////////////////////////////

    private RewardView() {
    }

    public RewardView(EAdNetworkType _adNetworkType, String _adUnitId, EAdUnitPrice _adUnitPrice) {

        me_adUnitPrice = _adUnitPrice;
        me_adNetworkType = _adNetworkType;

        switch (me_adNetworkType) {
            case _ADMOB:
                m_exampleAdUnit = new RewardAdmob(this, _adUnitId);
                break;
            case _FACEBOOK:
                m_exampleAdUnit = new RewardFacebook(this, _adUnitId);
                break;
            case _UNITY_ADS:
                m_exampleAdUnit = new RewardUnityAds(this, _adUnitId);
                break;
            case _CROSS_PROMOTION:
                m_exampleAdUnit = new RewardCrossPromotion(this, _adUnitId);
                break;
            case _EXCHANGE:
                m_exampleAdUnit = new RewardExchange(this, _adUnitId);
                break;
            case _YOVO_ADVERTISING:
                m_exampleAdUnit = new RewardYovoAdvertising(this, _adUnitId);
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
        Rewards.getInstance().mc_rewardViewActive = (RewardView) this;
        m_exampleAdUnit.Show();
    }
    ////////////////////////////////////////////////////////////// ---  Callback --- //////////////////////////////////////////////////////////////

    @Override
    public void OnSetLoadingAdUnitId(String _adUnitId)
    {
        m_adUnitId = _adUnitId;
    }

    @Override
    public void OnAdLoaded() {
        YovoSDK.ShowLog("ViewReward", me_adNetworkType + " -> reward -> OnAdLoaded -> " + String.valueOf(me_adUnitPrice));
    }

    @Override
    public void OnAdFailedToLoad(String _errorReason) {
        YovoSDK.ShowLog("ViewReward", me_adNetworkType + " -> reward -> OnAdFailedToLoad -> " + String.valueOf(me_adUnitPrice) + "  ERROR=" +_errorReason);
        WWWRequest.getInstance().SendEventLoadFailed(me_adNetworkType, EAdUnitType._REWARD, me_adUnitPrice, m_adUnitId, EAdUnitAdLoadingFailed._ERROR_TEMP);
    }

    @Override
    public void OnAdShowing() {
        YovoSDK.ShowLog("ViewReward", me_adNetworkType + " -> reward -> OnAdShowing -> " + String.valueOf(me_adUnitPrice));
        ScenarioReward.getInstance().OnShowing(m_idRule);
        dbLocal.getInstance().SetReward_OnAdShowing();
        WWWRequest.getInstance().SendEventShowing(me_adNetworkType, EAdUnitType._REWARD, me_adUnitPrice, m_idRule, m_adUnitId);
    }

    @Override
    public void OnAdClicked() {
        YovoSDK.ShowLog("ViewReward", me_adNetworkType + " -> reward -> OnAdClick -> " + String.valueOf(me_adUnitPrice));
        WWWRequest.getInstance().SendEventClick(me_adNetworkType, EAdUnitType._REWARD, me_adUnitPrice, m_idRule, m_adUnitId);
    }

    @Override
    public void OnAdRewarded() {
        YovoSDK.ShowLog("ViewReward", me_adNetworkType + " -> reward -> OnAdRewarded -> " + String.valueOf(me_adUnitPrice));
        Rewards.getInstance().OnRewardResult(true);
        WWWRequest.getInstance().SendEventRewarded(me_adNetworkType, me_adUnitPrice, m_idRule, m_adUnitId, Rewards.getInstance().m_isIgnore);
    }

    @Override
    public void OnAdCompleted() {
        YovoSDK.ShowLog("ViewReward", me_adNetworkType + " -> reward -> OnAdCompleted -> " + String.valueOf(me_adUnitPrice));

    }

    @Override
    public void OnAdClosed() {
        YovoSDK.ShowLog("ViewReward", me_adNetworkType + " -> reward -> OnAdClosed -> " + String.valueOf(me_adUnitPrice));
        Rewards.getInstance().OnRewardResult(false);
    }

    @Override
    public void OnAdDestroy()
    {
        YovoSDK.ShowLog("ViewReward", String.valueOf(me_adNetworkType) + " -> reward -> OnAdDestroy -> " + String.valueOf(me_adUnitPrice));
        m_exampleAdUnit.me_isAdUnitlLoadingResult = EAdUnitLoadingResult._NONE;
        Rewards.getInstance().mc_rewardViewActive = null;
        IsLoadAndShowThisAdUnitNow();
    }

    private void IsLoadAndShowThisAdUnitNow()
    {
        if(me_adNetworkType == EAdNetworkType._CROSS_PROMOTION || me_adNetworkType == EAdNetworkType._EXCHANGE || me_adNetworkType == EAdNetworkType._YOVO_ADVERTISING)
        {
            int _nextRuleId = ScenarioReward.getInstance().GetNextAvailableRuleIdYovo(me_adNetworkType, m_idRule);
            if(_nextRuleId > 0) {
                LoadYovo(m_idRule);
            } else if(_nextRuleId == -99) {
                WWWRequest.getInstance().SendScenarioReset(EAdUnitType._REWARD);
            }
        } else {
            if(!ScenarioReward.getInstance().IsResetScenarioOther(me_adNetworkType, m_idRule)) {
                LoadOther();
            } else {
                WWWRequest.getInstance().SendScenarioReset(EAdUnitType._REWARD);
            }
        }
    }

}


