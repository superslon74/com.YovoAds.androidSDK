package com.yovoads.yovoplugin.implementations.interstitial;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.R;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.EPivol;
import com.yovoads.yovoplugin.common.EScreenOrientation;
import com.yovoads.yovoplugin.core.YImageData;
import com.yovoads.yovoplugin.implementations.YViewActivity;

public class YInterstitialView extends YViewActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(m_frameLayout);

        if(DI.m_activity.getResources().getConfiguration().orientation == 0){

        }
    }

    // Вызывается в начале "активного" состояния.
    @Override
    public void onResume() {
        super.onResume();

        if (m_isFirst) {
            m_isFirst = false;

            if (DI._SCREEN_ORIENTATION == EScreenOrientation._PORTRAIT) {
                m_frameLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                SetOrientationPortrait();
            } else {
                m_frameLayout.setBackgroundColor(Color.parseColor("#000000"));
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                SetOrientationLandscape();
            }

            m_frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnClick(YInterstitial.m_adUnitDataActive.m_clickURL);
                    YInterstitial.m_callbackActive.OnAdClicked();
                    Quit();
                }
            });

            YInterstitial.m_callbackActive.OnAdShowing();
        }
    }

    @Override
    protected void SetOrientationPortrait() {

        float _scaleH = (((float) DI._DISPLAY_WIDTH / YInterstitial.m_adUnitDataActive.m_screenWidth) * YInterstitial.m_adUnitDataActive.m_screenHeight) / (float) DI._DISPLAY_HEIGHT;
        ImageView _screen = CreateImageView(-1,YInterstitial.m_adUnitDataActive.m_screenWidth, YInterstitial.m_adUnitDataActive.m_screenHeight, _scaleH, 0.5f, 0.0f, EPivol._CENTER, EPivol._TOP);
        _screen.setImageBitmap(YInterstitial.m_adUnitDataActive.m_bitmapScreen);

        ImageView _close = CreateImageView(R.drawable.but_close, 50f, 50f, 0.05f, 0.005f, 0.005f, EPivol._LEFT, EPivol._TOP);
        _close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Quit();
            }
        });

        float _posY = 0.66f;

        YImageData _star3 = CreateYImageDataView(R.drawable.star, 1f, 1f, 0.044f, 0.5f, _posY, EPivol._CENTER, EPivol._CENTER);

        ImageView _star1 = CreateImageView(R.drawable.star,1f, 1f, 0.044f, 0.5f, _posY, EPivol._CENTER, EPivol._CENTER);
        _star1.setX(_star3.m_posX - (int)(2.6f * (float) _star3.m_width));

        ImageView _star2 = CreateImageView(R.drawable.star,1f, 1f, 0.044f, 0.5f, _posY, EPivol._CENTER, EPivol._CENTER);
        _star2.setX(_star3.m_posX - (int)(1.3f * (float) _star3.m_width));

        ImageView _star4 = CreateImageView(R.drawable.star,1f, 1f, 0.044f, 0.5f, _posY, EPivol._CENTER, EPivol._CENTER);
        _star4.setX(_star3.m_posX + (int)(1.3f * (float) _star3.m_width));

        ImageView _star5 = CreateImageView(R.drawable.star,1f, 1f, 0.044f, 0.5f, _posY, EPivol._CENTER, EPivol._CENTER);
        _star5.setX(_star3.m_posX + (int)(2.6f * (float) _star3.m_width));

        ImageView _icon = CreateImageView(-1,1f, 1f, 0.144f, 0.5f, 0.44f, EPivol._CENTER, EPivol._CENTER);
        _icon.setImageBitmap(YInterstitial.m_adUnitDataActive.m_bitmapIcon);

        ImageView _bgBotton = CreateImageView(R.drawable.fon,16f, 16f, 0.18f, 0.5f, 1.0f, EPivol._CENTER, EPivol._BOTTON);
        _bgBotton.setColorFilter(Color.parseColor("#ff9000"));
        _bgBotton.setScaleX(5);

        ImageView _install = CreateImageView(R.drawable.but_install,420f, 110f, 0.087f, 0.5f, 0.89f, EPivol._CENTER, EPivol._CENTER);
        _install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Quit();
            }
        });

        TextView _textTitle = CreateTextView(0.037f, Gravity.CENTER_HORIZONTAL, 0.0f, 0.56f, 0.0f, 0.0f);
        _textTitle.setText(YInterstitial.m_adUnitDataActive.m_title);
        //_textTitle.setTextColor(Color.parseColor("#00C6FF"));

        TextView _textDesc = CreateTextView(0.03f, Gravity.CENTER_HORIZONTAL, 0.0f, 0.69f, 0.0f, 0.0f);
        _textDesc.setText(YInterstitial.m_adUnitDataActive.m_title);
        //_textDesc.setTextColor(Color.parseColor("#00C6FF"));

        TextView _textAds = CreateTextView(0.0144f, Gravity.LEFT, 0f, 0.8f, 0f, 0f);
        SetTextAdsStyle(EAdNetworkType._CROSS_PROMOTION, _textAds);
    }

    @Override
    protected void SetOrientationLandscape() {

        ImageView _screen = CreateImageView(-1, YInterstitial.m_adUnitDataActive.m_screenWidth, YInterstitial.m_adUnitDataActive.m_screenHeight, 0.721f, 0.5f, 0.0f, EPivol._CENTER, EPivol._TOP);
        _screen.setImageBitmap(YInterstitial.m_adUnitDataActive.m_bitmapScreen);

        ImageView _close = CreateImageView(R.drawable.but_close, 50f, 50f, 0.089f, 0.005f, 0.005f, EPivol._LEFT, EPivol._TOP);
        _close.setOnClickListener(new View.OnClickListener() {
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
        _icon.m_image.setImageBitmap(YInterstitial.m_adUnitDataActive.m_bitmapIcon);
        _icon.m_image.setX(_icon.m_posX - (int)(4.0f * (float) _star3.m_width));

        YImageData _install = CreateYImageDataView(R.drawable.but_download,1f, 1f, 0.21f, 0.5f, 0.735f, EPivol._LEFT, EPivol._TOP);
        _install.m_image.setX(_install.m_posX + (int)(4.0f * (float) _star3.m_width));

        TextView _textTitle = CreateTextView(0.08f, Gravity.CENTER_HORIZONTAL, 0.0f, 0.73f, 0.0f, 0.0f);
        _textTitle.setText(YInterstitial.m_adUnitDataActive.m_title);
        _textTitle.setTextColor(Color.parseColor("#343434"));

        TextView _textDesc = CreateTextView(0.05f, Gravity.CENTER_HORIZONTAL, 0.0f, 0.937f, 0.0f, 0.0f);
        _textDesc.setText(YInterstitial.m_adUnitDataActive.m_title);
        _textDesc.setTextColor(Color.parseColor("#343434"));

        TextView _textAds = CreateTextView(0.034f, Gravity.LEFT, 0.01f, 0.96f, 0f, 0f);
        SetTextAdsStyle(EAdNetworkType._CROSS_PROMOTION, _textAds);
    }

    @Override
    protected void Quit()
    {
        YInterstitial.m_callbackActive.OnAdClosed();
        YInterstitial.m_callbackActive.OnAdDestroy();
        finish();
    }

}
