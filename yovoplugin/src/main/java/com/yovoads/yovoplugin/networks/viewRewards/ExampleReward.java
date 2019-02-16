package com.yovoads.yovoplugin.networks.viewRewards;

import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;

public abstract class ExampleReward
{
    public EAdUnitLoadingResult me_isAdUnitlLoadingResult;

    public abstract void Create(final String _adUnitId);
    public abstract void LoadOther();
    public abstract void LoadYovo(final int _idRule);
    public abstract void Show();
}
