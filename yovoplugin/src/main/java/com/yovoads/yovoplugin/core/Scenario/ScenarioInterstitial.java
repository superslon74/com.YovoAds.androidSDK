package com.yovoads.yovoplugin.core.Scenario;

import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.EAdUnitPrice;
import com.yovoads.yovoplugin.common.EAdUnitType;
import com.yovoads.yovoplugin.core.AdUnit.AdNetworkData;
import com.yovoads.yovoplugin.core.AdUnit.Interstitials;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.core.YTimer;
import com.yovoads.yovoplugin.core.www.WWWRequest;
import com.yovoads.yovoplugin.core.www.extra._QuratorSettingScenarioRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ScenarioInterstitial extends AScenario {
    private static ScenarioInterstitial mc_this = null;
    ////////////////////////////////////////////////////////////// ---  Constructor  --- //////////////////////////////////////////////////////////////

    public static ScenarioInterstitial Init() {
        if (mc_this == null) {
            mc_this = new ScenarioInterstitial();
            Interstitials.Init();
        }
        return mc_this;
    }

    private ScenarioInterstitial() {
    }

    public static ScenarioInterstitial getInstance() {
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
                    Interstitials.getInstance().AddNetworks(EAdNetworkType._ADMOB, AdNetworkData.getInstance().GetAdUnitsInterstitialAdmob());
                    break;
                case _FACEBOOK:
                    Interstitials.getInstance().AddNetworks(EAdNetworkType._FACEBOOK, AdNetworkData.getInstance().GetAdUnitsInterstitialFacebook());
                    break;
                case _UNITY_ADS:
                    Interstitials.getInstance().AddNetworks(EAdNetworkType._UNITY_ADS, AdNetworkData.getInstance().GetAdUnitsInterstitialUnityAds());
                    break;
                case _CROSS_PROMOTION:
                    Interstitials.getInstance().AddNetworks(EAdNetworkType._CROSS_PROMOTION, AdNetworkData.getInstance().GetAdUnitsInterstitialCrossPromo());
                    break;
                case _EXCHANGE:
                    Interstitials.getInstance().AddNetworks(EAdNetworkType._EXCHANGE, AdNetworkData.getInstance().GetAdUnitsInterstitialExchange());
                    break;
                case _YOVO_ADVERTISING:
                    Interstitials.getInstance().AddNetworks(EAdNetworkType._YOVO_ADVERTISING, AdNetworkData.getInstance().GetAdUnitsInterstitialYovoAdvertising());
                    break;
            }
        }

        YTimer.getInstance().RunLoadingAdUnitInterstitial();
    }

    @Override
    public  void RunLoadingAdUnit() {

        HashMap<EAdNetworkType, Boolean> _temp = new HashMap<EAdNetworkType, Boolean>();
        Iterator<Rule> _iterator = ml_listRules.iterator();
        while (_iterator.hasNext()) {
            Rule _rule =_iterator.next();
            YovoSDK.ShowLog("ScenarioInterstitial->", _rule.GetNetworkType().name());
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
                    Interstitials.getInstance().RunLoadingAdUnit(_rule.GetNetworkType(), _rule.getIdRule());
                    _temp.remove(_rule.GetNetworkType());
                    if(_temp.size() == 0) {
                        break;
                    }
                }
            }
        }
    }
////////////////////////////////////////////////////////////// ---  Method  --- //////////////////////////////////////////////////////////////

    public int GetNextAvailableRuleIdYovo(EAdNetworkType  _adNetworkType, int _showingLastIdRule) {

        int _nextRuleId = -99; // -1 dalshe po scenariu netu takih pravil no ust drygie pravula activnue // -99 dslshe nety ne kakih voobzhe activnuh pravil
        boolean _isRuleThis = false;

        Iterator<Rule> _iterator = ml_listRules.iterator();
        while (_iterator.hasNext()) {
            Rule _rule = _iterator.next();
            if (!_isRuleThis && _rule.getIdRule() != _showingLastIdRule) {
                continue;
            }
            if (!_isRuleThis) {
                if (_rule.IsHaveSomethingToShow()) {
                    return _rule.getIdRule();
                }
                _isRuleThis = true;
            } else {
                if(_rule.IsUsed()) {
                    if (_rule.GetNetworkType() == _adNetworkType) {
                        if ( _rule.IsHaveSomethingToShow()) {
                            return _rule.getIdRule();
                        }
                    } else if (_nextRuleId == -99) {
                        if(_rule.IsHaveSomethingToShow()) {
                            _nextRuleId = -1;
                        }
                    }
                }
            }
        }

        return _nextRuleId;
    }

    public boolean IsResetScenarioOther(EAdNetworkType  _adNetworkType, int _showingLastIdRule) {

        boolean _isResetScenario = true;
        boolean _isRuleThis = false;

        Iterator<Rule> _iterator = ml_listRules.iterator();
        while (_iterator.hasNext()) {
            Rule _rule = _iterator.next();
            if (!_isRuleThis && _rule.getIdRule() != _showingLastIdRule) {
                continue;
            }
            if (!_isRuleThis) {
                if (_rule.IsHaveSomethingToShow()) {
                    return false;
                }
                _isRuleThis = true;
            } else {
                if(_rule.IsUsed()) {
                    if (_rule.GetNetworkType() == _adNetworkType) {
                        if ( _rule.IsHaveSomethingToShow()) {
                            return false;
                        }
                    } else if (_isResetScenario) {
                        if(_rule.IsHaveSomethingToShow()) {
                            if(Interstitials.getInstance().IsLoadNextAdUnit(_adNetworkType)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return _isResetScenario;
    }

    @Override
    public void ShowNextAvailableAdUnit() {
        YovoSDK.ShowLog("ScenarioInterstitial", "ShowNextAvailableAdUnit=");

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
            WWWRequest.getInstance().SendScenarioReset(EAdUnitType._INTERSTITIAL);
        }
    }

    private boolean TryShowAdUnit(Rule _rule) {
        if (_rule.IsHaveSomethingToShowUsed()) {
            if (Interstitials.getInstance().TryShowingAdUnit(_rule.GetNetworkType(), _rule.getIdRule())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void ResetScenario()
    {
        super.ResetScenario();
        YTimer.getInstance().m_loadInterstitilalTimer = -1;
        YovoSDK.ShowLog("ScenarioInterstitia", "ResetScenario");

    }






}
