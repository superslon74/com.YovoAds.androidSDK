package com.yovoads.yovoplugin.implementations.reward;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.EAdUnitType;
import com.yovoads.yovoplugin.common.EWwwCommand;
import com.yovoads.yovoplugin.common.IAdUnitOnMethodReward;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.IYHttpConnectResult;
import com.yovoads.yovoplugin.implementations.AdUnitData;
import com.yovoads.yovoplugin.implementations.AdUnitTypeMode;
import com.yovoads.yovoplugin.implementations.AdTypeBaseCommon;
import com.yovoads.yovoplugin.implementations.HttpRequests;
import com.yovoads.yovoplugin.implementations.IOnEventLoaderPicture;
import com.yovoads.yovoplugin.implementations.LoaderPicture;

public class YReward extends AdTypeBaseCommon implements IYHttpConnectResult, IOnEventLoaderPicture
{

    private IAdUnitOnMethodReward m_callback;
    public static IAdUnitOnMethodReward m_callbackActive = null;
    public static AdUnitData m_adUnitDataActive = null;
    public static EAdNetworkType me_adNetworkYovoActive = null;

    public YReward(EAdNetworkType _adNetworkYovo, IAdUnitOnMethodReward _callback)
    {
        me_adNetworkYovo = _adNetworkYovo;
        m_callback = _callback;
        m_adUnitData = new AdUnitData();
    }

    public void Load(int _idRule) {
        switch (me_adNetworkYovo) {
            case _CROSS_PROMOTION:
                HttpRequests.SendCrossPromo(EWwwCommand._YOVO_NETWORK_GET, this, EAdUnitType._REWARD, _idRule);
                break;
            case _EXCHANGE:
                HttpRequests.SendExchange(EWwwCommand._YOVO_NETWORK_GET, this, EAdUnitType._REWARD, _idRule);
                break;
            case _YOVO_ADVERTISING:
                HttpRequests.SendYovoAdvertising(EWwwCommand._YOVO_NETWORK_GET, this, EAdUnitType._REWARD, _idRule);
                break;
        }
    }

    @Override
    public void ResultOk(EWwwCommand _wwwCommand, String _json)
    {
        switch (_wwwCommand)
        {
            case _YOVO_NETWORK_GET:
                if(m_adUnitData.Set(_json).isEmpty()) {
                    new LoaderPicture(AdUnitTypeMode._SCREEN,this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, m_adUnitData.m_urlScreen);
                } else {
                    m_callback.OnSetLoadingAdUnitId("");
                    m_callback.OnAdFailedToLoad(m_adUnitData.Set(_json));
                }
                break;
            case _ERROR:
                switch (_json)
                {
                    case "init":
                        //SystemClock.sleep(300);
                        break;
                }

                break;
        }
    }

    @Override
    public void ResultError(String _command)
    {
        YovoSDK.ShowLog("YovoReward", ".yovoads = " + _command);
    }

    @Override
    public void ResultError(EWwwCommand _wwwComand, String _command)
    {
        YovoSDK.ShowLog("YovoReward", ".yovoads = " + _command);
    }

    @Override
    public void OnLoading(AdUnitTypeMode _adUnitTypeMode, Bitmap _bitmap) {

        if(_adUnitTypeMode == AdUnitTypeMode._SCREEN)
        {
            m_adUnitData.m_bitmapScreen = _bitmap;
            new YRewardLoader(AdUnitTypeMode._VIDEO, me_adNetworkYovo,this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,m_adUnitData.m_urlIcon);

        } else {
            m_callback.OnSetLoadingAdUnitId(m_adUnitData.m_id);
            m_callback.OnAdLoaded();
        }
    }

    @Override
    public void OnLoadingFailed(int _error) {
        YovoSDK.ShowLog("OnLoadingFailed", m_adUnitData.m_id);
        m_callback.OnAdFailedToLoad(String.valueOf(_error));
    }

    public void Show() {
        m_adUnitDataActive = m_adUnitData;
        m_callbackActive = m_callback;
        me_adNetworkYovoActive = me_adNetworkYovo;
        DI.m_activity.startActivity(new Intent(DI.m_activity, YRewardView.class));
    }

}
