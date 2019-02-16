package com.yovoads.yovoplugin.networks;


import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;

public class UnityAds
{
    public static String m_gameId;
    private static UnityAdsListener m_unityAdsListener;

    public static void AddNetwork (final String _gameId)
    {
        m_gameId = _gameId;
    }
}



