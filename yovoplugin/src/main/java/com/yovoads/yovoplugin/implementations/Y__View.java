package com.yovoads.yovoplugin.implementations;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.common.EPivol;
import com.yovoads.yovoplugin.core.YImage;

public abstract class Y__View extends Activity {

    protected boolean m_isFirst = true;
    protected FrameLayout m_frameLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        m_frameLayout = new FrameLayout(DI.m_activity);
        m_frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected abstract void SetOrientationPortrait(float _autoScaleWidth);
    protected abstract void SetOrientationLandscape(float _autoScaleWidth);

    protected YImage CreateImageView(int _idResource, float _widthImageOR, float _heightImageOR, float _scaleRelativelyScreenHeight, float _offsetLeft, float _offsetTop, EPivol _pivolX, EPivol _pivolY) {
        YImage _image = new YImage(DI.m_activity);
        if(_idResource > 0) {
            _image.setImageResource(_idResource);
        }
        float _cof = _widthImageOR / _heightImageOR;
        float _height = _heightImageOR * DI._DISPLAY_HEIGHT / _heightImageOR * _scaleRelativelyScreenHeight;
        int _width = (int) (_cof * _height);
        _image.SetSize(_width, (int)_height);

        _image.SetPivol(_pivolX, _pivolY);
        _image.SetPos(_offsetLeft, _offsetTop);

        m_frameLayout.addView(_image);
        return _image;
    }

    protected TextView CreateTextView(float _scaleRelativelyScreenHeight, int _gravity, float _offsetLeft, float _offsetTop, float _offsetRight, float _offsetBotton) {
        TextView _text = new TextView(DI.m_activity);
        _text.setTextSize(TypedValue.COMPLEX_UNIT_PX, _scaleRelativelyScreenHeight * (float) DI._DISPLAY_HEIGHT);
        _text.setGravity(_gravity);
        m_frameLayout.addView(_text);
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) _text.getLayoutParams();
        mlp.setMargins((int) (_offsetLeft * (float) DI._DISPLAY_WIDTH), (int) (_offsetTop * (float) DI._DISPLAY_HEIGHT), (int) (_offsetRight * (float) DI._DISPLAY_WIDTH), (int) (_offsetBotton * (float) DI._DISPLAY_WIDTH));
        return _text;
    }

    protected void SetTextAdsStyle(EAdNetworkType _adNetworkType, TextView _textAds) {

        _textAds.setText("   Ads");
        switch (_adNetworkType) {
            case _CROSS_PROMOTION:
                _textAds.setTextColor(Color.parseColor("#488be6"));
                break;
            case _EXCHANGE:
                _textAds.setTextColor(Color.parseColor("#ffff00"));
                break;
            case _YOVO_ADVERTISING:
                _textAds.setTextColor(Color.parseColor("#86da51"));
                break;
        }
    }

    protected abstract void Quit();
}
