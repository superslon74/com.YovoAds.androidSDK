package com.yovoads.yovoplugin.core.Scenario;

import com.yovoads.yovoplugin.common.EAdNetworkType;

public class Rule {

    private int m_idRule = 0;
    private EAdNetworkType me_networkType;
    private int m_countShowingMax = -1;
    private int m_countShowingRemain = -1;

    private boolean m_isUsed = false;


    private Rule() {
    }

    public Rule(int _idRule, EAdNetworkType _networkType, int _countShowingMax, int _countShowingRemain, boolean _isUsed) {
        m_idRule = _idRule;
        me_networkType = _networkType;
        m_countShowingMax = _countShowingMax;
        m_countShowingRemain = _countShowingRemain;
        m_isUsed = _isUsed;
    }


    public int getIdRule() {
        return m_idRule;
    }

    public EAdNetworkType GetNetworkType() {
        return me_networkType;
    }

    public boolean IsHaveSomethingToShow() {
        return (m_countShowingMax == -1 || (m_countShowingMax > 0 && m_countShowingRemain > 0)) ? true : false;
    }

    public boolean IsHaveSomethingToShowUsed() {
        return (m_isUsed && (m_countShowingMax == -1 || (m_countShowingMax > 0 && m_countShowingRemain > 0))) ? true : false;
    }

    public int GetCountShowingMax() {
        return m_countShowingMax;
    }

    public int GetCountShowingRemain() {
        return m_countShowingRemain;
    }

    public void SetCountShowingRemain(int _countShowingRemain) {
        m_countShowingRemain = _countShowingRemain;
    }

    public boolean IsUsed() {
        return m_isUsed;
    }

    public void Reset() {
        m_countShowingRemain = m_countShowingMax;
    }
}
