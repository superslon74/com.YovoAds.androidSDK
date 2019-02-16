package com.yovoads.yovoplugin.networks;

import com.google.android.gms.ads.MobileAds;
import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;

public class Admob
{
    public static void AddNetwork(String _appId)
    {
        MobileAds.initialize(DI.m_activity, _appId);
    }
}
