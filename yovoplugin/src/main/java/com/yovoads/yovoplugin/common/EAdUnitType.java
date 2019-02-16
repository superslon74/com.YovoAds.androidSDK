package com.yovoads.yovoplugin.common;

public enum EAdUnitType {

    _BANNER(0),
    _INTERSTITIAL(1),
    _REWARD(2),
    _ERROR(99);

    EAdUnitType(int _index)
    {

    }

    public static EAdUnitType GetName(int _index)
    {
        switch (_index)
        {
            case 0:
                return _BANNER;
            case 1:
                return _INTERSTITIAL;
            case 2:
                return _REWARD;
        }

        return _ERROR;
    }

    public static int GetInt(EAdUnitType _adUnitType)
    {
        switch (_adUnitType)
        {
            case _BANNER:
                return 0;
            case _INTERSTITIAL:
                return 1;
            case _REWARD:
                return 2;
        }

        return 99;
    }
}
