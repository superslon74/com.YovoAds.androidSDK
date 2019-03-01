package com.yovoads.yovoplugin.core;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.common.EPivol;

public class YImageData {

    public ImageView m_image = null;

    public int m_posX = 0;
    public int m_posY = 0;
    public int m_width = 0;
    public int m_height = 0;


    public YImageData(Context _context, int _idResource) {
        m_image = new ImageView(_context);
    }

    public void SetSize(int _width, int _height) {
        m_width = _width;
        m_height = _height;
        SetSize(m_image, m_width, m_height);
    }

    public static void SetSize(ImageView _image, int _width, int _height){
        _image.setLayoutParams(new FrameLayout.LayoutParams(_width, _height));
    }

    public void SetPivol(EPivol _pivolX, EPivol _pivolY) {
        SetPivol(m_image, _pivolX, _pivolY, (float) m_width, (float) m_height);
    }

    public static void SetPivol(ImageView _image, EPivol _pivolX, EPivol _pivolY, float _width, float _height) {

        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) _image.getLayoutParams();
        mlp.setMargins(
                (int) (((_pivolX == EPivol._CENTER) ? -0.5f : (_pivolX == EPivol._RIGHT) ? -1.0f : 0.0f) * _width),
                (int) (((_pivolY == EPivol._CENTER) ? -0.5f : (_pivolY == EPivol._BOTTON) ? -1.0f : 0.0f) * _height),
                mlp.rightMargin, mlp.bottomMargin);
    }

    public void SetPos(float _offsetLeft, float _offsetTop) {

        m_posX = (int) (_offsetLeft * (float) DI._DISPLAY_WIDTH);
        m_posY = (int) (_offsetTop * (float) DI._DISPLAY_HEIGHT);
        SetPos(m_image, _offsetLeft, _offsetTop);
    }

    public static void SetPos(ImageView _image, float _offsetLeft, float _offsetTop) {
        _image.setX((int) (_offsetLeft * (float) DI._DISPLAY_WIDTH));
        _image.setY((int) (_offsetTop * (float) DI._DISPLAY_HEIGHT));
    }


}