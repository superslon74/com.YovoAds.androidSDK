package com.yovoads.yovoplugin.networks.viewInterstitials;

import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;

public abstract class ExampleInterstitial
{
    public EAdUnitLoadingResult me_isAdUnitlLoadingResult;

    public abstract void Create(final String _adUnitId);
    public abstract void LoadOther();
    public abstract void LoadYovo(int _idRule);
    public abstract void Show();
}
