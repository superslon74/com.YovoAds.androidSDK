package com.yovoads.yovoplugin;

public interface IOnUnitySDK
{
    void OnSetGaid();
    void OnRewarded(boolean _isRewarded, String _key, String _value);
}
