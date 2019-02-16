package com.yovoads.yovoplugin.common;

public interface IAdUnitType {

    void AddNetworks(EAdNetworkType _adNetwork, String[] _adUnits);
    void RunLoadingAdUnit(EAdNetworkType _adNetworkType, int _idRule);
    boolean TryShowingAdUnit(EAdNetworkType _adNetworkType, int _idRule);
}
