package com.yovoads.yovoplugin;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;

import com.yovoads.yovoplugin.common.EScreenOrientation;

public class DI
{

    private static DI mc_this = null;
    public static Activity m_activity;

    public static String _URL_QURATOR				= "http://q5.yovoads.com/api/";

    public static String _VERSION_SDK = "5.0.1";

    public static String _OPERATING_SYSTEM = "Android";
    public static String _OPERATING_SYSTEM_VERSION = "89";
    public static String _LANGUAGE_DEVICE = "EN";
    public static String _MAKE = "";
    public static String _MODEL = "";
    public static String _MAC = "";
    public static int _GENDER = 2;
    public static int _DEVICE_TYPE = 0;
    public static int _CONNECTION_TYPE = 0;
    public static int _YOB = 0;


    public static String m_yovoAdsAccountId = "2";
    public static String m_yovoAdsToken = "12345678901234567890123456789012";
    public static String m_packageName = "com.YovoAds.example";
    public static boolean m_isCOPPA = true;
    public static boolean m_isTesting = false;


    public static String _DID = "DID ANDROID SDK DEFAULT";
    public static String _GAID = "GAID ANDROID SDK DEFAULT";


    public static float _DISPLAY_DPI = 1;
    public static int _DISPLAY_HEIGHT_DPI = 1;
    public static int _DISPLAY_WIDTH = 0;
    public static float _DISPLAY_RATIO = 16f / 9f;
    public static int _DISPLAY_HEIGHT = 0;
    public static int _IS_BANNER_HEIGHT_DOUBLE;
    public static EScreenOrientation _SCREEN_ORIENTATION = EScreenOrientation._PORTRAIT;


    public static DI getInstance() {
        if (mc_this == null) {
            mc_this = new DI();
        }
        return mc_this;
    }

    private DI()
    {

    }

    public void Set(Activity _activity, String _yovoAdsAccountId, String _yovoAdsToken, String _packageName, boolean _isCOPPA, boolean _isTesting) {

            m_activity = _activity;

            m_yovoAdsAccountId = _yovoAdsAccountId;
            m_yovoAdsToken = _yovoAdsToken;
            m_packageName = _packageName;
            m_isCOPPA = _isCOPPA;
            m_isTesting = _isTesting;

            _DID = Settings.Secure.getString(m_activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            _OPERATING_SYSTEM_VERSION = String.valueOf(android.os.Build.VERSION.SDK_INT);

            _MAKE = Build.BRAND;
            _MODEL = Build.MODEL;
            _MAC = "mac:mac:mac:mac";
            _GENDER = 2; // MALE = 0  FEMALE =1  OTHER = 2
            _DEVICE_TYPE = 1;
            _CONNECTION_TYPE = 1;
            _YOB = 0;
        //com.google.android.gms.plus.model.people.
            ChangeOrientationDevice();
    }

    public void SetGaid()
    {
        GAID _getData = new GAID();
        _getData.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, m_activity);
    }

    public void OnSetGaid(String _gaid, String _error)
    {
        if(_error.isEmpty()) {
            _GAID = _gaid;
        }
        //_GAID = "___empty___";
        YovoSDK.ShowLog("GAID_error", _error);
        YovoSDK.ShowLog("GAID_GAID", _gaid);
        YovoSDK.ShowLog("GAID_DID", _DID);
        YovoSDK.mi_OnUnitySDK.OnSetGaid();
    }

    private static void ChangeOrientationDevice()
    {
        DisplayMetrics metrics = new DisplayMetrics();
        m_activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        _DISPLAY_WIDTH = metrics.widthPixels;
        _DISPLAY_HEIGHT = metrics.heightPixels;
        _DISPLAY_RATIO = (_DISPLAY_WIDTH < _DISPLAY_HEIGHT) ? ((float)2150 / (float)_DISPLAY_WIDTH) : ((float)_DISPLAY_WIDTH / (float)_DISPLAY_HEIGHT);

        _SCREEN_ORIENTATION = (m_activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ? EScreenOrientation._LANDSCAPE : EScreenOrientation._PORTRAIT;
        YovoSDK.ShowLog("DEVICE_INFO", _SCREEN_ORIENTATION.name());
        //_YOVO_KOEF = (float) _DISPLAY_HEIGHT / (_SCREEN_ORIENTATION ? 1024 : 576);

        _DISPLAY_DPI = m_activity.getResources().getDisplayMetrics().density;
        _DISPLAY_HEIGHT_DPI = (int)(_DISPLAY_HEIGHT / _DISPLAY_DPI);
       _IS_BANNER_HEIGHT_DOUBLE = (_DISPLAY_HEIGHT_DPI) < 720 ? 50 : 90;



        YovoSDK.ShowLog("SCREEN__DISPLAY_WIDTH", String.valueOf(_DISPLAY_WIDTH));
        YovoSDK.ShowLog("SCREEN__DISPLAY_HEIGHT", String.valueOf(_DISPLAY_HEIGHT));
        YovoSDK.ShowLog("SCREEN__DISPLAY_RATIO", String.valueOf(_DISPLAY_RATIO));
        YovoSDK.ShowLog("SCREEN__density", String.valueOf(m_activity.getResources().getDisplayMetrics().density));
        YovoSDK.ShowLog("SCREEN__densityDpi", String.valueOf(m_activity.getResources().getDisplayMetrics().densityDpi));
//        YovoSDK.ShowLog("SCREEN__heightPixels", String.valueOf(m_activity.getResources().getDisplayMetrics().heightPixels));
//        YovoSDK.ShowLog("SCREEN__widthPixels", String.valueOf(m_activity.getResources().getDisplayMetrics().widthPixels));
//        YovoSDK.ShowLog("SCREEN__scaledDensity", String.valueOf(m_activity.getResources().getDisplayMetrics().scaledDensity));
//        YovoSDK.ShowLog("SCREEN__xdpi", String.valueOf(m_activity.getResources().getDisplayMetrics().xdpi));
//        YovoSDK.ShowLog("SCREEN__ydpi", String.valueOf(m_activity.getResources().getDisplayMetrics().ydpi));
//        YovoSDK.ShowLog("IS_BANNER_HEIGHT_DOUBLE", String.valueOf(_IS_BANNER_HEIGHT_DOUBLE));
    }

    public int GetBannerHeight()
    {
        return  (int)GetBannerHeight_float();
    }

    public float GetBannerHeight_float()
    {
        return  _IS_BANNER_HEIGHT_DOUBLE * DI._DISPLAY_DPI;
    }






}
