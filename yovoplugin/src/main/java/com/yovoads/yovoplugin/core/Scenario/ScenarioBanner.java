package com.yovoads.yovoplugin.core.Scenario;

import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.EAdUnitPrice;
import com.yovoads.yovoplugin.common.EAdUnitType;
import com.yovoads.yovoplugin.core.AdUnit.AdNetworkData;
import com.yovoads.yovoplugin.core.AdUnit.Banners;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.core.BannerStarting;
import com.yovoads.yovoplugin.core.YTimer;
import com.yovoads.yovoplugin.core.www.WWWRequest;
import com.yovoads.yovoplugin.core.www.extra._QuratorSettingScenarioRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ScenarioBanner extends AScenario {

    private static ScenarioBanner			mc_this = null;

    public Thread                           m_threadBannerStartAwake = null;
    ////////////////////////////////////////////////////////////// ---  Constructor  --- //////////////////////////////////////////////////////////////

    public static ScenarioBanner Init() {
        if (mc_this == null) {
            mc_this = new ScenarioBanner();
            Banners.Init();
        }

        return mc_this;
    }

    private ScenarioBanner() {
        //BannerStarting.getInstance();
    }

    public static ScenarioBanner getInstance() {
        return mc_this;
    }
    ////////////////////////////////////////////////////////////// ---  ScenarioMethod  --- //////////////////////////////////////////////////////////////

    @Override
    public void SetScenario(ArrayList<_QuratorSettingScenarioRule> _rulesWww, ArrayList<EAdNetworkType> _alNetworksAvailable) {
        super.SetScenario(_rulesWww, _alNetworksAvailable);

        Iterator<Rule> _iterator = ml_listRules.iterator();
        while (_iterator.hasNext()) {
            Rule _rule = _iterator.next();

            switch (_rule.GetNetworkType()) {
                case _ADMOB:
                    Banners.getInstance().AddNetworks(EAdNetworkType._ADMOB, AdNetworkData.getInstance().GetAdUnitsBannerAdmob());
                    break;
                case _FACEBOOK:
                    Banners.getInstance().AddNetworks(EAdNetworkType._FACEBOOK, AdNetworkData.getInstance().GetAdUnitsBannerFacebook());
                    break;
                case _UNITY_ADS:
                    Banners.getInstance().AddNetworks(EAdNetworkType._UNITY_ADS, AdNetworkData.getInstance().GetAdUnitsBannerUnityAds());
                    break;
                case _CROSS_PROMOTION:
                    Banners.getInstance().AddNetworks(EAdNetworkType._CROSS_PROMOTION, AdNetworkData.getInstance().GetAdUnitsBannerCrossPromo());
                    break;
                case _EXCHANGE:
                    Banners.getInstance().AddNetworks(EAdNetworkType._EXCHANGE, AdNetworkData.getInstance().GetAdUnitsBannerExchange());
                    break;
                case _YOVO_ADVERTISING:
                    Banners.getInstance().AddNetworks(EAdNetworkType._YOVO_ADVERTISING, AdNetworkData.getInstance().GetAdUnitsBannerYovoAdvertising());
                    break;
            }
        }

        YTimer.getInstance().RunLoadingAdUnitBanner();

        if(Banners.getInstance().m_startAwake) {
            PullStartAwake();
        }
    }

    @Override
    public void RunLoadingAdUnit() {

        HashMap<EAdNetworkType, Boolean> _temp = new HashMap<EAdNetworkType, Boolean>();
        Iterator<Rule> _iterator = ml_listRules.iterator();
        while (_iterator.hasNext()) {
            Rule _rule =_iterator.next();
            YovoSDK.ShowLog("ScenarioBanner->", _rule.GetNetworkType().name());
            if(!_temp.containsKey(_rule.GetNetworkType())) {
                _temp.put(_rule.GetNetworkType(), true);
            }
        }

        _iterator = ml_listRules.iterator();
        while (_iterator.hasNext()) {
            Rule _rule = _iterator.next();
            if (_rule.IsHaveSomethingToShowUsed()) {
                if(_temp.containsKey(_rule.GetNetworkType()))
                {
                    Banners.getInstance().RunLoadingAdUnit(_rule.GetNetworkType(), _rule.getIdRule());
                    _temp.remove(_rule.GetNetworkType());
                    if(_temp.size() == 0) {
                        break;
                    }
                }
            }
        }
    }
    ////////////////////////////////////////////////////////////// ---  Method  --- //////////////////////////////////////////////////////////////

    public void PullStartAwake() {
        if(m_threadBannerStartAwake == null) {
            m_threadBannerStartAwake = new Thread(new BannerStarting());
            m_threadBannerStartAwake.start();
        }
    }

    public boolean Starting() {
        Iterator<Rule> _iterator = ml_listRules.iterator();
        while (_iterator.hasNext()) {
            Rule _rule = _iterator.next();
            if (_rule.IsHaveSomethingToShowUsed()) {
                if (Banners.getInstance().TryShowingAdUnit(_rule.GetNetworkType(), _rule.getIdRule())) {
                    return true;
                }
            }
        }
        return false;
    }
////////////////////////////////////////////////////////////// ---  Method  --- //////////////////////////////////////////////////////////////

    public NextRuleId GetNextAvailableRuleIdYovo(EAdNetworkType  _adNetworkType, int _showingLastIdRule) {

        NextRuleId _nextRuleId = new NextRuleId(false, _showingLastIdRule);
        boolean _isRuleThis = false;
        int _index = -1;
        boolean _isLoadAndShow = true;

        Iterator<Rule> _iterator = ml_listRules.iterator();
        while (_iterator.hasNext()) {
            Rule _rule = _iterator.next();
            if (!_isRuleThis && _rule.getIdRule() != _showingLastIdRule) {
                _index++;
                continue;
            }
            if (!_isRuleThis) {
                if (_rule.IsHaveSomethingToShow()) {
                    return new NextRuleId(true, _rule.getIdRule());
                }
                _isRuleThis = true;
            } else {
                if(_rule.IsUsed()) {
                    if (_rule.GetNetworkType() == _adNetworkType) {
                        if ( _rule.IsHaveSomethingToShow()) {
                            return new NextRuleId(_isLoadAndShow, _rule.getIdRule());
                        }
                    } else {
                        _isLoadAndShow = false;
                    }
                }
            }
        }

        for (int i = _index; i > 0; i--) {
            Rule _rule = ml_listRules.get(i);
            if (_adNetworkType == _rule.GetNetworkType()) {
                if (_rule.IsHaveSomethingToShowUsed()) {
                    _nextRuleId.m_idRule = _rule.getIdRule();
                    _nextRuleId.m_isLoadAndShow = false;
                }
            }
        }

        return _nextRuleId;
    }

    public boolean GetNextAvailableRuleId(EAdNetworkType  _adNetworkType, int _showingLastIdRule, EAdUnitPrice _adUnitPrice) {

        boolean _isRuleThis = false;
        boolean _isLoadAndShow = true;

        Iterator<Rule> _iterator = ml_listRules.iterator();
        while (_iterator.hasNext()) {
            Rule _rule = _iterator.next();
            if (!_isRuleThis && _rule.getIdRule() != _showingLastIdRule) {
                continue;
            }
            if (!_isRuleThis) {
                if (_rule.IsHaveSomethingToShow()) {
                    return Banners.getInstance().IsLoadAndShowThisAdUnitNow(_adNetworkType, _adUnitPrice);
                }
                _isRuleThis = true;
            } else {
                if(_rule.IsUsed()) {
                    if (_rule.GetNetworkType() == _adNetworkType) {
                        if ( _rule.IsHaveSomethingToShow()) {
                            if(Banners.getInstance().IsLoadAndShowThisAdUnitNow(_adNetworkType, _adUnitPrice)) {
                                return (_isLoadAndShow) ? true : false;
                            }
                        }
                    } else {
                        _isLoadAndShow = false;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void ShowNextAvailableAdUnit() {
       // YovoSDK.ShowLog("ScenarioBanner", "ShowNextAvailableAdUnit=");

        boolean _isShowNextAvailableAdUnit = false;
        m_maskAdNetwork = 0;

        Iterator<Rule> _iterator = ml_listRules.iterator();
        while (_iterator.hasNext()) {
            Rule _rule = _iterator.next();
            if (TryShowAdUnit(_rule)) {
                _isShowNextAvailableAdUnit = true;
                break;
            } else {
                m_maskAdNetwork += 1;
            }
        }

        if (!_isShowNextAvailableAdUnit) {
            //YovoSDK.ShowLog("ScenarioBanner", "Banner all loadinFAILED");
            WWWRequest.getInstance().SendScenarioReset(EAdUnitType._BANNER);
        }
    }

    private boolean TryShowAdUnit(Rule _rule) {
        if (_rule.IsHaveSomethingToShowUsed()) {
            if (Banners.getInstance().TryShowingAdUnit(_rule.GetNetworkType(), _rule.getIdRule())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void ResetScenario()
    {
        super.ResetScenario();
        YovoSDK.ShowLog("ScenarioBanner", "ResetScenario");
        PullStartAwake();
    }


}

