package com.yovoads.yovoplugin.core.www.extra;

import com.yovoads.yovoplugin.common.EAdNetworkType;

public class _QuratorSettingScenarioRule {

    private int m_idRule = 0;
    private EAdNetworkType me_adNetworkType = null;
    private int m_countShowingLimit = -1;
    private int m_countShowingRemain = 0;

    private _QuratorSettingScenarioRule()
    {

    }

    public _QuratorSettingScenarioRule(int _idRule, EAdNetworkType _adNetworkType, int _countShowingLimit, int _countShowingRemain)
    {
        m_idRule = _idRule;
        me_adNetworkType = _adNetworkType;
        m_countShowingLimit = _countShowingLimit;
        m_countShowingRemain = _countShowingRemain;
    }

    public int GetIdRule()
    {
        return m_idRule;
    }

    public EAdNetworkType GetNetworkType()
    {
        return me_adNetworkType;
    }

    public int GetCountShowingLimit()
    {
        return m_countShowingLimit;
    }

    public int GetCountShowingRemain()
    {
        return m_countShowingRemain;
    }

}
