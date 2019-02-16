package com.yovoads.yovoplugin.core.www.extra;


import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.EAdUnitType;
import com.yovoads.yovoplugin.common.dbLocal;
import com.yovoads.yovoplugin.core.www.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class _QuratorSetting {

    public String m_error = "";
    public long m_modified = 0;
    public int m_sessionPeriod = 21;


    public ArrayList<_QuratorSettingScenario> ml_quratorSettingScenario = new ArrayList<_QuratorSettingScenario>();
    public HashMap<EAdUnitType, ArrayList<EAdNetworkType>> md_quratorAdNetworksAvailable = new HashMap<EAdUnitType, ArrayList<EAdNetworkType>>();

    private String m_stringAdNetwork = "";
    private String m_stringScenarioQueue = "";

    public void LoadingLocalData() {
        try {


            m_error = "";
            m_modified = dbLocal.getInstance().GetScenarioModified();
            m_sessionPeriod = dbLocal.getInstance().GetSessionPeriod();

            JsonParser.GetScenarioQueue(ml_quratorSettingScenario, new JSONArray(dbLocal.getInstance().GetScenarioQueue()));
            JsonParser.GetAdNetworkAvailable(md_quratorAdNetworksAvailable, new JSONArray(dbLocal.getInstance().GetAdNetworkAvailable()));
        }
        catch (JSONException e)
        {

        }
    }

    public void SetStringAdNetwork(String _str)
    {
        m_stringAdNetwork = _str;
    }

    public void SetStringScenarioQueue(String _str)
    {
        m_stringScenarioQueue = _str;
    }

    public String GetStringAdNetwork()
    {
        return m_stringAdNetwork;
    }

    public String GetStringScenarioQueue()
    {
        return m_stringScenarioQueue;
    }

}
