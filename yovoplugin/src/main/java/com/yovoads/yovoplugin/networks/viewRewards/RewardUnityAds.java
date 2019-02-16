package com.yovoads.yovoplugin.networks.viewRewards;

import com.yovoads.yovoplugin.common.IAdUnitOnMethodReward;
import com.yovoads.yovoplugin.common.EAdUnitLoadingResult;
import com.yovoads.yovoplugin.networks.UnityAdsListener;


public class RewardUnityAds extends ExampleReward
{
    private IAdUnitOnMethodReward m_callback = null;
    private static UnityAdsListener m_unityAdsListener;
    public static String m_placementId;

    public RewardUnityAds(IAdUnitOnMethodReward _callback, String _placementId)
    {
        m_placementId = _placementId;
        me_isAdUnitlLoadingResult = EAdUnitLoadingResult._NONE;
        m_unityAdsListener = new UnityAdsListener(_callback, "REWARD_CONST");
        Create("");
    }

    @Override
    public void Create(final String _adUnitId)
    {

    }

    @Override
    public void LoadOther()
    {
        //me_isAdUnitlLoadingResult = EAdUnitLoadingResult._LOADING;
    }

    @Override
    public void LoadYovo(final int _empty)
    {
        // empty
    }

    @Override
    public void Show()
    {

    }
}


//public class UnityAdsListener implements IUnityAdsListener {
//    public IAdUnitCallback m_callback;
//
//    public UnityAdsListener(IAdUnitCallback _callback) {
//        m_callback = _callback;
//    }
//
//    @Override
//    public void onUnityAdsReady(String _placementId) {
//        if(RewardUnityAds.m_placementId == _placementId) {
//            m_callback.OnAdLoaded();
//        }
//    }
//
//    @Override
//    public void onUnityAdsStart(String s) {
//        m_callback.OnAdStarted();
//    }
//
//    @Override
//    public void onUnityAdsFinish(String s, com.unity3d.ads.UnityAds.FinishState finishState) {
//        if (finishState != com.unity3d.ads.UnityAds.FinishState.SKIPPED && finishState == UnityAds.FinishState.COMPLETED) {
//            m_callback.OnAdRewarded();
//        }
//        else
//        {
//            m_callback.OnAdClosed();
//        }
//    }
//
//    @Override
//    public void onUnityAdsError(com.unity3d.ads.UnityAds.UnityAdsError unityAdsError, String s) {
//        m_callback.OnAdFailedToLoad(s);
//    }
//}




