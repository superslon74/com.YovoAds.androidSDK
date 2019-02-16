package com.yovoads.yovoplugin.core.AdUnit;

import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;
import com.yovoads.yovoplugin.common.EAdUnitPrice;
import com.yovoads.yovoplugin.common.IAdUnitType;
import com.yovoads.yovoplugin.core.Scenario.ScenarioInterstitial;
import com.yovoads.yovoplugin.core.YTimer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Interstitials implements IAdUnitType {

    private static Interstitials mc_this = null;
    private HashMap<EAdNetworkType, ArrayList<InterstitialView>> md_adNetworkUnit = new HashMap<EAdNetworkType, ArrayList<InterstitialView>>();

    public InterstitialView mc_interstitialViewActive = null;
    private int m_periodShow = 0;
    private int m_timeShowingLast = -1;
////////////////////////////////////////////////////////////// ---  Constructor  --- //////////////////////////////////////////////////////////////

    public static Interstitials Init() {
        if (mc_this == null) {
            mc_this = new Interstitials();
        }
        return mc_this;
    }

    private Interstitials() {
    }

    public static Interstitials getInstance() {
        return mc_this;
    }

////////////////////////////////////////////////////////////// ---  Method_AddNetwork  --- //////////////////////////////////////////////////////////////

    public void AddNetworks(EAdNetworkType _adNetwork, String[] _adUnits) {
        if (!md_adNetworkUnit.containsKey(_adNetwork)) {
            ArrayList<InterstitialView> _list = new ArrayList<InterstitialView>();
            _list.ensureCapacity(_adUnits.length);
            for(int i = 0; i < _adUnits.length; i++) {
                _list.add(new InterstitialView(_adNetwork, _adUnits[i], EAdUnitPrice.GetName(i)));
            }
            _list.trimToSize();
            md_adNetworkUnit.put(_adNetwork, _list);
        }
    }
    ////////////////////////////////////////////////////////////// ---  Method  --- //////////////////////////////////////////////////////////////

    @Override
    public void RunLoadingAdUnit(EAdNetworkType _adNetworkType, int _idRule) {

        if (md_adNetworkUnit.containsKey(_adNetworkType)) {
            if (_adNetworkType == EAdNetworkType._ADMOB || _adNetworkType == EAdNetworkType._FACEBOOK || _adNetworkType == EAdNetworkType._UNITY_ADS) {
                Iterator<InterstitialView> _iterator = md_adNetworkUnit.get(_adNetworkType).iterator();
                while (_iterator.hasNext()) {
                    _iterator.next().LoadOther();
                }
            } else if (_adNetworkType == EAdNetworkType._CROSS_PROMOTION || _adNetworkType == EAdNetworkType._EXCHANGE || _adNetworkType == EAdNetworkType._YOVO_ADVERTISING) {
                Iterator<InterstitialView> _iterator = md_adNetworkUnit.get(_adNetworkType).iterator();
                while (_iterator.hasNext()) {
                    _iterator.next().LoadYovo(_idRule);;
                }
            }
        }
    }

    @Override
    public boolean TryShowingAdUnit(EAdNetworkType _adNetworkType, int _idRule) {

        if (md_adNetworkUnit.containsKey(_adNetworkType)) {
            Iterator<InterstitialView> _iterator = md_adNetworkUnit.get(_adNetworkType).iterator();
            while (_iterator.hasNext()) {
                if(_iterator.next().TryShow(_idRule)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean IsLoadNextAdUnit(EAdNetworkType  _adNetworkType) {

        Iterator<InterstitialView> _iterator = md_adNetworkUnit.get(_adNetworkType).iterator();
        while (_iterator.hasNext()) {
            if(_iterator.next().IsAdUnitlLoadingResult() == EAdUnitLoadingResult._READY) {
                return true;
            }
        }

        return false;
    }
////////////////////////////////////////////////////////////// ---  Method  --- //////////////////////////////////////////////////////////////

    public void SetPeriodShow(int _periodShow) {
        if (_periodShow > 0) {
            m_periodShow = _periodShow;
        }
    }

    public void Show() {
        if (mc_interstitialViewActive == null && m_timeShowingLast + m_periodShow <= YTimer.getInstance().m_secActive) {
            ScenarioInterstitial.getInstance().ShowNextAvailableAdUnit();
        }
    }

    public void SetTimeLastShowing() {
        m_timeShowingLast = YTimer.getInstance().m_secActive;
    }
}
