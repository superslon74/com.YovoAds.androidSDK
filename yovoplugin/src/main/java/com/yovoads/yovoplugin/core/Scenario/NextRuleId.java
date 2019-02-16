package com.yovoads.yovoplugin.core.Scenario;

public class NextRuleId {

    public boolean m_isLoadAndShow = false;
    public int m_idRule = 0;

    private NextRuleId() {
    }

    public NextRuleId(boolean _isLoadAndShow, int _idRule)
    {
        m_isLoadAndShow = _isLoadAndShow;
        m_idRule = _idRule;
    }
}
