package com.yovoads.yovoplugin.implementations;


import android.os.AsyncTask;

import com.yovoads.yovoplugin.common.EAdUnitType;
import com.yovoads.yovoplugin.common.EWwwCommand;
import com.yovoads.yovoplugin.common.IYHttpConnectResult;

public final class HttpRequests
{

    private static String m_urlCrossPromo = "http://cross.yovoads.com/";
    private static String m_urlExchange = "http://exc.yovoads.com/";
    private static String m_urlYovoAdvertising = "http://ads.yovoads.com/";
    //http://rd.yovoads.com/test


    public synchronized static void SendCrossPromo(EWwwCommand _wwwCommand, IYHttpConnectResult _iYHttpConnectResult, EAdUnitType _adUnitType, int _idRule)
    {
        new HttpBase(_wwwCommand, m_urlCrossPromo, _iYHttpConnectResult, _adUnitType).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, _idRule);
    }

    public synchronized static void SendExchange(EWwwCommand _wwwCommand, IYHttpConnectResult _iYHttpConnectResult, EAdUnitType _adUnitType, int _idRule)
    {
        new HttpBase(_wwwCommand, m_urlExchange, _iYHttpConnectResult, _adUnitType).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, _idRule);
    }

    public synchronized static void SendYovoAdvertising(EWwwCommand _wwwCommand, IYHttpConnectResult _iYHttpConnectResult, EAdUnitType _adUnitType, int _idRule)
    {
        new HttpBase(_wwwCommand, m_urlYovoAdvertising, _iYHttpConnectResult, _adUnitType).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,_idRule);
    }
}
