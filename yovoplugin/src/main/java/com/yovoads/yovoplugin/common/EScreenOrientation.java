package com.yovoads.yovoplugin.common;

public enum  EScreenOrientation {

    _LANDSCAPE(0),
    _PORTRAIT(1);

    EScreenOrientation(int _index)
    {

    }

    public static int GetInt(EScreenOrientation _screenOrientation)
    {
        switch (_screenOrientation)
        {
            case _LANDSCAPE:
                return 0;
            case _PORTRAIT:
                return 1;
        }

        return 1;
    }
}
