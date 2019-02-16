package com.yovoads.yovoplugin.common;

public enum EAdUnitPrice {

    _LOW(0),
    _MEDIUM(1),
    _HIGH(2),

    _ERROR(99);

    EAdUnitPrice(int _index)
    {}

    public static EAdUnitPrice GetName(int _index)
    {
        switch (_index)
        {
            case 0:
                return _LOW;
            case 1:
                return _MEDIUM;
            case 2:
                return _HIGH;
        }

        return EAdUnitPrice._LOW;
    }

    public static int GetInt(EAdUnitPrice _adUnitPrice)
    {
        switch (_adUnitPrice)
        {
            case _LOW:
                return 0;
            case _MEDIUM:
                return 1;
            case _HIGH:
                return 2;
        }

        return 99;
    }
}

