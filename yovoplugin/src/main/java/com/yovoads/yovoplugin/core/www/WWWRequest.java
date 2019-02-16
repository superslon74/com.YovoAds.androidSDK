package com.yovoads.yovoplugin.core.www;

import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.EAdUnitAdLoadingFailed;
import com.yovoads.yovoplugin.common.EAdUnitPrice;
import com.yovoads.yovoplugin.common.EAdUnitType;
import com.yovoads.yovoplugin.common.EWwwCommand;

public class WWWRequest extends WWWParse {

    private static WWWRequest			mc_this;

    public static WWWRequest getInstance() {
        if(mc_this == null)
        {
            mc_this = new WWWRequest();
        }
        return mc_this;
    }

    private WWWRequest() {

    }




    public void SendQuratorInit() {
        new WWWQurator(EWwwCommand._QURATOR_INIT, this).SendQurator_InitGetCheck();
    }

    public void SendScenarioGet() {
        new WWWQurator(EWwwCommand._SCENARIO_GET, this).SendQurator_InitGetCheck();
    }

    public void SendScenarioCheckUpdate() {
        new WWWQurator(EWwwCommand._SCENARIO_CHECK_UPDATE, this).SendQurator_InitGetCheck();
    }

    public void SendScenarioReset(EAdUnitType _adUnitType) {
        new WWWQurator(EWwwCommand._SCENARIO_RESET, this).SendQurator_Reset(_adUnitType);
    }

    public void SendRewardGetData()
    {
        new WWWQurator(EWwwCommand._REWARD_GET_DATA, this).SendRewardGetData();
    }

    public void SendSessionTime(int _sessionTime)
    {
        new WWWQurator(EWwwCommand._SEND_SESSION_TIME, this).SendSessionTime(_sessionTime);
    }


    public void SendEventLoadFailed(EAdNetworkType _adNetworkType, EAdUnitType _adUnitType, EAdUnitPrice _adUnitPrice, String _adUnitID, EAdUnitAdLoadingFailed _adUnitAdLoadingFailed)
    {
        new WWWQuratorEvent(EWwwCommand._EVENT_LOADFAILED, this).SendEventLoadFailed(_adNetworkType, _adUnitType, _adUnitPrice, _adUnitID, _adUnitAdLoadingFailed);
    }

    public void SendEventShowing(EAdNetworkType _adNetworkType, EAdUnitType _adUnitType, EAdUnitPrice _adUnitPrice, int _idRule, String _adUnitID)
    {
        new WWWQuratorEvent(EWwwCommand._EVENT_SHOWING, this).SendEventShowing(_adNetworkType, _adUnitType, _adUnitPrice, _idRule, _adUnitID);
    }

    public void SendEventShowError()
    {
        new WWWQuratorEvent(EWwwCommand._EVENT_SHOWERROR, this).SendEventShowError();
    }

    public void SendEventClick(EAdNetworkType _adNetworkType, EAdUnitType _adUnitType, EAdUnitPrice _adUnitPrice, int _idRule, String _adUnitID)
    {
        new WWWQuratorEvent(EWwwCommand._EVENT_CLICK, this).SendEventClick(_adNetworkType, _adUnitType, _adUnitPrice, _idRule, _adUnitID);
    }

    public void SendEventRewarded(EAdNetworkType _adNetworkType, EAdUnitPrice _adUnitPrice, int _idRule, String _adUnitID, boolean _isIgnore)
    {
        new WWWQuratorEvent(EWwwCommand._EVENT_REWARD, this).SendEventRewarded(_adNetworkType, _adUnitPrice, _idRule, _adUnitID, _isIgnore);
    }
}
