package com.yovoads.yovoplugin.networks;

import com.facebook.ads.AdSettings;
import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;

public class Facebook
{

    public static String    m_facebookAppId;

    public static void AddNetwork(String _facebookAppId)
    {
        m_facebookAppId = _facebookAppId;

        if (DI.m_isTesting) {
            AdSettings.addTestDevice(_facebookAppId);
            //AdSettings.addTestDevice("HASHED ID");
        }
    }
}
