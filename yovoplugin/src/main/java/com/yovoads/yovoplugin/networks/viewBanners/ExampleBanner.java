package com.yovoads.yovoplugin.networks.viewBanners;

import com.yovoads.yovoplugin.common.EGravity;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;


public abstract class ExampleBanner
{
    public EAdUnitLoadingResult me_isAdUnitlLoadingResult;

    private int m_timeMax = 30;
    private int m_timeCur = 30;

    protected void SetShowTimeMax(int _showTime) {
        _showTime = 8;
        m_timeMax = _showTime;
        m_timeCur = m_timeMax;
    }
    public int GetShowTimeCur() {
        return m_timeCur;
    }

    public int TimeTick() {
        m_timeCur--;
        return GetShowTimeCur();
    }

    public void SetShowTimeEnd() {
        m_timeCur = -1;
    }
    public void ResetTimeCur() {
        m_timeCur = m_timeMax;
    }

    public abstract void Create(final String _adUnitId, final EGravity _eGravity);
    public abstract void SetGravity(final EGravity _eGravity);
    public abstract void LoadOther();
    public abstract void LoadYovo(int _idRule);
    public abstract void Show();
    public abstract void Hide();
    public abstract boolean SetPause(boolean _isPause);
    public abstract void OnAdDestroy();

}
