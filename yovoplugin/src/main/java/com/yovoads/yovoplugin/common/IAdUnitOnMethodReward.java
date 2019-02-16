package com.yovoads.yovoplugin.common;

public interface IAdUnitOnMethodReward {

    void OnAdLoaded();
    void OnAdFailedToLoad(String _errorReason);
    void OnAdShowing();
    void OnAdClicked();
    void OnAdClosed();
    void OnAdRewarded();
    void OnAdCompleted();

    void OnSetLoadingAdUnitId(String _adUnitId);
    void OnAdDestroy();
}
