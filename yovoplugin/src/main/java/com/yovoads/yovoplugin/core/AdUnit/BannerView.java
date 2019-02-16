package com.yovoads.yovoplugin.core.AdUnit;

import android.util.Log;

import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.EAdUnitAdLoadingFailed;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;
import com.yovoads.yovoplugin.common.EAdUnitPrice;
import com.yovoads.yovoplugin.common.EAdUnitType;
import com.yovoads.yovoplugin.common.IAdUnitOnMethod;
import com.yovoads.yovoplugin.common.EGravity;
import com.yovoads.yovoplugin.common.dbLocal;
import com.yovoads.yovoplugin.core.Scenario.NextRuleId;
import com.yovoads.yovoplugin.core.Scenario.ScenarioBanner;
import com.yovoads.yovoplugin.core.www.WWWRequest;
import com.yovoads.yovoplugin.networks.viewBanners.BannerAdmob;
import com.yovoads.yovoplugin.networks.viewBanners.BannerCrossPromotion;
import com.yovoads.yovoplugin.networks.viewBanners.BannerExchange;
import com.yovoads.yovoplugin.networks.viewBanners.BannerFacebook;
import com.yovoads.yovoplugin.networks.viewBanners.BannerUnityAds;
import com.yovoads.yovoplugin.networks.viewBanners.BannerYovoAdvertising;
import com.yovoads.yovoplugin.networks.viewBanners.ExampleBanner;

public class BannerView implements IAdUnitOnMethod {

    private int m_id = 0;
    private ExampleBanner m_exampleAdUnit;
    private EAdNetworkType me_adNetworkType;
    private EAdUnitPrice me_adUnitPrice;

    private boolean m_isLoadAndShow = false;
    private boolean m_isPause = false;
    private String m_adUnitId = "";
    private int m_idRule = 0;

////////////////////////////////////////////////////////////// ---  Init  --- //////////////////////////////////////////////////////////////

    private BannerView() {
    }

    public BannerView(EAdNetworkType _adNetworkType, String _adUnitId, EGravity _gravity, EAdUnitPrice _adUnitPrice) {

        m_id = AdNetworkData.getInstance().CreateIdForAdUnit();
        me_adUnitPrice = _adUnitPrice;
        me_adNetworkType = _adNetworkType;

        switch (me_adNetworkType) {
            case _ADMOB:
                m_exampleAdUnit = new BannerAdmob(this, _adUnitId, _gravity, dbLocal.getInstance().GetBannerTimerShow(me_adUnitPrice));
                break;
            case _FACEBOOK:
                m_exampleAdUnit = new BannerFacebook(this, _adUnitId, _gravity, dbLocal.getInstance().GetBannerTimerShow(me_adUnitPrice));
                break;
            case _UNITY_ADS:
                m_exampleAdUnit = new BannerUnityAds(this, _adUnitId, _gravity, dbLocal.getInstance().GetBannerTimerShow(me_adUnitPrice));
                break;
            case _CROSS_PROMOTION:
                m_exampleAdUnit = new BannerCrossPromotion(this, _adUnitId, _gravity, dbLocal.getInstance().GetBannerTimerShow(me_adUnitPrice));
                break;
            case _EXCHANGE:
                m_exampleAdUnit = new BannerExchange(this, _adUnitId, _gravity, dbLocal.getInstance().GetBannerTimerShow(me_adUnitPrice));
                break;
            case _YOVO_ADVERTISING:
                m_exampleAdUnit = new BannerYovoAdvertising(this, _adUnitId, _gravity, dbLocal.getInstance().GetBannerTimerShow(me_adUnitPrice));
                break;
        }
    }
////////////////////////////////////////////////////////////// ---  Method  --- //////////////////////////////////////////////////////////////

    public void SetGravity(EGravity _gravity) {
        m_exampleAdUnit.SetGravity(_gravity);
    }


    public void LoadOther() {
        if(m_exampleAdUnit.me_isAdUnitlLoadingResult == EAdUnitLoadingResult._NONE || m_exampleAdUnit.me_isAdUnitlLoadingResult == EAdUnitLoadingResult._FAILED) {
            m_isLoadAndShow = false;
            m_exampleAdUnit.LoadOther();
        }
    }

    public void LoadOtherAndShow(int _idRule) {
        if(m_exampleAdUnit.me_isAdUnitlLoadingResult == EAdUnitLoadingResult._NONE || m_exampleAdUnit.me_isAdUnitlLoadingResult == EAdUnitLoadingResult._FAILED) {
            m_idRule = _idRule;
            m_isLoadAndShow = true;
            m_exampleAdUnit.LoadOther();
        }
    }

