package com.yovoads.yovoplugin.core.Scenario;


import android.util.Log;

import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.core.AdUnit.AdNetworkData;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.core.www.extra._QuratorSettingScenarioRule;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class AScenario
{

    protected AScenario         	mc_this = null;
    protected int 					m_maskAdNetwork = 0;
    protected ArrayList<Rule>		ml_listRules = new ArrayList<Rule>();


    protected void SetScenario(ArrayList<_QuratorSettingScenarioRule> _rulesWww, ArrayList<EAdNetworkType> _alNetworksAvailable)
    {

        ml_listRules.clear();
        Iterator<_QuratorSettingScenarioRule> _iterator = _rulesWww.iterator();
        while(_iterator.hasNext()) {
            _QuratorSettingScenarioRule _qSSRule = _iterator.next();

            switch (_qSSRule.GetNetworkType()) {
                case _ADMOB:
                    ml_listRules.add(new Rule(_qSSRule.GetIdRule(), EAdNetworkType._ADMOB, _qSSRule.GetCountShowingLimit(), _qSSRule.GetCountShowingRemain(),
                            (AdNetworkData.getInstance().m_isAdmob && _alNetworksAvailable.contains(EAdNetworkType._ADMOB) ? true : false)));
                    break;
                case _FACEBOOK:
                    ml_listRules.add(new Rule(_qSSRule.GetIdRule(), EAdNetworkType._FACEBOOK, _qSSRule.GetCountShowingLimit(), _qSSRule.GetCountShowingRemain(),
                            (AdNetworkData.getInstance().m_isFacebook && _alNetworksAvailable.contains(EAdNetworkType._FACEBOOK) ? true : false)));
                    break;
                case _UNITY_ADS:
                    ml_listRules.add(new Rule(_qSSRule.GetIdRule(), EAdNetworkType._UNITY_ADS, _qSSRule.GetCountShowingLimit(), _qSSRule.GetCountShowingRemain(),
                            (AdNetworkData.getInstance().m_isUnityAds && _alNetworksAvailable.contains(EAdNetworkType._UNITY_ADS) ? true : false)));
                    break;
                case _CROSS_PROMOTION:
                    ml_listRules.add(new Rule(_qSSRule.GetIdRule(), EAdNetworkType._CROSS_PROMOTION, _qSSRule.GetCountShowingLimit(), _qSSRule.GetCountShowingRemain(),
                            (AdNetworkData.getInstance().m_isCrossPromotion && _alNetworksAvailable.contains(EAdNetworkType._CROSS_PROMOTION) ? true : false)));
                    break;
                case _EXCHANGE:
                    ml_listRules.add(new Rule(_qSSRule.GetIdRule(), EAdNetworkType._EXCHANGE, _qSSRule.GetCountShowingLimit(), _qSSRule.GetCountShowingRemain(),
                            (AdNetworkData.getInstance().m_isExchange && _alNetworksAvailable.contains(EAdNetworkType._EXCHANGE) ? true : false)));
                    break;
                case _YOVO_ADVERTISING:
                    ml_listRules.add(new Rule(_qSSRule.GetIdRule(), EAdNetworkType._YOVO_ADVERTISING, _qSSRule.GetCountShowingLimit(), _qSSRule.GetCountShowingRemain(),
                            (AdNetworkData.getInstance().m_isYovoAdvertising && _alNetworksAvailable.contains(EAdNetworkType._YOVO_ADVERTISING) ? true : false)));
                    break;
            }
        }
    }

    public abstract void RunLoadingAdUnit();

    public void OnShowing(int _showingIdRule)
    {
        Iterator<Rule> _iterator = ml_listRules.iterator();
        while (_iterator.hasNext()) {
            Rule _rule = _iterator.next();
            if(_rule.getIdRule() == _showingIdRule) {
                if(_rule.GetCountShowingLimit() > 0) {
                    _rule.SetCountShowingRemain(_rule.GetCountShowingRemain() + 1);
                    break;
                }
            }
        }
    }

    public abstract void ShowNextAvailableAdUnit();

    public void ResetScenario()
    {
        Iterator<Rule> _iterator = ml_listRules.iterator();
        while (_iterator.hasNext()) {
            _iterator.next().Reset();
        }
    }



















    //protected abstract boolean ShowFirstAvailableLoading();

    /*protected void AddAdNetworkToMask(EAdNetworkType _adNetworkType)
    {
        //m_maskAdNetwork += (int)Mathf.Pow(2f, (int)_adNetworkType);
    }*/

    /*public boolean IsNetworkType(EAdNetworkType _networkType)
    {
        for (int i = 0; i < ml_listRules.size(); i++)
        {
            if(_networkType == ml_listRules.get(i).GetNetworkType())
            {
                return true;
            }
        }
        return false;
    }*/

    /*protected int GetIndexNextRule()
    {
        if(ml_listRules.size() - 1 == m_indexScenario)
        {
            for (int i = 0; i < ml_listRules.size(); i++)
            {
                ml_listRules.get(i).Reset();
            }
            return 0;
        }
        return m_indexScenario + 1;
    }*/
}
