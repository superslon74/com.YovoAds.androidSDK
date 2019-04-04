package com.yovoads.yovoplugin.core;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.EAdUnitPrice;
import com.yovoads.yovoplugin.core.AdUnit.Rewards;

public class dbLocal {

    private static dbLocal mc_this;
    private SharedPreferences m_db;
    private SharedPreferences.Editor m_dbSet;

    public final static String mk_sessionPeriod = "Sessionperiod";
    public final static  String mk_updateScenario = "UpdateScenario";

    public final static String mk_rewardCountPerDayMax = "RewardedCountPerDayMax";
    public final static String mk_rewardCountShowing24 = "RewardedCountShowing24";
    public final static String mk_rewardMinimumPeriod = "RewardedMinimumPeriod";

    public final static String mk_adNetworks = "AdNetworks";
    public final static String mk_Modified = "Modified";
    public final static String mk_Queue = "Queue";

    private String mk_yovoGaidIdCreate = "_yovogaididcreate";
    private String mk_yovoBannerLow = "_yovobannerLow";
    private String mk_yovoBannerMedium = "_bannerMedium";
    private String mk_yovoBannerHigh = "_bannerHigh";


    public static dbLocal getInstance() {
        if (mc_this == null) {
            mc_this = new dbLocal();
        }
        return mc_this;
    }

    private dbLocal() {

        m_db = PreferenceManager.getDefaultSharedPreferences(DI.m_activity);

        if (m_db.getString("qurator_db_Local", "empty") == "empty") {

            QuratorUpdateData(-1, 13, 377,"[{\"ID\":3,\"AdTypes\":[0,1,2]},{\"ID\":4,\"AdTypes\":[0,1,2]}]",
                    "[{\"T\":0,\"Q\":[{\"ID\":0,\"ADN\":3,\"C\":-1,\"V\":0},{\"ID\":0,\"ADN\":4,\"C\":-1,\"V\":0}]},{\"T\":1,\"Q\":[{\"ID\":0,\"ADN\":3,\"C\":-1,\"V\":0},{\"ID\":0,\"ADN\":4,\"C\":-1,\"V\":0}]},{\"T\":2,\"Q\":[{\"ID\":0,\"ADN\":3,\"C\":-1,\"V\":0},{\"ID\":0,\"ADN\":4,\"C\":-1,\"V\":0}]}]");
            RewardSetData(-1, -1, 0, 0);

        } else {
            YovoSDK.ShowLog("dbLocal", "none create");
        }
    }

    public void QuratorUpdateData(long _scenarioModified, int _sessionPeriod, int _updateScenario, String _adNetworkAvailable, String _scenarioQueue) {

        m_dbSet = m_db.edit();
        m_dbSet.putLong(mk_Modified, _scenarioModified);
        m_dbSet.putInt(mk_sessionPeriod, _sessionPeriod);
        m_dbSet.putInt(mk_updateScenario, _updateScenario);
        m_dbSet.putString(mk_adNetworks, _adNetworkAvailable);
        m_dbSet.putString(mk_Queue, _scenarioQueue);
        m_dbSet.putString("qurator_db_Local", "empty");
        m_dbSet.commit();
    }

    public Long GetScenarioModified() {
        return m_db.getLong(mk_Modified, -1);
    }

    public void RewardSetData(int _countPerDayMax, int _rewardCountShowing24, int _minimumPeriodShowing, int _rewardNextShowAvailable) {
        Rewards.m_rewardNextShowAvailable = _rewardNextShowAvailable;
        m_dbSet = m_db.edit();
        m_dbSet.putInt(mk_rewardCountPerDayMax, _countPerDayMax);
        m_dbSet.putInt(mk_rewardCountShowing24, _rewardCountShowing24);
        m_dbSet.putInt(mk_rewardMinimumPeriod, _minimumPeriodShowing);
        m_dbSet.commit();
    }

    public void SetReward_OnAdShowing() {
        Rewards.m_rewardNextShowAvailable = m_db.getInt(mk_rewardMinimumPeriod, 0);
        m_dbSet = m_db.edit();
        m_dbSet.putInt(mk_rewardCountShowing24, m_db.getInt(mk_rewardCountShowing24, -1) - 1);
        m_dbSet.commit();
    }

    public boolean IsRewardAvailable(){
        return (m_db.getInt(mk_rewardCountPerDayMax, -1) < 0) ? true : (m_db.getInt(mk_rewardCountShowing24, -1) < m_db.getInt(mk_rewardCountPerDayMax, -1)) ? true : false;
    }

    public int GetSessionPeriod() {
        return m_db.getInt(mk_sessionPeriod, 13);
    }

    public String GetScenarioQueue() {
        return m_db.getString(mk_Queue, "[{\"T\":0,\"Q\":[{\"ID\":0,\"ADN\":3,\"C\":-1,\"V\":0},{\"ID\":0,\"ADN\":4,\"C\":-1,\"V\":0}]},{\"T\":1,\"Q\":[{\"ID\":0,\"ADN\":3,\"C\":-1,\"V\":0},{\"ID\":0,\"ADN\":4,\"C\":-1,\"V\":0}]},{\"T\":2,\"Q\":[{\"ID\":0,\"ADN\":3,\"C\":-1,\"V\":0},{\"ID\":0,\"ADN\":4,\"C\":-1,\"V\":0}]}]");
    }

    public String GetAdNetworkAvailable()
    {
        return m_db.getString(mk_adNetworks, "[{\"ID\":3,\"AdTypes\":[0,1,2]},{\"ID\":4,\"AdTypes\":[0,1,2]}]");
    }

    public String GetGaid() {
        if(m_db.getString(mk_yovoGaidIdCreate, "").isEmpty())
        {
            m_dbSet = m_db.edit();
            m_dbSet.putString(mk_yovoGaidIdCreate, "qqqqqqqq-0000-0000-0000-wwwwwwwwwwww"); // a6ff3009-0bd7-4cc9-ad0f-8548f12a5ffe
            m_dbSet.commit();
        }
        return m_db.getString(mk_yovoGaidIdCreate, "");
    }

    public int GetBannerTimerShow(EAdUnitPrice _adUnitPrice)
    {
        switch (_adUnitPrice)
        {
            case _MEDIUM:
                return m_db.getInt(mk_yovoBannerMedium, 13);
            case _HIGH:
                return m_db.getInt(mk_yovoBannerHigh, 8);
        }

        return m_db.getInt(mk_yovoBannerLow, 5);
    }
}