    public void LoadYovo(boolean _isLoadAndShow, int _idRule) {
        if(m_exampleAdUnit.me_isAdUnitlLoadingResult == EAdUnitLoadingResult._NONE || m_exampleAdUnit.me_isAdUnitlLoadingResult == EAdUnitLoadingResult._FAILED) {
            m_isLoadAndShow = _isLoadAndShow;
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
        if (Banners.getInstance().mc_bannerViewActive == null) {
            Banners.getInstance().mc_bannerViewActive = (BannerView) this;
        } else {
            if (Banners.getInstance().mc_bannerViewActive.m_id != m_id) {
                Banners.getInstance().mc_bannerViewActive.Hide();
                Banners.getInstance().mc_bannerViewActive = (BannerView) this;
            }
        }
        m_exampleAdUnit.Show();
    }

    public void ShowContinue() {
        m_exampleAdUnit.SetPause(false);
    }

    private void Hide() {
        m_exampleAdUnit.Hide();
    }

    public boolean SetPause(boolean _isPause) {

        if(m_exampleAdUnit.me_isAdUnitlLoadingResult == EAdUnitLoadingResult._SHOWING_NOW) {
            if(m_exampleAdUnit.SetPause(true)) {

                OnAdClosed();
                Hide();
                m_exampleAdUnit.me_isAdUnitlLoadingResult = EAdUnitLoadingResult._SHOWING_PAUSE;
                m_isPause = true;

                return true;
            } else {
                YovoSDK.ShowLog("ViewBanner", "Hide= " + m_exampleAdUnit.GetShowTimeCur());
            }
        } else {
            YovoSDK.ShowLog("ViewBanner", "Hide= " + m_exampleAdUnit.me_isAdUnitlLoadingResult.name());
        }
        return false;
    }

    ////////////////////////////////////////////////////////////// ---  Callback --- //////////////////////////////////////////////////////////////
    @Override
    public void OnSetLoadingAdUnitId(String _adUnitId) {
        m_adUnitId = _adUnitId;
    }

    @Override
    public void OnAdLoaded() {
        YovoSDK.ShowLog("ViewBanner", String.valueOf(me_adNetworkType) + " -> banner -> OnAdLoaded -> " + String.valueOf(me_adUnitPrice));
        if (m_isLoadAndShow) {
            m_isLoadAndShow = false;
            Show();
        }
    }

    @Override
    public void OnAdFailedToLoad(String _errorReason) {
        YovoSDK.ShowLog("ViewBanner", String.valueOf(me_adNetworkType) + " -> banner -> OnAdFailedToLoad -> " + String.valueOf(me_adUnitPrice) + "  ERROR=" + _errorReason);
        WWWRequest.getInstance().SendEventLoadFailed(me_adNetworkType, EAdUnitType._BANNER, me_adUnitPrice, m_adUnitId, EAdUnitAdLoadingFailed._ERROR_TEMP);
        if (m_isLoadAndShow)
        {
            m_isLoadAndShow = false;
            ScenarioBanner.getInstance().ShowNextAvailableAdUnit();
        }
    }

    @Override
    public void OnAdShowing() {
        if(!m_isPause) {
            YovoSDK.ShowLog("ViewBanner", String.valueOf(me_adNetworkType) + " -> banner -> OnAdShowing -> " + String.valueOf(me_adUnitPrice));
            ScenarioBanner.getInstance().OnShowing(m_idRule);
            WWWRequest.getInstance().SendEventShowing(me_adNetworkType, EAdUnitType._BANNER, me_adUnitPrice, m_idRule, m_adUnitId);
        } else {
            m_isPause = false;
        }
    }

    @Override
    public void OnAdClicked() {
        YovoSDK.ShowLog("ViewBanner", String.valueOf(me_adNetworkType) + " -> banner -> OnAdClick -> " + String.valueOf(me_adUnitPrice));
        WWWRequest.getInstance().SendEventClick(me_adNetworkType, EAdUnitType._BANNER, me_adUnitPrice, m_idRule, m_adUnitId);
    }

    @Override
    public void OnAdClosed() {
        YovoSDK.ShowLog("ViewBanner", String.valueOf(me_adNetworkType) + " -> banner -> OnAdClosed -> " + String.valueOf(me_adUnitPrice));
    }

    @Override
    public void OnAdDestroy()
    {
        YovoSDK.ShowLog("ViewBanner", String.valueOf(me_adNetworkType) + " -> banner -> OnAdDestroy -> " + String.valueOf(me_adUnitPrice));

        m_exampleAdUnit.Hide();
        m_exampleAdUnit.me_isAdUnitlLoadingResult = EAdUnitLoadingResult._NONE;
        IsLoadAndShowThisAdUnitNow();
    }

    private void IsLoadAndShowThisAdUnitNow()
    {
        if(me_adNetworkType == EAdNetworkType._CROSS_PROMOTION || me_adNetworkType == EAdNetworkType._EXCHANGE || me_adNetworkType == EAdNetworkType._YOVO_ADVERTISING)
        {
            NextRuleId _nextRuleId = ScenarioBanner.getInstance().GetNextAvailableRuleIdYovo(me_adNetworkType, m_idRule);
            if(_nextRuleId.m_idRule > 0) {
                if(!_nextRuleId.m_isLoadAndShow) {
                    ScenarioBanner.getInstance().ShowNextAvailableAdUnit();
                }
                LoadYovo(_nextRuleId.m_isLoadAndShow, m_idRule);
            }
        } else {
            if(ScenarioBanner.getInstance().GetNextAvailableRuleId(me_adNetworkType, m_idRule, me_adUnitPrice)) {
                LoadOtherAndShow(m_idRule);
            } else {
                LoadOther();
                ScenarioBanner.getInstance().ShowNextAvailableAdUnit();
            }
        }
    }
}
