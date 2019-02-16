package com.yovoads.yovoplugin.core;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.EPivol;

public class YImage extends android.support.v7.widget.AppCompatImageView {

    public int m_posX = 0;
    public int m_posY = 0;
    public int m_width = 0;
    public int m_height = 0;


    public YImage(Context context) {
        super(context);
    }


    public void SetSize(int _width, int _height) {
        m_width = _width;
        m_height = _height;
        setLayoutParams(new FrameLayout.LayoutParams(_width, _height));
    }

    public void SetPivol(EPivol _pivolX, EPivol _pivolY) {

        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) getLayoutParams();
        mlp.setMargins(
                (int) (((_pivolX == EPivol._CENTER) ? -0.5f : (_pivolX == EPivol._RIGHT) ? -1.0f : 0.0f) * (float) m_width),
                (int) (((_pivolY == EPivol._CENTER) ? -0.5f : (_pivolY == EPivol._BOTTON) ? -1.0f : 0.0f) * (float) m_height),
                mlp.rightMargin, mlp.bottomMargin);
    }

    public void SetPos(float _offsetLeft, float _offsetTop) {

        m_posX = (int) (_offsetLeft * (float) DI._DISPLAY_WIDTH);
        m_posY = (int) (_offsetTop * (float) DI._DISPLAY_HEIGHT);
        setX(m_posX);
        setY(m_posY);
    }



}