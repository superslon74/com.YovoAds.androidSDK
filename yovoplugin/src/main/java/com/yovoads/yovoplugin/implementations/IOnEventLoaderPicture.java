package com.yovoads.yovoplugin.implementations;

import android.graphics.Bitmap;

import com.yovoads.yovoplugin.implementations.AdUnitTypeMode;

public interface IOnEventLoaderPicture
{
    public void OnLoading(AdUnitTypeMode _adUnitTypeMode, Bitmap _bitmap);
    public void OnLoadingFailed(int _error);
}
