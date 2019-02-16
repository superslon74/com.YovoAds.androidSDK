package com.yovoads.yovoplugin.implementations.reward;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ImageView;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.core.YImage;

public class YRewardTimer extends AsyncTask<Void, Float, Void> {

    private float m_progressMax = 0;
    private YImage m_progressBg = null;
    private YImage m_progressValue = null;

    private YRewardTimer() {
    }

    public YRewardTimer(float _progressMax, YImage _progressBg, YImage _progressValue) {
        m_progressMax = _progressMax;
        m_progressBg = _progressBg;
        m_progressValue = _progressValue;
    }

    @Override
    protected Void doInBackground(Void... _empty) {
        float _progress = 0f;
        while (_progress < m_progressMax) {
            SystemClock.sleep(34);
            _progress += 34f;
            publishProgress((Float) ( _progress / (float) m_progressMax));
        }
        publishProgress(1F);
        return null;
    }

    @Override
    protected void onProgressUpdate(Float... _progress) {
        float _step = (float)_progress[0];
        if (_step > 1) {
            _step = 1f;
        }
        m_progressBg.setScaleX(_step);
        m_progressValue.setX((float) m_progressValue.m_posX + (_step * (float) m_progressBg.m_width));
    }

    @Override
    protected void onPostExecute(Void _void) {
        // empty
    }
}