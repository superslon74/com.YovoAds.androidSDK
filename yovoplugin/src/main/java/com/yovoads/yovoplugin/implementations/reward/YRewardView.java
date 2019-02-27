package com.yovoads.yovoplugin.implementations.reward;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.R;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.EPivol;
import com.yovoads.yovoplugin.common.EScreenOrientation;
import com.yovoads.yovoplugin.core.YImage;
import com.yovoads.yovoplugin.implementations.Y__View;


public class YRewardView extends Y__View {

    private YRewardTimer m_yRewardTimer = null;

    private VideoView m_video = null;
    private YImage m_progressBg = null;
    private YImage m_progressValue = null;
    private ImageView m_install = null;
    private ImageView m_screen = null;
    private ImageView m_close = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(m_frameLayout);
        m_frameLayout.setBackgroundColor(Color.parseColor("#000000"));
    }

    @Override
    public void onResume() {
        super.onResume();

        if (m_isFirst) {
            m_isFirst = false;
            float _autoScaleWidth = ((float) DI._DISPLAY_WIDTH / (float) DI._DISPLAY_HEIGHT) / (4f / 3f);

            if (DI._SCREEN_ORIENTATION == EScreenOrientation._PORTRAIT) {
                SetOrientationPortrait(_autoScaleWidth);
            } else {
                SetOrientationLandscape(_autoScaleWidth);
            }

            YReward.m_callbackActive.OnAdShowing();
        }
    }

    @Override
    protected void SetOrientationPortrait(float _autoScaleWidth) {

        TextView _title = CreateTextView(0.034f, Gravity.CENTER_HORIZONTAL, 0.0f, 0.13f, 0.0f, 0.0f);
        _title.setText(YReward.m_adUnitDataActive.m_title);
        _title.setTextColor(Color.parseColor("#00C6FF"));

        TextView _textAds = CreateTextView(0.018f, Gravity.TOP + Gravity.LEFT, 0.0f, 0.19f, 0.0f, 0.0f);
        SetTextAdsStyle(YReward.me_adNetworkYovoActive, _textAds);

        CreteVideoView(0.0f, 0.0f, 0.21f);

        float _scaleH = (( ( (float) DI._DISPLAY_WIDTH / (float)610) * (float)298) / (float)DI._DISPLAY_HEIGHT);
        m_screen = CreateImageView(-1, 610.0f, 298.0f, _scaleH, 0.5f, 0.21f, EPivol._CENTER, EPivol._TOP);
        m_screen.setVisibility(View.GONE);
        m_screen.setImageBitmap(YReward.m_adUnitDataActive.m_bitmapScreen);

        m_install = CreateImageView(R.drawable.but_install, 420f, 110f, 0.089f, 0.5f, 0.8f, EPivol._CENTER, EPivol._CENTER);
        m_install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("googlechrome://navigate?url=" + YReward.m_adUnitDataActive.m_clickURL));
                    startActivity(intent);
                } catch (Exception exc) {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(YReward.m_adUnitDataActive.m_clickURL));
                    startActivity(intent);
                }

                YReward.m_callbackActive.OnAdClicked();
                Quit();
            }
        });

        ImageView _imageProgressRect = CreateImageView(R.drawable.progressbg, 100f, 5f,0.0233f, 0.5f, 0.93f, EPivol._CENTER, EPivol._CENTER);
        _imageProgressRect.setColorFilter(Color.parseColor("#898989")); // 8989FF
        _imageProgressRect.setScaleY(0.13f);

        m_progressBg = CreateImageView(R.drawable.progressbg, 100f, 5f,0.0233f, 0.5f, 0.93f, EPivol._CENTER, EPivol._CENTER);
        m_progressBg.setColorFilter(Color.parseColor("#ff007e"));
        m_progressBg.setScaleY(0.13f);

        m_progressValue = CreateImageView(R.drawable.but_close, 50.0f, 50.0f, 0.015f, 0.0f, 0.93f, EPivol._CENTER, EPivol._CENTER);
        m_progressValue.setColorFilter(Color.parseColor("#ff007e"));
        m_progressValue.m_posX = m_progressBg.m_posX - (int)((float)m_progressBg.m_width / 2f);
        m_progressValue.setX(m_progressValue.m_posX);

        m_close = CreateImageView(R.drawable.but_close, 50f, 50f, 0.05f, 0.005f, 0.005f, EPivol._LEFT, EPivol._TOP);
        m_close.setVisibility(View.GONE);
        m_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quit();
            }
        });
    }

    @Override
    protected void SetOrientationLandscape(float _autoScaleWidth)
    {

    }

    private void CreteVideoView(float _widthProcent, float _heightProcent, float _offSetTop) {

        m_video = new VideoView(DI.m_activity);
        m_video.setVisibility(View.GONE);
        //m_video.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        //((ViewGroup.MarginLayoutParams) m_video.getLayoutParams()).setMargins(0, (int) (0.21f * DI._DISPLAY_HEIGHT), 0, 0);
        int _width = 0;
        if(_widthProcent > 0) {
            m_video.setLayoutParams(new FrameLayout.LayoutParams((int)((float)DI._DISPLAY_WIDTH * _widthProcent), FrameLayout.LayoutParams.WRAP_CONTENT));
        } else if(_heightProcent > 0){
            m_video.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, (int)((float)DI._DISPLAY_HEIGHT * _heightProcent)));
        } else {
            m_video.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        }
        ((ViewGroup.MarginLayoutParams) m_video.getLayoutParams()).setMargins(0, (int) (_offSetTop * DI._DISPLAY_HEIGHT), 0, 0);
        m_frameLayout.addView(m_video);
        //m_video.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.mp4_720);

        m_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                YovoSDK.ShowLog("YRewardView", String.valueOf(m_video.getDuration()));
                m_yRewardTimer = new YRewardTimer(m_video.getDuration(), m_progressBg, m_progressValue);
                m_yRewardTimer.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                m_video.start();
            }
        });

        m_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(!m_yRewardTimer.isCancelled()) {
                    m_yRewardTimer.cancel(true);
                }
                //m_progressValue.setX(m_progressValue.m_posX + m_progressBg.m_width);
                m_progressBg.setScaleX(1);
                m_video.setVisibility(View.GONE);
                m_screen.setVisibility(View.VISIBLE);
                m_close.setVisibility(View.VISIBLE);
                YReward.m_callbackActive.OnAdRewarded();
            }
        });

        m_video.setVideoPath(DI.m_activity.getFilesDir() + YRewardLoader.m_yovoPath + EAdNetworkType.GetValue(EAdNetworkType._CROSS_PROMOTION));
        m_video.setVisibility(View.VISIBLE);
    }

    @Override
    protected void Quit() {
        YReward.m_callbackActive.OnAdClosed();
        YReward.m_callbackActive.OnAdDestroy();
        m_yRewardTimer = null;
        finish();
    }



}
