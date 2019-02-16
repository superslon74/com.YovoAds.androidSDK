package com.yovoads.yovoplugin.common;

public enum EWwwCommand {

    _QURATOR_INIT,

    _SCENARIO_CHECK_UPDATE,
    _SCENARIO_GET,
    _SCENARIO_RESET,

    _REWARD_GET_DATA,

    _SEND_SESSION_TIME,

    _EVENT_LOADING,
    _EVENT_LOADFAILED,
    _EVENT_SHOWING,
    _EVENT_SHOWERROR,
    _EVENT_CLICK,
    _EVENT_REWARD,

    _YOVO_NETWORK_GET,

    _ERROR;


    public static String GetValue(EWwwCommand _name)
    {
        switch (_name)
        {
            case _QURATOR_INIT:
                return "init";
            case _SCENARIO_CHECK_UPDATE:
                return "scd";
            case _SCENARIO_GET:
                return "gs";
            case _SCENARIO_RESET:
                return "reset";
            case _REWARD_GET_DATA:
                return "adRewardData";
            case _SEND_SESSION_TIME:
                return "s";
            case _EVENT_LOADING:
                return "adLoaded";
            case _EVENT_LOADFAILED:
                return "eventAdLoadFailed";
            case _EVENT_SHOWING:
                return "eventAdShow";
            case _EVENT_SHOWERROR:
                return "eventAdShowError";
            case _EVENT_CLICK:
                return "eventAdClick";
            case _EVENT_REWARD:
                return "eventAdReward";
            case _YOVO_NETWORK_GET:
                return "get";
            case _ERROR:
                return "err";
        }

        return "err";
    }

}
