package com.yovoads.yovoplugin.core.www.extra;

import com.yovoads.yovoplugin.common.EAdNetworkType;

public class _QuratorSettingScenarioRule {

    private int m_idRule = 0;
    private EAdNetworkType me_adNetworkType = null;
    private int m_countShowingMax = -1;
    private int m_countShowingRemain = 0;

    private _QuratorSettingScenarioRule()
    {

    }

    public _QuratorSettingScenarioRule(int _idRule, EAdNetworkType _adNetworkType, int _countShowingMax, int _countShowingRemain)
    {
        m_idRule = _idRule;
        me_adNetworkType = _adNetworkType;
        m_countShowingMax = _countShowingMax;
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

    public int GetCountShowingMax()
    {
        return m_countShowingMax;
    }

    public int GetCountShowingRemain()
    {
        return m_countShowingRemain;
    }

}
