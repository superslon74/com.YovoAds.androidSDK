package com.yovoads.yovoplugin.implementations.reward;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.core.YImageData;
import com.yovoads.yovoplugin.core.YTimer;

public class YRewardTimer extends AsyncTask<Void, Float, Void> {

    private float m_progressMax = 0;
    private ImageView m_progressValue = null;

    private YRewardTimer() {
    }

    public YRewardTimer(float _progressMax, ImageView _progressValue) {
        m_progressMax = _progressMax;
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
            m_progressValue.setX(DI._DISPLAY_WIDTH);
        }
        m_progressValue.setX(_step * (float) DI._DISPLAY_WIDTH);
    }

    @Override
    protected void onPostExecute(Void _void) {
        // empty
    }
}