package com.yovoads.yovoplugin.core.www;


import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.common.EPostCommand;
import com.yovoads.yovoplugin.common.EWwwCommand;
import com.yovoads.yovoplugin.common.IYHttpConnectResult;
import com.yovoads.yovoplugin.common.EAdUnitType;


public class WWWQurator extends WWWBase {


    public WWWQurator(EWwwCommand _wwwCommand, IYHttpConnectResult _iYHttpConnectResult) {
        super(_wwwCommand, _iYHttpConnectResult);
    }


    public void SendQurator_InitGetCheck()
    {
        String _postParams = EPostCommand.GetString(EPostCommand._TEST) + "=" + ((DI.m_isTesting) ? 1 : 0);
        _postParams += "&" + EPostCommand.GetString(EPostCommand._BUNDLE) + "=" + DI.m_packageName;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._VERSION_SDK) + "=" + DI._VERSION_SDK;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._OPERATING_SYSTEM) + "=" + DI._OPERATING_SYSTEM;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._HEIGHT_DISPLAY) + "=" + DI._DISPLAY_HEIGHT;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._WIDTH_DISPLAY) + "=" + DI._DISPLAY_WIDTH;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._DID) + "=" + DI._DID;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._GAID) + "=" + DI._GAID;

        if(!DI.m_isCOPPA)
        {
            _postParams += "&" + EPostCommand.GetString(EPostCommand._LANGUAGE_DEVICE) + "=" + DI._LANGUAGE_DEVICE;
            _postParams += "&" + EPostCommand.GetString(EPostCommand._DEVICE_TYPE) + "=" + DI._DEVICE_TYPE;
            _postParams += "&" + EPostCommand.GetString(EPostCommand._MAKE) + "=" + DI._MAKE;
            _postParams += "&" + EPostCommand.GetString(EPostCommand._MODEL) + "=" + DI._MODEL;
            _postParams += "&" + EPostCommand.GetString(EPostCommand._OPERATING_SYSTEM_VERSION) + "=" + DI._OPERATING_SYSTEM_VERSION;
            _postParams += "&" + EPostCommand.GetString(EPostCommand._CONNECTION_TYPE) + "=" + DI._CONNECTION_TYPE;
            _postParams += "&" + EPostCommand.GetString(EPostCommand._MAC) + "=" + DI._MAC;
            _postParams += "&" + EPostCommand.GetString(EPostCommand._YOB) + "=" + DI._YOB;
            _postParams += "&" + EPostCommand.GetString(EPostCommand._GENDER) + "=" + DI._GENDER;
        }

        execute(_postParams);
    }

    public void SendQurator_Reset(EAdUnitType _adUnitType)
    {
        String _postParams = EPostCommand.GetString(EPostCommand._BUNDLE) + "=" + DI.m_packageName;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._DID) + "=" + DI._DID;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._GAID) + "=" + DI._GAID;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_TYPE) + "=" + EAdUnitType.GetInt(_adUnitType);

        execute(_postParams);
    }

    public void SendRewardGetData()
    {
        String _postParams = EPostCommand.GetString(EPostCommand._BUNDLE) + "=" + DI.m_packageName;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._DID) + "=" + DI._DID;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._GAID) + "=" + DI._GAID;

        execute(_postParams);
    }

    public void SendSessionTime(int _sessionTime)
    {
        String _postParams =  EPostCommand.GetString(EPostCommand._BUNDLE) + "=" + DI.m_packageName;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._DID) + "=" + DI._DID;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._GAID) + "=" + DI._GAID;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._SESSION_TIME) + "=" + _sessionTime;

        execute(_postParams);
    }
}
