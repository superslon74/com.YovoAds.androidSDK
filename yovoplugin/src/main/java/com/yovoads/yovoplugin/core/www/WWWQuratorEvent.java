package com.yovoads.yovoplugin.core.www;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.EAdUnitAdLoadingFailed;
import com.yovoads.yovoplugin.common.EAdUnitPrice;
import com.yovoads.yovoplugin.common.EAdUnitType;
import com.yovoads.yovoplugin.common.EPostCommand;
import com.yovoads.yovoplugin.common.EWwwCommand;
import com.yovoads.yovoplugin.common.IYHttpConnectResult;

public class WWWQuratorEvent extends WWWBase {


    public WWWQuratorEvent(EWwwCommand _wwwCommand, IYHttpConnectResult _iYHttpConnectResult) {
        super(_wwwCommand, _iYHttpConnectResult);
    }

public void SendEventLoadFailed(EAdNetworkType _adNetworkType, EAdUnitType _adUnitType, EAdUnitPrice _adUnitPrice, String _adUnitID, EAdUnitAdLoadingFailed _adUnitAdLoadingFailed)
{
    String _postParams = EPostCommand.GetString(EPostCommand._BUNDLE) + "=" + DI.m_packageName;
    _postParams += "&" + EPostCommand.GetString(EPostCommand._DID) + "=" + DI._DID;
    _postParams += "&" + EPostCommand.GetString(EPostCommand._GAID) + "=" + DI._GAID;
    _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_NETWORK) + "=" + EAdNetworkType.GetInt(_adNetworkType);
    _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_TYPE) + "=" + EAdUnitType.GetInt(_adUnitType);
    _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_PRICE_LEVEL) + "=" + EAdUnitPrice.GetInt(_adUnitPrice);
    _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_UNIT_ID) + "=" + _adUnitID;
    _postParams += "&" + EPostCommand.GetString(EPostCommand._ERROR_TYPE_INT) + "=" + EAdUnitAdLoadingFailed.GetInt(_adUnitAdLoadingFailed);

    execute(_postParams);
}

    public void SendEventShowing(EAdNetworkType _adNetworkType, EAdUnitType _adUnitType, EAdUnitPrice _adUnitPrice, int _idRule, String _adUnitID)
    {
        String _postParams = EPostCommand.GetString(EPostCommand._BUNDLE) + "=" + DI.m_packageName;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._DID) + "=" + DI._DID;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._GAID) + "=" + DI._GAID;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_NETWORK) + "=" + EAdNetworkType.GetInt(_adNetworkType);
        _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_TYPE) + "=" + EAdUnitType.GetInt(_adUnitType);
        _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_PRICE_LEVEL) + "=" + EAdUnitPrice.GetInt(_adUnitPrice);
        _postParams += "&" + EPostCommand.GetString(EPostCommand._ID_RULE) + "=" + _idRule;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_UNIT_ID) + "=" + _adUnitID;

        execute(_postParams);
    }

    public void SendEventShowError(/*EAdUnitType _adUnitType, String _adUnitID, int _adUnitPrice*/)
    {
//        String _postParams = EPostCommand.GetString(EPostCommand._BUNDLE) + "=" + DI.m_packageName;
//        _postParams += "&" + EPostCommand.GetString(EPostCommand._DID) + "=" + DI._DID;
//        _postParams += "&" + EPostCommand.GetString(EPostCommand._GAID) + "=" + DI._GAID;
//        _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_NETWORK) + "=" + EAdNetworkType.getInt(_adNetworkType);
//        _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_TYPE) + "=" + EAdUnitType.GetInt(_adUnitType);
//        _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_UNIT_ID) + "=" + _adUnitID;
//        _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_PRICE_LEVEL) + "=" + EAdUnitPrice.GetInt(_adUnitPrice);
//
//        execute(_postParams);
    }

    public void SendEventClick(EAdNetworkType _adNetworkType, EAdUnitType _adUnitType, EAdUnitPrice _adUnitPrice, int _idRule, String _adUnitID)
    {
        String _postParams = EPostCommand.GetString(EPostCommand._BUNDLE) + "=" + DI.m_packageName;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._DID) + "=" + DI._DID;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._GAID) + "=" + DI._GAID;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_NETWORK) + "=" + EAdNetworkType.GetInt(_adNetworkType);
        _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_TYPE) + "=" + EAdUnitType.GetInt(_adUnitType);
        _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_PRICE_LEVEL) + "=" + EAdUnitPrice.GetInt(_adUnitPrice);
        _postParams += "&" + EPostCommand.GetString(EPostCommand._ID_RULE) + "=" + _idRule;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_UNIT_ID) + "=" + _adUnitID;

        execute(_postParams);
    }

    public void SendEventRewarded(EAdNetworkType _adNetworkType, EAdUnitPrice _adUnitPrice, int _idRule, String _adUnitID, boolean _isIgnore)
    {
        String _postParams = EPostCommand.GetString(EPostCommand._BUNDLE) + "=" + DI.m_packageName;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._DID) + "=" + DI._DID;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._GAID) + "=" + DI._GAID;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_NETWORK) + "=" + EAdNetworkType.GetInt(_adNetworkType);
        _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_PRICE_LEVEL) + "=" + EAdUnitPrice.GetInt(_adUnitPrice);
        _postParams += "&" + EPostCommand.GetString(EPostCommand._ID_RULE) + "=" + _idRule;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_UNIT_ID) + "=" + _adUnitID;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._IGNORE_REWARD) + "=" + (_isIgnore ? 1 : 0);

        execute(_postParams);
    }
}
