package com.yovoads.yovoplugin.implementations.banner;


import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.EWwwCommand;
import com.yovoads.yovoplugin.common.IAdUnitOnMethod;
import com.yovoads.yovoplugin.common.IYHttpConnectResult;
import com.yovoads.yovoplugin.common.EGravity;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.EAdUnitType;
import com.yovoads.yovoplugin.implementations.AdUnitData;
import com.yovoads.yovoplugin.implementations.AdUnitTypeMode;
import com.yovoads.yovoplugin.implementations.AdTypeBaseCommon;
import com.yovoads.yovoplugin.implementations.HttpRequests;
import com.yovoads.yovoplugin.implementations.IOnEventLoaderPicture;
import com.yovoads.yovoplugin.implementations.LoaderPicture;

public class YBanner extends AdTypeBaseCommon implements IYHttpConnectResult, IOnEventLoaderPicture
{

    private YBanner mc_this = null;
    private IAdUnitOnMethod m_callback = null;


    public YBanner(EAdNetworkType _adNetworkYovo, IAdUnitOnMethod _callback)
    {
        me_adNetworkYovo = _adNetworkYovo;
        m_callback = _callback;
        m_adUnitData = new AdUnitData();
        mc_this = (YBanner)this;
    }

    public void SetGravity(final EGravity _gravity)
    {
        // empty
    }

    public void Load(int _idRule) {
        switch (me_adNetworkYovo) {
            case _CROSS_PROMOTION:
                HttpRequests.SendCrossPromo(EWwwCommand._YOVO_NETWORK_GET, this, EAdUnitType._BANNER, _idRule);
                break;
            case _EXCHANGE:
                HttpRequests.SendExchange(EWwwCommand._YOVO_NETWORK_GET, this, EAdUnitType._BANNER, _idRule);
                break;
            case _YOVO_ADVERTISING:
                HttpRequests.SendYovoAdvertising(EWwwCommand._YOVO_NETWORK_GET, this, EAdUnitType._BANNER, _idRule);
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
                    if(m_adUnitData.m_urlIcon != null) {
                        new LoaderPicture(AdUnitTypeMode._ICON, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, m_adUnitData.m_urlIcon);
                    } else {
                        new LoaderPicture(AdUnitTypeMode._IMAGE_ONLY, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, m_adUnitData.m_urlIcon);
                    }
                }
                else
                {
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
        YovoSDK.ShowLog("YovoBanners", "cross.yovoads = " + _command);
    }

    @Override
    public void ResultError(EWwwCommand _wwwComand, String _command)
    {
        YovoSDK.ShowLog("YovoBanners", "cross.yovoads = " + _command);
    }

    @Override
    public void OnLoading(AdUnitTypeMode _adUnitTypeMode, Bitmap _bitmap) {

        if(_adUnitTypeMode == AdUnitTypeMode._ICON) {
            m_adUnitData.m_bitmapIcon = _bitmap;
            new LoaderPicture(AdUnitTypeMode._SCREEN,this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, m_adUnitData.m_urlScreen);
        }
        else if(_adUnitTypeMode == AdUnitTypeMode._SCREEN || _adUnitTypeMode == AdUnitTypeMode._IMAGE_ONLY)
        {
            m_adUnitData.m_bitmapScreen = _bitmap;
            m_callback.OnSetLoadingAdUnitId(m_adUnitData.m_id);
            m_callback.OnAdLoaded();
        }
    }

    @Override
    public void OnLoadingFailed(int _error) {
        YovoSDK.ShowLog("OnLoadingFailed", m_adUnitData.m_id);
        m_callback.OnAdFailedToLoad(String.valueOf(_error));
    }


    public void Show()
    {
        YBannerView.getInstance().Show(m_adUnitData, m_callback);
    }

    public void Hide()
    {
        YBannerView.getInstance().Hide();
    }



}
