package com.yovoads.yovoplugin.implementations.banner;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.R;
import com.yovoads.yovoplugin.common.EGravity;
import com.yovoads.yovoplugin.core.AdUnit.Banners;


public class YBannerBackground extends Fragment {


    private static YBannerBackground mc_this;

    private  FrameLayout.LayoutParams m_layoutParams;
    private  View m_view;

    public static String m_colorBackground = null;
    public static boolean m_isBackgroundShow = true;


    public static YBannerBackground getInstance() {

        if(mc_this == null)
        {
            mc_this = new YBannerBackground();
        }
        return mc_this;
    }

    public YBannerBackground() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        m_view = inflater.inflate(R.layout.fragment_ybanner_background, container, false);

        m_view.setBackgroundColor((m_colorBackground == null) ? Color.parseColor("#212121") : Color.parseColor(m_colorBackground));
        m_layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (DI.getInstance().GetBannerHeight_float() * 1.03));

        if (Banners.getInstance().m_gravity == EGravity._BOTTON || Banners.getInstance().m_gravity == EGravity._BOTTON_LEFT || Banners.getInstance().m_gravity == EGravity._BOTTON_RIGHT) {
            m_layoutParams.gravity = Gravity.BOTTOM + Gravity.CENTER_HORIZONTAL;
        } else {
            m_layoutParams.gravity = Gravity.TOP + Gravity.CENTER_HORIZONTAL;
        }

        m_view.setLayoutParams(m_layoutParams);

        return m_view;
    }

    @Override
    public void onViewCreated(View _view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(_view, savedInstanceState);

        if (m_isBackgroundShow) {
            BannerSetBackground(true);
        } else {
            BannerSetBackground(false);
        }
    }

    public void BannerSetBackground(final boolean _isBackgroundShow) {
        m_isBackgroundShow = _isBackgroundShow;
        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if(m_isBackgroundShow)
                {
                    FragmentTransaction _ft = getFragmentManager().beginTransaction();
                    _ft.show(YBannerBackground.getInstance());
                    _ft.commitAllowingStateLoss();
                }
                else
                {
                    FragmentTransaction _ft = getFragmentManager().beginTransaction();
                    _ft.hide(YBannerBackground.getInstance());
                    _ft.commitAllowingStateLoss();
                }
            }
        });
    }

    public void BannerSetBackgroundColor(final String _color)
    {
        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                FragmentTransaction _ft = getFragmentManager().beginTransaction();
                getView().setBackgroundColor(Color.parseColor(_color));
                _ft.commitAllowingStateLoss();
            }
        });
    }

    public void SetGravityTop()
    {
        m_layoutParams.gravity = Gravity.TOP + Gravity.CENTER_HORIZONTAL;
        m_view.setLayoutParams(m_layoutParams);
    }

    public void SetGravityBotton()
    {
        m_layoutParams.gravity = Gravity.BOTTOM + Gravity.CENTER_HORIZONTAL;
        m_view.setLayoutParams(m_layoutParams);
    }


}