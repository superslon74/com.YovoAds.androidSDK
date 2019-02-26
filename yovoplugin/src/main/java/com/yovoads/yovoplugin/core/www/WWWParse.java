package com.yovoads.yovoplugin.core.www;

import android.util.Log;

import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.EWwwCommand;
import com.yovoads.yovoplugin.common.IYHttpConnectResult;
import com.yovoads.yovoplugin.common.EAdUnitType;
import com.yovoads.yovoplugin.core.Scenario.ScenarioBanner;
import com.yovoads.yovoplugin.core.Scenario.ScenarioInterstitial;
import com.yovoads.yovoplugin.core.Scenario.ScenarioReward;
import com.yovoads.yovoplugin.core.YTimer;
import com.yovoads.yovoplugin.core.www.extra._QuratorSetting;
import com.yovoads.yovoplugin.core.www.extra._QuratorSettingScenario;
import com.yovoads.yovoplugin.common.dbLocal;

public class WWWParse implements IYHttpConnectResult {

    @Override
    public void ResultError(String _command) {

    }

    @Override
    public void ResultError(EWwwCommand _wwwCommand, String _command) {

    }

    @Override
    public void ResultOk(EWwwCommand _wwwCommand, String _json) {

        switch(_wwwCommand)
        {
            case _ERROR:

                break;
            case _QURATOR_INIT:
                ScenarioCheck(_json, true);
                WWWRequest.getInstance().SendRewardGetData();
                break;
            case _SCENARIO_GET:
                ScenarioCheck(_json, false);
                break;
            case _SCENARIO_CHECK_UPDATE:
                ScenarioCheckUpdate(_json);
                break;
            case _SCENARIO_RESET:
                ScenarioReset(_json);
            case _REWARD_GET_DATA:
                JsonParser.RewardSetData(_json);
                break;
            case _EVENT_REWARD:
                JsonParser.RewardSetData(_json);
                break;
            case _SEND_SESSION_TIME:
                // empty
                break;


        }
    }

    private void ScenarioCheck(String _json, boolean _isInit) {
        _QuratorSetting _quratorWww = JsonParser.GetQuratorSetting(_json);
        _QuratorSetting _quratorDefault = new _QuratorSetting();
        _quratorDefault.LoadingLocalData();

        if(!_quratorWww.m_error.isEmpty())
        {
            _quratorWww = null;
            YovoSDK.ShowLog("WWWParse","Loading Default Scenario");
            YTimer.getInstance().SetSessionPeriod(_quratorDefault.m_sessionPeriod);
            ScenarioSet(_quratorDefault, _isInit);
        }
        else
        {
            _quratorWww.m_sessionPeriod = (_quratorWww.m_sessionPeriod > 9) ? _quratorWww.m_sessionPeriod : 10;

            if(_quratorWww.m_modified > _quratorDefault.m_modified && !_quratorWww.GetStringScenarioQueue().isEmpty())
            {
                YTimer.getInstance().SetSessionPeriod(_quratorWww.m_sessionPeriod);
                dbLocal.getInstance().QuratorUpdateData(_quratorWww.m_modified, _quratorWww.m_sessionPeriod, _quratorWww.GetStringAdNetwork(), _quratorWww.GetStringScenarioQueue());
            }

            _quratorDefault = null;
            ScenarioSet(_quratorWww, _isInit);
        }
    }

    private void ScenarioSet(_QuratorSetting _quratorActive, boolean _isInit)
    {
        for (_QuratorSettingScenario _scenario : _quratorActive.ml_quratorSettingScenario)
        {
            if(_scenario.me_adUnitType == EAdUnitType._BANNER)
            {
                if(ScenarioBanner.getInstance() == null){
                    Log.e("QQQQ", "111");
                }
                if(_scenario.ml_quratorScenarioQueueRule == null){
                    Log.e("QQQQ", "222");
                }
                if(_quratorActive.md_quratorAdNetworksAvailable.get(EAdUnitType._BANNER) == null){
                    Log.e("QQQQ", "333");
                }
                ScenarioBanner.getInstance().SetScenario(_scenario.ml_quratorScenarioQueueRule, _quratorActive.md_quratorAdNetworksAvailable.get(EAdUnitType._BANNER));
            }
            else if(_scenario.me_adUnitType == EAdUnitType._INTERSTITIAL)
            {
                ScenarioInterstitial.getInstance().SetScenario(_scenario.ml_quratorScenarioQueueRule, _quratorActive.md_quratorAdNetworksAvailable.get(EAdUnitType._INTERSTITIAL));
            }
            else if(_scenario.me_adUnitType == EAdUnitType._REWARD)
            {
                ScenarioReward.getInstance().SetScenario(_scenario.ml_quratorScenarioQueueRule, _quratorActive.md_quratorAdNetworksAvailable.get(EAdUnitType._REWARD));
            }
        }
        _quratorActive = null;
    }

    private void ScenarioCheckUpdate(String _json)
    {
        long _modified = JsonParser.GetScenarioCheckUpdate(_json);
        if(_modified > 0)
        {
            if(_modified > dbLocal.getInstance().GetScenarioModified())
            {
                WWWRequest.getInstance().SendScenarioGet();
            }
        }
    }

    private void ScenarioReset(String _json)
    {
        EAdUnitType _adUnitType = JsonParser.GetScenarioReset(_json);
        switch (_adUnitType)
        {
            case _BANNER:
                ScenarioBanner.getInstance().ResetScenario();
                break;
            case _INTERSTITIAL:
                ScenarioInterstitial.getInstance().ResetScenario();
                break;
            case _REWARD:
                ScenarioReward.getInstance().ResetScenario();
                break;
        }
    }




}
