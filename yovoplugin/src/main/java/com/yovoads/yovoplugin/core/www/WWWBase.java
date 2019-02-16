package com.yovoads.yovoplugin.core.www;

import android.os.AsyncTask;
import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.common.EWwwCommand;
import com.yovoads.yovoplugin.common.IYHttpConnectResult;
import com.yovoads.yovoplugin.common.YHttpConnection;



public class WWWBase extends AsyncTask<String, Void, String>
{

    protected EWwwCommand me_wwwComand = null;
    protected IYHttpConnectResult mi_yHttpConnectResult = null;

    protected WWWBase(EWwwCommand _wwwCommand, IYHttpConnectResult _iYHttpConnectResult) {
        me_wwwComand = _wwwCommand;
        mi_yHttpConnectResult = _iYHttpConnectResult;
    }

    protected String doInBackground(String... _postParams) {
        return YHttpConnection.CreateConnect(DI._URL_QURATOR + EWwwCommand.GetValue(me_wwwComand) + "?id=" + DI.m_yovoAdsAccountId + "&token=" + DI.m_yovoAdsToken, _postParams[0], mi_yHttpConnectResult);
    }

    @Override
    protected void onPostExecute(String _json) {
        super.onPostExecute(_json);
        if (_json == null) {
            mi_yHttpConnectResult.ResultError(EWwwCommand._ERROR, "");
        } else if (JsonParser.IsOK(_json)) {
            mi_yHttpConnectResult.ResultOk(me_wwwComand, _json);
        } else {
            mi_yHttpConnectResult.ResultError(EWwwCommand._ERROR, "");
        }
    }
}