package com.yovoads.yovoplugin.implementations.banner;


import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.core.AdUnit.Banners;


public class YFragments extends FragmentActivity
{

    private static YFragments mc_this = null;

   // public static boolean   m_isShowingBanner = false;


    public static YFragments getInstance()
    {
        if(mc_this == null)
        {
            mc_this = new YFragments();
        }
        return mc_this;
    }

    private YFragments() {
        //m_isShowingBanner = false;
    }

    public void CreateBannerXml(final boolean _isBackgroundShow, final String _color)
    {
        YBannerBackground.m_isBackgroundShow = _isBackgroundShow;
        if(_color != null && !_color.isEmpty()) {
            YBannerBackground.m_colorBackground = _color;
        }

        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                FrameLayout _framelayout = new FrameLayout(DI.m_activity);
                _framelayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                DI.m_activity.addContentView(_framelayout.getRootView(), _framelayout.getLayoutParams());

                if(android.os.Build.VERSION.SDK_INT > 16) {
                    _framelayout.setId(View.generateViewId());
                }
                else {
                    _framelayout.setId(-862675712);
                }

                FragmentManager fragmentManager = DI.m_activity.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(_framelayout.getId(),  YBannerBackground.getInstance());
                fragmentTransaction.add(_framelayout.getId(),  YBannerView.getInstance());
                fragmentTransaction.commitAllowingStateLoss();
            }
        });
    }

    public void SetGravity() {
        switch (Banners.getInstance().m_gravity) {
            case _TOP:
                YBannerBackground.getInstance().SetGravityTop();
                YBannerView.getInstance().SetGravityTop(Gravity.CENTER_HORIZONTAL);
                break;
            case _BOTTON:
                YBannerBackground.getInstance().SetGravityBotton();
                YBannerView.getInstance().SetGravityBotton(Gravity.CENTER_HORIZONTAL);
                break;
            case _TOP_LEFT:
                YBannerBackground.getInstance().SetGravityTop();
                YBannerView.getInstance().SetGravityTop(Gravity.LEFT);
                break;
            case _TOP_RIGHT:
                YBannerBackground.getInstance().SetGravityTop();
                YBannerView.getInstance().SetGravityTop(Gravity.RIGHT);
                break;
            case _BOTTON_LEFT:
                YBannerBackground.getInstance().SetGravityBotton();
                YBannerView.getInstance().SetGravityBotton(Gravity.LEFT);
                break;
            case _BOTTON_RIGHT:
                YBannerBackground.getInstance().SetGravityBotton();
                YBannerView.getInstance().SetGravityBotton(Gravity.RIGHT);
                break;
        }
    }
}
