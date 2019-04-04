package com.yovoads.yovoplugin.core.www;

import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.EAdUnitType;
import com.yovoads.yovoplugin.core.dbLocal;
import com.yovoads.yovoplugin.core.www.extra._QuratorSetting;
import com.yovoads.yovoplugin.core.www.extra._QuratorSettingScenario;
import com.yovoads.yovoplugin.core.www.extra._QuratorSettingScenarioRule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonParser {

    public static boolean IsOK(String _json)  {
        try
        {
            if (new JSONObject(_json).getString("error").isEmpty()) {
                return true;
            }
        } catch (JSONException e) {
            return false;
        }

        return false;
    }

    public static _QuratorSetting GetQuratorSetting(String _json)
    {
        _QuratorSetting _quratorTemp = new _QuratorSetting();
        try
        {
            JSONObject _obj = new JSONObject(_json);
            _quratorTemp.m_error = _obj.getString("error");

            if(_quratorTemp.m_error.isEmpty()) {

                JSONObject _obj2 = _obj.getJSONObject("data");
                _quratorTemp.m_modified = _obj2.getLong(dbLocal.mk_Modified);
                _quratorTemp.SetStringScenarioQueue(_obj2.getJSONArray(dbLocal.mk_Queue).toString());
                GetScenarioQueue(_quratorTemp.ml_quratorSettingScenario, _obj2.getJSONArray(dbLocal.mk_Queue));

                _obj2 = _obj.getJSONObject("settings");
                JSONObject _obj3 = _obj2.getJSONObject("GlobalSettings");
                _quratorTemp.m_sessionPeriod = _obj3.getInt(dbLocal.mk_sessionPeriod);
                _quratorTemp.m_updateScenario = _obj3.getInt(dbLocal.mk_updateScenario);
                _quratorTemp.SetStringAdNetwork(_obj2.getJSONArray(dbLocal.mk_adNetworks).toString());
                GetAdNetworkAvailable(_quratorTemp.md_quratorAdNetworksAvailable, _obj2.getJSONArray(dbLocal.mk_adNetworks));
            }
        } catch (JSONException e) {
            _quratorTemp.m_error = e.getMessage();
            return  _quratorTemp;
        }

        return _quratorTemp;
    }

    public static void GetScenarioQueue(ArrayList<_QuratorSettingScenario> _listQuratorSettingScenario,  JSONArray _scenarioQueue) throws JSONException {
        for (int i = 0; i < _scenarioQueue.length(); i++) {
            _QuratorSettingScenario _queue = new _QuratorSettingScenario();
            _queue.me_adUnitType = EAdUnitType.GetName(_scenarioQueue.getJSONObject(i).getInt("T"));
            JSONArray _Q = _scenarioQueue.getJSONObject(i).getJSONArray("Q");

            for (int ii = 0; ii < _Q.length(); ii++) {
                JSONObject _ruleData = _Q.getJSONObject(ii);
                if(EAdNetworkType.GetName(_ruleData.getInt("ADN")) != EAdNetworkType._ERROR) {
                    _queue.ml_quratorScenarioQueueRule.add(new _QuratorSettingScenarioRule(_ruleData.getInt("ID"), EAdNetworkType.GetName(_ruleData.getInt("ADN")),
                            _ruleData.getInt("Limit"), _ruleData.getInt("Value")));
                }
            }
            _listQuratorSettingScenario.add(_queue);
        }
    }

    public static void GetAdNetworkAvailable(HashMap<EAdUnitType, ArrayList<EAdNetworkType>> _quratorAdNetworksAvailable, JSONArray _adNetworksAvailable) throws JSONException {

        ArrayList<EAdNetworkType> _listBanners = new ArrayList<EAdNetworkType>();
        ArrayList<EAdNetworkType> _listInterstitia = new ArrayList<EAdNetworkType>();
        ArrayList<EAdNetworkType> _listReward = new ArrayList<EAdNetworkType>();

        for (int i = 0; i < _adNetworksAvailable.length(); i++) {
            EAdNetworkType _adNetworksType = EAdNetworkType.GetName(_adNetworksAvailable.getJSONObject(i).getInt("ID"));
            if(_adNetworksType != EAdNetworkType._ERROR) {

                JSONArray _adTypes = _adNetworksAvailable.getJSONObject(i).getJSONArray("AdTypes");

                for (int ii = 0; ii < _adTypes.length(); ii++) {
                    switch (EAdUnitType.GetName(ii)) {
                        case _BANNER:
                            _listBanners.add(_adNetworksType);
                            break;
                        case _INTERSTITIAL:
                            _listInterstitia.add(_adNetworksType);
                            break;
                        case _REWARD:
                            _listReward.add(_adNetworksType);
                            break;
                    }
                }
            }
        }

        _quratorAdNetworksAvailable.put(EAdUnitType._BANNER, _listBanners);
        _quratorAdNetworksAvailable.put(EAdUnitType._INTERSTITIAL, _listInterstitia);
        _quratorAdNetworksAvailable.put(EAdUnitType._REWARD, _listReward);
    }

    public static long GetScenarioCheckUpdate(String _json)
    {
        try {
            JSONObject _obj = new JSONObject(_json);

            if(_obj.getString("error").isEmpty()) {
                return _obj.getJSONObject("data").getLong(dbLocal.mk_Modified);
            }
        } catch (JSONException e) {
            YovoSDK.ShowLog("JsonParserERROR", "GetScenarioCheckUpdate");
        }

        return -99;
    }

    public static EAdUnitType GetScenarioReset(String _json)
    {
        try {
            JSONObject _obj = new JSONObject(_json);

            if(_obj.getString("error").isEmpty()) {
                return EAdUnitType.GetName(_obj.getInt("adtype"));
            }
        } catch (JSONException e) {
            YovoSDK.ShowLog("JsonParserERROR", "GetScenarioCheckUpdate");
        }
        return EAdUnitType._ERROR;
    }

    public static void RewardSetData(String _json)
    {
        try {
            JSONObject _obj = new JSONObject(_json);

            if(_obj.getString("error").isEmpty()) {
                _obj = _obj.getJSONObject("rewarddata");
                dbLocal.getInstance().RewardSetData(_obj.getInt("MaxCountPerDay"), _obj.getInt("Shows24"), _obj.getInt("MinimumPeriodSec"), _obj.getInt("NextShow"));
            }
        } catch (JSONException e) {
            YovoSDK.ShowLog("JsonParserERROR", "OnRewarded");
        }
    }

}
