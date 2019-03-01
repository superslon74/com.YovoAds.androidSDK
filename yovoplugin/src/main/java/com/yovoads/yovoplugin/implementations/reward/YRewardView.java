package com.yovoads.yovoplugin.implementations.reward;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.R;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.EPivol;
import com.yovoads.yovoplugin.common.EScreenOrientation;
import com.yovoads.yovoplugin.core.YImageData;
import com.yovoads.yovoplugin.implementations.YViewActivity;


public class YRewardView extends YViewActivity {

    private YRewardTimer m_yRewardTimer = null;

    private VideoView m_video = null;
    private FrameLayout m_frameProgressRect = null;
    private ImageView m_progressValue = null;
    private YImageData m_install = null;
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

            if (DI._SCREEN_ORIENTATION == EScreenOrientation._PORTRAIT) {
                m_frameLayout.setBackgroundColor(Color.parseColor("#000000"));
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                SetOrientationPortrait();
            } else {
                m_frameLayout.setBackgroundColor(Color.parseColor("#000000"));
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                SetOrientationLandscape();
            }

            YReward.m_callbackActive.OnAdShowing();
        }
    }

    @Override
    protected void SetOrientationPortrait() {

        TextView _title = CreateTextView(0.034f, Gravity.CENTER_HORIZONTAL, 0.0f, 0.13f, 0.0f, 0.0f);
        _title.setText(YReward.m_adUnitDataActive.m_title);
        _title.setTextColor(Color.parseColor("#00C6FF"));

        TextView _textAds = CreateTextView(0.018f, Gravity.TOP + Gravity.LEFT, 0.0f, 0.19f, 0.0f, 0.0f);
        SetTextAdsStyle(YReward.me_adNetworkYovoActive, _textAds);

        CreteVideoView(0.0f, 0.0f, 0.0f,0.21f);

        float _scaleH = (( ( (float) DI._DISPLAY_WIDTH / (float)610) * (float)298) / (float)DI._DISPLAY_HEIGHT);
        m_screen = CreateImageView(-1, 610.0f, 298.0f, _scaleH, 0.5f, 0.21f, EPivol._CENTER, EPivol._TOP);
        m_screen.setVisibility(View.GONE);
        m_screen.setImageBitmap(YReward.m_adUnitDataActive.m_bitmapScreen);

        m_install = CreateYImageDataView(R.drawable.but_install, 420f, 110f, 0.089f, 0.5f, 0.8f, EPivol._CENTER, EPivol._CENTER);


        m_frameProgressRect = new FrameLayout(DI.m_activity);
        m_frameProgressRect.setLayoutParams(new FrameLayout.LayoutParams(DI._DISPLAY_WIDTH, (int)(DI._DISPLAY_HEIGHT * 0.005f)));
        m_frameProgressRect.setBackgroundColor(Color.parseColor("#898989"));
        ((ViewGroup.MarginLayoutParams) m_frameProgressRect.getLayoutParams()).setMargins(0, (int) (0.93f * DI._DISPLAY_HEIGHT), 0, 0);
        m_frameLayout.addView(m_frameProgressRect);

        m_progressValue = CreateImageView(R.drawable.but_close,50.0f, 50.0f, 0.0144f, 0.0f, 0.933f, EPivol._CENTER, EPivol._CENTER);
        m_progressValue.setColorFilter(Color.parseColor("#ff007e"));
        m_progressValue.setX(0);

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
    protected void SetOrientationLandscape()
    {
        m_screen = CreateImageView(-1, YReward.m_adUnitDataActive.m_screenWidth, YReward.m_adUnitDataActive.m_screenHeight, 0.721f, 0.5f, 0.0f, EPivol._CENTER, EPivol._TOP);
        m_screen.setImageBitmap(YReward.m_adUnitDataActive.m_bitmapScreen);
        m_screen.setVisibility(View.GONE);

        CreteVideoView(0.0f, 0.71f, 0.1f, 0.0f);

        m_close = CreateImageView(R.drawable.but_close, 50f, 50f, 0.089f, 0.005f, 0.005f, EPivol._LEFT, EPivol._TOP);
        m_close.setVisibility(View.GONE);
        m_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Quit();
            }
        });


        ImageView _bgBotton = CreateImageView(R.drawable.fon,16f, 16f, 0.28f, 0.5f, 1.0f, EPivol._CENTER, EPivol._BOTTON);
        _bgBotton.setColorFilter(Color.parseColor("#ff9000"));
        _bgBotton.setScaleX(10);

        float _posY = 0.89f;
        YImageData _star3 = CreateYImageDataView(R.drawable.star,1f, 1f, 0.08f, 0.5f, _posY, EPivol._CENTER, EPivol._CENTER);

        ImageView _star1 = CreateImageView(R.drawable.star,1f, 1f, 0.08f, 0.5f, _posY, EPivol._CENTER, EPivol._CENTER);
        _star1.setX(_star3.m_posX - (int)(2.6f * (float) _star3.m_width));

        ImageView _star2 = CreateImageView(R.drawable.star,1f, 1f, 0.08f, 0.5f, _posY, EPivol._CENTER, EPivol._CENTER);
        _star2.setX(_star3.m_posX - (int)(1.3f * (float) _star3.m_width));

        ImageView _star4 = CreateImageView(R.drawable.star,1f, 1f, 0.08f, 0.5f, _posY, EPivol._CENTER, EPivol._CENTER);
        _star4.setX(_star3.m_posX + (int)(1.3f * (float) _star3.m_width));

        ImageView _star5 = CreateImageView(R.drawable.star,1f, 1f, 0.08f, 0.5f, _posY, EPivol._CENTER, EPivol._CENTER);
        _star5.setX(_star3.m_posX + (int)(2.6f * (float) _star3.m_width));

        YImageData _icon = CreateYImageDataView(-1, 1f, 1f, 0.21f, 0.5f, 0.735f, EPivol._RIGHT, EPivol._TOP);
        _icon.m_image.setImageBitmap(YReward.m_adUnitDataActive.m_bitmapIcon);
        _icon.m_image.setX(_icon.m_posX - (int)(4.0f * (float) _star3.m_width));

        m_install = CreateYImageDataView(R.drawable.but_download,1f, 1f, 0.21f, 0.5f, 0.735f, EPivol._LEFT, EPivol._TOP);
        m_install.m_image.setX(m_install.m_posX + (int)(4.0f * (float) _star3.m_width));

        TextView _textTitle = CreateTextView(0.08f, Gravity.CENTER_HORIZONTAL, 0.0f, 0.73f, 0.0f, 0.0f);
        _textTitle.setText(YReward.m_adUnitDataActive.m_title);
        _textTitle.setTextColor(Color.parseColor("#343434"));

        TextView _textDesc = CreateTextView(0.05f, Gravity.CENTER_HORIZONTAL, 0.0f, 0.937f, 0.0f, 0.0f);
        _textDesc.setText(YReward.m_adUnitDataActive.m_title);
        _textDesc.setTextColor(Color.parseColor("#343434"));

        TextView _textAds = CreateTextView(0.034f, Gravity.LEFT, 0.01f, 0.96f, 0f, 0f);
        SetTextAdsStyle(EAdNetworkType._CROSS_PROMOTION, _textAds);


        m_frameProgressRect = new FrameLayout(DI.m_activity);
        m_frameProgressRect.setLayoutParams(new FrameLayout.LayoutParams(DI._DISPLAY_WIDTH, (int)(DI._DISPLAY_HEIGHT * 0.01f)));
        m_frameProgressRect.setBackgroundColor(Color.parseColor("#898989"));
        ((ViewGroup.MarginLayoutParams) m_frameProgressRect.getLayoutParams()).setMargins(0, (int) (0.715f * DI._DISPLAY_HEIGHT), 0, 0);
        m_frameLayout.addView(m_frameProgressRect);

        m_progressValue = CreateImageView(R.drawable.but_close,50.0f, 50.0f, 0.021f, 0.0f, 0.72f, EPivol._CENTER, EPivol._CENTER);
        m_progressValue.setColorFilter(Color.parseColor("#ff007e"));
        m_progressValue.setX(0);
    }


    private void CreteVideoView(float _widthProcent, float _heightProcent, float _offSetLeft, float _offSetTop) {

        m_video = new VideoView(DI.m_activity);
        m_video.setVisibility(View.GONE);
        //m_video.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        //((ViewGroup.MarginLayoutParams) m_video.getLayoutParams()).setMargins(0, (int) (0.21f * DI._DISPLAY_HEIGHT), 0, 0);
        int _width = (int)((float)DI._DISPLAY_WIDTH * _widthProcent);
        int _height = (int)((float)DI._DISPLAY_HEIGHT * _heightProcent);
        if(_widthProcent > 0) {
            m_video.setLayoutParams(new FrameLayout.LayoutParams(_width, FrameLayout.LayoutParams.WRAP_CONTENT));
        } else if(_heightProcent > 0){
            m_video.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, _height));
        } else {
            m_video.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        }
        ((ViewGroup.MarginLayoutParams) m_video.getLayoutParams()).setMargins(((int) (_offSetLeft * DI._DISPLAY_WIDTH)), (int) (_offSetTop * DI._DISPLAY_HEIGHT), 0, 0);
        m_frameLayout.addView(m_video);

        //m_video.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.mp4_720);

        m_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                YovoSDK.ShowLog("YRewardView", String.valueOf(m_video.getDuration()));
                m_yRewardTimer = new YRewardTimer(m_video.getDuration(), m_progressValue);
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

                m_video.setVisibility(View.GONE);
                m_screen.setVisibility(View.VISIBLE);
                m_close.setVisibility(View.VISIBLE);
                YReward.m_callbackActive.OnAdRewarded();
                m_progressValue.setVisibility(View.GONE);
                m_frameProgressRect.setVisibility(View.GONE);

                m_install.m_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnClick(YReward.m_adUnitDataActive.m_clickURL);
                        YReward.m_callbackActive.OnAdClicked();
                        Quit();
                    }
                });
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
