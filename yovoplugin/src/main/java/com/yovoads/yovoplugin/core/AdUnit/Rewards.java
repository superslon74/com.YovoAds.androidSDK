package com.yovoads.yovoplugin.core.AdUnit;

import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;
import com.yovoads.yovoplugin.common.EAdUnitPrice;
import com.yovoads.yovoplugin.common.IAdUnitType;
import com.yovoads.yovoplugin.common.dbLocal;
import com.yovoads.yovoplugin.core.Scenario.ScenarioReward;
import com.yovoads.yovoplugin.core.YTimer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class Rewards implements IAdUnitType {

    private static Rewards mc_this = null;
    public HashMap<EAdNetworkType, ArrayList<RewardView>> md_adNetworkUnit = new HashMap<EAdNetworkType, ArrayList<RewardView>>();

    public RewardView mc_rewardViewActive = null;

    public static int m_rewardNextShowAvailable;
    public boolean m_isIgnore = false;
    private String m_key = "";
    private String m_value = "";
////////////////////////////////////////////////////////////// ---  Constructor  --- //////////////////////////////////////////////////////////////

    public static Rewards Init() {
        if(mc_this == null)
        {
            mc_this = new Rewards();
        }

        return mc_this;
    }

    private Rewards() {
    }

    public static Rewards getInstance() {
        return mc_this;
    }
////////////////////////////////////////////////////////////// ---  Method_AddNetwork  --- //////////////////////////////////////////////////////////////

    public void AddNetworks(EAdNetworkType _adNetwork, String[] _adUnits) {
        if (!md_adNetworkUnit.containsKey(_adNetwork)) {
            ArrayList<RewardView> _list = new ArrayList<RewardView>();
            _list.ensureCapacity(_adUnits.length);
            for(int i= 0; i < _adUnits.length; i++) {
                _list.add(new RewardView(_adNetwork, _adUnits[i], EAdUnitPrice.GetName(i)));
            }
            _list.trimToSize();
            md_adNetworkUnit.put(_adNetwork, _list);
        }
    }
////////////////////////////////////////////////////////////// ---  Method  --- //////////////////////////////////////////////////////////////

    @Override
    public void RunLoadingAdUnit(EAdNetworkType _adNetworkType, int _idRule/*, boolean _isLoadAndShow*/) {

        if (md_adNetworkUnit.containsKey(_adNetworkType)) {
            if (_adNetworkType == EAdNetworkType._ADMOB || _adNetworkType == EAdNetworkType._FACEBOOK || _adNetworkType == EAdNetworkType._UNITY_ADS) {
                Iterator<RewardView> _iterator = md_adNetworkUnit.get(_adNetworkType).iterator();
                while (_iterator.hasNext()) {
                    _iterator.next().LoadOther();
                }
            } else if (_adNetworkType == EAdNetworkType._CROSS_PROMOTION || _adNetworkType == EAdNetworkType._EXCHANGE || _adNetworkType == EAdNetworkType._YOVO_ADVERTISING) {
                Iterator<RewardView> _iterator = md_adNetworkUnit.get(_adNetworkType).iterator();
                while (_iterator.hasNext()) {
                    _iterator.next().LoadYovo(_idRule);;
                }
            }
        }
    }

    @Override
    public boolean TryShowingAdUnit(EAdNetworkType _adNetworkType, int _idRule) {
         if (md_adNetworkUnit.containsKey(_adNetworkType)) {
            Iterator<RewardView> _iterator = md_adNetworkUnit.get(_adNetworkType).iterator();
            while (_iterator.hasNext()) {
                if(_iterator.next().TryShow(_idRule)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean IsLoadNextAdUnit(EAdNetworkType  _adNetworkType) {

        Iterator<RewardView> _iterator = md_adNetworkUnit.get(_adNetworkType).iterator();
        while (_iterator.hasNext()) {
            if(_iterator.next().IsAdUnitlLoadingResult() == EAdUnitLoadingResult._READY) {
                return true;
            }
        }

        return false;
    }
    ////////////////////////////////////////////////////////////// ---  Method  --- //////////////////////////////////////////////////////////////

    public void Show(String _key, String _value) {
        if(dbLocal.getInstance().IsRewardAvailable() && m_rewardNextShowAvailable < 1 && mc_rewardViewActive == null) {
            m_isIgnore = false;
            m_key = _key;
            m_value = _value;
            ScenarioReward.getInstance().ShowNextAvailableAdUnit();
        } else {
            OnRewardResult(false);
        }
    }

    public void ShowIgnore(String _key, String _value) {
        if (mc_rewardViewActive == null) {
            m_isIgnore = true;
            m_key = _key;
            m_value = _value;
            ScenarioReward.getInstance().ShowNextAvailableAdUnit();
        } else {
            OnRewardResult(false);
        }
    }

    public void OnRewardResult(boolean _result) {
        YovoSDK.mi_OnUnitySDK.OnRewarded(_result, m_key, m_value);
    }
}