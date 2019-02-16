package com.yovoads.yovoplugin.common;

public enum EAdUnitAdLoadingFailed {

    _INTERNET_ERROR(0),


    _ERROR_TEMP(99);

    EAdUnitAdLoadingFailed(int _index)
    {

    }

    public static int GetInt(EAdUnitAdLoadingFailed _adUnitAdLoadingFailed)
    {
        switch (_adUnitAdLoadingFailed)
        {
            case _INTERNET_ERROR:
                return 0;
        }

        return 99;
    }
}
