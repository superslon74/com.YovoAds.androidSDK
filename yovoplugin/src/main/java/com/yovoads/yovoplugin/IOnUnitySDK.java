package com.yovoads.yovoplugin;

public interface IOnUnitySDK
{
    void OnSetGaid();
    void OnRewarded(boolean _isRewarded, String _key, String _value);
    void OnPopupsShow(int _butClick);
    void OnAppTryQuit(int _answer);
}
