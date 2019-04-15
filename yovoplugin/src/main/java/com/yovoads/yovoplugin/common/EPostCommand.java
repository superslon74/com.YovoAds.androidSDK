package com.yovoads.yovoplugin.common;

public enum EPostCommand {
    _TEST,
    _BUNDLE,
    _VERSION_SDK,
    _HEIGHT_DISPLAY,
    _WIDTH_DISPLAY,
    _LANGUAGE_DEVICE,
    _GAID,
    _DID,
    _DEVICE_TYPE,
    _MAKE,
    _MODEL,
    _OPERATING_SYSTEM,
    _OPERATING_SYSTEM_VERSION,
    _CONNECTION_TYPE,
    _MAC,
    _YOB,
    _GENDER,
    // session
    _SESSION_TIME,
    // event adUnit
    _EVENT_TYPE,
    _AD_NETWORK,
    _AD_TYPE,
    _ID_RULE,
    _AD_UNIT_ID,
    _ERROR_TYPE_INT,
    _AD_PRICE_LEVEL,
    _EXTRA_VALUE,
    _IGNORE_REWARD;

    public static String GetString(EPostCommand _value)
    {
        switch (_value)
        {
            case _TEST:
                return "test";
            case _BUNDLE:
                return "bundle";
            case _VERSION_SDK:
                return "ver";
            case _HEIGHT_DISPLAY:
                return "h";
            case _WIDTH_DISPLAY:
                return "w";
            case _LANGUAGE_DEVICE:
                return "lang";
            case _GAID:
                return "gaid";
            case _DID:
                return "did";
            case _DEVICE_TYPE:
                return "devicetype";
            case _MAKE:
                return "make";
            case _MODEL:
                return "model";
            case _OPERATING_SYSTEM:
                return "os";
            case _OPERATING_SYSTEM_VERSION:
                return "osv";
            case _CONNECTION_TYPE:
                return "connectiontype";
            case _MAC:
                return "mac";
            case _YOB:
                return "yob";
            case _GENDER:
                return "gender";
            case _SESSION_TIME:
                return "timersec";
            case _EVENT_TYPE:
                return "eventtype";
            case _AD_NETWORK:
                return "adnetwork";
            case _AD_TYPE:
                return "adtype";
            case _ID_RULE:
                return "ruleID";
            case _AD_UNIT_ID:
                return "adid";
            case _ERROR_TYPE_INT:
                return "errortype";
            case _AD_PRICE_LEVEL:
                return "adpricelevel";
            case _EXTRA_VALUE:
                return "extraValue";
            case _IGNORE_REWARD:
                return "ignore";

        }
        return "";
    }
}
