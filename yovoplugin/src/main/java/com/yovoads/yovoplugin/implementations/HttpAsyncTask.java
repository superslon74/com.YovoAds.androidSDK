package com.yovoads.yovoplugin.implementations;

import android.os.AsyncTask;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.common.EPostCommand;
import com.yovoads.yovoplugin.common.IYHttpConnectResult;
import com.yovoads.yovoplugin.core.YBaseHttp;
import com.yovoads.yovoplugin.common.EAdUnitType;
import com.yovoads.yovoplugin.common.EWwwCommand;
import com.yovoads.yovoplugin.core.www.JsonParser;


public class HttpAsyncTask extends AsyncTask<Integer, Void, String>
{
    private String m_url = "";
    private EWwwCommand m_wwwCommand = null;
    private EAdUnitType me_adUnitType;

    private IYHttpConnectResult mi_yHttpConnectResult = null;

    public HttpAsyncTask(EWwwCommand _wwwCommand, String _url, IYHttpConnectResult _yHttpConnectResult, EAdUnitType _adUnitType)
    {
        m_wwwCommand = _wwwCommand;
        m_url = _url + EWwwCommand.GetValue(m_wwwCommand) + "?id=" + DI.m_yovoAdsAccountId + "&token=" + DI.m_yovoAdsToken;
        mi_yHttpConnectResult = _yHttpConnectResult;
        me_adUnitType = _adUnitType;
    }

    @Override
    protected String doInBackground(Integer... _postParams)
    {
        return YBaseHttp.CreateConnect(m_url, GetParamsDefault(_postParams[0], me_adUnitType), mi_yHttpConnectResult);
    }

    private String GetParamsDefault(int _ruleID, EAdUnitType _adUnitType)
    {
        String _postParams = EPostCommand.GetString(EPostCommand._BUNDLE) + "=" + DI.m_packageName;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._DID) + "=" + DI._DID;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._GAID) + "=" + DI._GAID;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._OPERATING_SYSTEM) + "=" + DI._OPERATING_SYSTEM;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._AD_TYPE) + "=" + EAdUnitType.GetInt(_adUnitType);
        _postParams += "&" + EPostCommand.GetString(EPostCommand._ID_RULE) + "=" + _ruleID;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._TEST) + "=" + ((DI.m_isTesting) ? 1 : 0);
        _postParams += "&" + EPostCommand.GetString(EPostCommand._VERSION_SDK) + "=" + DI._VERSION_SDK;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._OPERATING_SYSTEM_VERSION) + "=" + DI._OPERATING_SYSTEM_VERSION;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._WIDTH_DISPLAY) + "=" + DI._DISPLAY_WIDTH;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._HEIGHT_DISPLAY) + "=" + DI._DISPLAY_HEIGHT;
        _postParams += "&" + EPostCommand.GetString(EPostCommand._LANGUAGE_DEVICE) + "=" + DI._LANGUAGE_DEVICE;
        return _postParams;
    }

    @Override
    protected void onPostExecute(String _json) {
        super.onPostExecute(_json);
        if(_json == null) {
            mi_yHttpConnectResult.ResultError(m_wwwCommand, "512458sddfsw");
        } else if (JsonParser.IsOK(_json)) {
            mi_yHttpConnectResult.ResultOk(m_wwwCommand, _json);
        } else {
            mi_yHttpConnectResult.ResultError(m_wwwCommand, _json);
        }
    }
}
