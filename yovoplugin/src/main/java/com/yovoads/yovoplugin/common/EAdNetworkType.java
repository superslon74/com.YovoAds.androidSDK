package com.yovoads.yovoplugin.common;

public enum EAdNetworkType {

    _CROSS_PROMOTION,
    _EXCHANGE,
    _YOVO_ADVERTISING,

    _ADMOB,
    _FACEBOOK,
    _UNITY_ADS,
    _ERROR;


    public static EAdNetworkType GetName(int _index)
    {
        switch (_index)
        {
            case 0:
                return _CROSS_PROMOTION;
            case 1:
                return _EXCHANGE;
            case 2:
                return _YOVO_ADVERTISING;
            case 3:
                return _ADMOB;
            case 4:
                return _FACEBOOK;
            case 5:
                return _UNITY_ADS;
        }

        return _ERROR;
    }

    public static  int GetInt(EAdNetworkType _adNetworkYovo)
    {
        switch (_adNetworkYovo)
        {
            case _CROSS_PROMOTION:
                return 0;
            case _EXCHANGE:
                return 1;
            case _YOVO_ADVERTISING:
                return 2;
        }
        return 99;
    }

    public static String GetValue(EAdNetworkType _adNetworkType)
    {
        switch (_adNetworkType)
        {
            case _CROSS_PROMOTION:
                return "yovocrosspromotion";
            case _EXCHANGE:
                return "yovoexchange";
            case _YOVO_ADVERTISING:
                return "yovoadvertising";
            case _ADMOB:
                return "admob";
            case _FACEBOOK:
                return "facebook";
            case _UNITY_ADS:
                return "unityads";
        }

        return "error";
    }

}
