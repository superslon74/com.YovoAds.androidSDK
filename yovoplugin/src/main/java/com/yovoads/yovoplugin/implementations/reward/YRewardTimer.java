package com.yovoads.yovoplugin.implementations.reward;

import android.os.AsyncTask;
import android.os.SystemClock;


import com.yovoads.yovoplugin.core.YImageData;

public class YRewardTimer extends AsyncTask<Void, Float, Void> {

    private float m_progressMax = 0;
    private YImageData m_progressBg = null;
    private YImageData m_progressValue = null;

    private YRewardTimer() {
    }

    public YRewardTimer(float _progressMax, YImageData _progressBg, YImageData _progressValue) {
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
        m_progressBg.m_image.setScaleX(_step);
        m_progressValue.m_image.setX((float) m_progressValue.m_posX + (_step * (float) m_progressBg.m_width));
    }

    @Override
    protected void onPostExecute(Void _void) {
        // empty
    }
}