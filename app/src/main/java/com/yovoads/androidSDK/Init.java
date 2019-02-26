package com.yovoads.androidSDK;

import android.app.Activity;
import android.os.SystemClock;

import com.yovoads.yovoplugin.IOnUnitySDK;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.EGravity;

import java.sql.Time;

public class Init
{

    public static boolean m_isCOPPA = true;
    public static boolean m_isTesting = false;
    public static boolean m_bannerStartAwake = true;
    public static boolean m_isBackground = true;
    public static EGravity m_gravity = EGravity._BOTTON;
    public static String m_color = "#FF0000";

    public static void initYovoSDK(Activity _activity)
    {
        MainActivity.m_yovoSDK = YovoSDK.getInstance(_activity, new IOnUnitySDK() {


            @Override
            public void OnSetGaid() {

                MainActivity.m_yovoSDK.ScenarioInit();
                MainActivity.m_yovoSDK.SetNetworksParams(true,true, false, true, false, false);
                MainActivity.m_yovoSDK.AddNetworkAdmobData("ca-app-pub-3940256099942544~3347511713",
                        "ca-app-pub-3940256099942544/6300978111","","",
                        "ca-app-pub-3940256099942544/1033173712","","", "ca-app-pub-3940256099942544/5224354917");
                MainActivity.m_yovoSDK.AddNetworkFacebookData("2297129613849018",
                        "YOUR_PLACEMENT_ID","2297129613849018_2297135987181714","",
                        "YOUR_PLACEMENT_ID","2297129613849018_2297139323848047","", "");
                MainActivity.m_yovoSDK.AddNetworkUnityAdsData("14851");
                MainActivity.m_yovoSDK.CreateBannerXml(m_bannerStartAwake, EGravity.GetIndex(m_gravity), m_isBackground, m_color);

                MainActivity.m_yovoSDK.InterstitialSetPeriodShow(0);

                MainActivity.m_yovoSDK.Start();

            }

            @Override
            public void OnRewarded(boolean _isRewarded, String _key, String _value) {
                YovoSDK.ShowLog("Main", String.valueOf(_isRewarded) + " <> " + _key + " <> " + _value);
            }
        }, "2", "12345678901234567890123456789012", "com.YovoGames.aircraft", m_isCOPPA, m_isTesting); // com.androidSDK.example

        MainActivity.m_yovoSDK.SetGaid();





    }
}
