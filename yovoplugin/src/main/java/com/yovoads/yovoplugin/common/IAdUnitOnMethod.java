package com.yovoads.yovoplugin.common;


public interface IAdUnitOnMethod {

    void OnAdLoaded();
    void OnAdFailedToLoad(String _errorReason);
    void OnAdShowing();
    void OnAdClicked();
    void OnAdClosed();
    void OnAdDestroy();

    void OnSetLoadingAdUnitId(String _adUnitId);

}
