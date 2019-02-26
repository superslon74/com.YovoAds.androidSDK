package com.yovoads.yovoplugin.core.AdUnit;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;
import com.yovoads.yovoplugin.common.EAdUnitPrice;
import com.yovoads.yovoplugin.common.IAdUnitType;
import com.yovoads.yovoplugin.common.EGravity;
import com.yovoads.yovoplugin.core.Scenario.ScenarioBanner;
import com.yovoads.yovoplugin.implementations.banner.YBannerBackground;
import com.yovoads.yovoplugin.implementations.banner.YFragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Banners implements IAdUnitType {

    private static Banners mc_this = null;
    private HashMap<EAdNetworkType, ArrayList<BannerView>> md_adNetworkUnit = new HashMap<EAdNetworkType, ArrayList<BannerView>>();

    public boolean m_startAwake = false;
    public EGravity m_gravity = EGravity._BOTTON;

    public BannerView mc_bannerViewActive = null;
    ////////////////////////////////////////////////////////////// ---  Constructor  --- //////////////////////////////////////////////////////////////

    public static void Init() {
        if (mc_this == null) {
            mc_this = new Banners();
        }
    }

    private Banners() {
        m_gravity = EGravity._BOTTON;
        m_startAwake = false;
    }

    public static Banners getInstance() {
        return mc_this;
    }
    ////////////////////////////////////////////////////////////// ---  Method_AddNetwork  --- //////////////////////////////////////////////////////////////

    public void AddNetworks(EAdNetworkType _adNetwork, String[] _adUnits) {
        if (!md_adNetworkUnit.containsKey(_adNetwork)) {
            ArrayList<BannerView> _list = new ArrayList<BannerView>();
            _list.ensureCapacity(_adUnits.length);
            for(int i = 0; i < _adUnits.length; i++) {
                _list.add(new BannerView(_adNetwork, _adUnits[i], m_gravity, EAdUnitPrice.GetName(i)));
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
                Iterator<BannerView> _iterator = md_adNetworkUnit.get(_adNetworkType).iterator();
                while (_iterator.hasNext()) {
                    _iterator.next().LoadOther();
                }
            } else if (_adNetworkType == EAdNetworkType._CROSS_PROMOTION || _adNetworkType == EAdNetworkType._EXCHANGE || _adNetworkType == EAdNetworkType._YOVO_ADVERTISING) {
                Iterator<BannerView> _iterator = md_adNetworkUnit.get(_adNetworkType).iterator();
                while (_iterator.hasNext()) {
                    _iterator.next().LoadYovo(false, _idRule);;
                }
            }
        }
    }

    @Override
    public boolean TryShowingAdUnit(EAdNetworkType _adNetworkType, int _idRule) {

        if (md_adNetworkUnit.containsKey(_adNetworkType)) {
            Iterator<BannerView> _iterator = md_adNetworkUnit.get(_adNetworkType).iterator();
            while (_iterator.hasNext()) {
                if(_iterator.next().TryShow(_idRule)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean IsLoadAndShowThisAdUnitNow(EAdNetworkType  _adNetworkType, EAdUnitPrice _adUnitPrice) {
        if (md_adNetworkUnit.containsKey(_adNetworkType)) {
            switch (_adUnitPrice) {
                case _HIGH:
                    return true;
                case _MEDIUM:
                    if (md_adNetworkUnit.get(_adNetworkType).get(0).IsAdUnitlLoadingResult() == EAdUnitLoadingResult._READY) {
                        return false;
                    }
                    return true;
                case _LOW:
                    if (md_adNetworkUnit.get(_adNetworkType).get(0).IsAdUnitlLoadingResult() == EAdUnitLoadingResult._READY) {
                        return false;
                    } else if (md_adNetworkUnit.get(_adNetworkType).get(1).IsAdUnitlLoadingResult() == EAdUnitLoadingResult._READY) {
                        return false;
                    }
                    return true;
            }
        }
        return false;
    }
    ////////////////////////////////////////////////////////////// ---  Method  --- //////////////////////////////////////////////////////////////

    public void Show() {
        if (mc_bannerViewActive != null) {
            if(mc_bannerViewActive.IsAdUnitlLoadingResult() == EAdUnitLoadingResult._SHOWING_PAUSE) {
                mc_bannerViewActive.ShowContinue();
            }
        }
        else if(!m_startAwake)
        {
            m_startAwake = true;
            ScenarioBanner.getInstance().PullStartAwake();
        }
    }

    public void Hide() {
        if (mc_bannerViewActive != null) {
            mc_bannerViewActive.SetPause(true);
        }
    }

    public void SetGravity(int _gravity) {

        m_gravity = EGravity.GetName(_gravity);
        DI.m_activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                YFragments.getInstance().SetGravity();
                for(Map.Entry<EAdNetworkType, ArrayList<BannerView>> _item : md_adNetworkUnit.entrySet())
                {
                    if(EAdNetworkType.GetInt(_item.getKey()) > 2 && _item.getKey() != EAdNetworkType._ERROR) {
                        Iterator<BannerView> _iterator = _item.getValue().iterator();
                        while (_iterator.hasNext()) {
                            _iterator.next().SetGravity(m_gravity);
                        }
                    }
                }
            }
        });
    }

    public void BannerSetBackground(boolean _isBackgroundShow) {
        YBannerBackground.getInstance().BannerSetBackground(_isBackgroundShow);
    }

    public void BannerSetBackgroundColor(String _color) {
        YBannerBackground.getInstance().BannerSetBackgroundColor(_color);
    }


}

