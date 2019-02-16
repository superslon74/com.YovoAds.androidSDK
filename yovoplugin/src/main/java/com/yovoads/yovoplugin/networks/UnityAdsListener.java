package com.yovoads.yovoplugin.networks;

import com.unity3d.ads.IUnityAdsListener;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.IAdUnitOnMethodReward;
import com.yovoads.yovoplugin.networks.viewRewards.RewardUnityAds;


public class UnityAdsListener implements IUnityAdsListener {
    public IAdUnitOnMethodReward m_callback;
private String m_tag;
    public UnityAdsListener(IAdUnitOnMethodReward _callback, String _tag) {
        m_callback = _callback;
        m_tag = _tag;
    }

    @Override
    public void onUnityAdsReady(String _placementId) {
        YovoSDK.ShowLog("++UnityAdsListener", m_tag + " >> " + _placementId);
       // BannerUnityAds.m_bannerUnityAds.Load2(_placementId);
        if(RewardUnityAds.m_placementId == _placementId) {
            m_callback.OnAdLoaded();
        }
    }

    @Override
    public void onUnityAdsStart(String s) {
        YovoSDK.ShowLog("++UnityAdsListener", m_tag + " >> " + s);
        m_callback.OnAdShowing();
    }

    @Override
    public void onUnityAdsFinish(String s, com.unity3d.ads.UnityAds.FinishState finishState) {
        YovoSDK.ShowLog("++UnityAdsListener", m_tag + " >> " + finishState.toString());
        if (finishState != com.unity3d.ads.UnityAds.FinishState.SKIPPED && finishState == com.unity3d.ads.UnityAds.FinishState.COMPLETED) {
            //m_callback.OnAdRewarded();
        }
        else
        {
            m_callback.OnAdClosed();
        }
    }

    @Override
    public void onUnityAdsError(com.unity3d.ads.UnityAds.UnityAdsError unityAdsError, String s) {
        YovoSDK.ShowLog("++UnityAdsListener", m_tag + " >> " + unityAdsError.toString());
        m_callback.OnAdFailedToLoad(s);
    }
}
