package com.yovoads.yovoplugin.implementations.banner;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.R;
import com.yovoads.yovoplugin.common.EScreenOrientation;
import com.yovoads.yovoplugin.common.IAdUnitOnMethod;
import com.yovoads.yovoplugin.core.AdUnit.Banners;
import com.yovoads.yovoplugin.implementations.AdUnitData;

public class YBannerView extends Fragment
{

    private static YBannerView mc_this;

    private AdUnitData m_adUnitDataActive = null;
    private IAdUnitOnMethod m_callbackActive = null;

    private  FrameLayout.LayoutParams m_layoutParams = null;
    private  View m_view = null;
    private float m_scaleFactor = 1;
    private int m_marginVertical = 0;

    private ImageView m_screen = null;
    private ImageView m_icon = null;
    private ImageView m_install = null;
    private ImageView m_star1 = null;
    private ImageView m_star2 = null;
    private ImageView m_star3 = null;
    private ImageView m_star4 = null;
    private ImageView m_star5 = null;
    private TextView m_title = null;
    private ImageView m_imageOnly = null;

    private boolean m_isFirstShow = true;

    public static YBannerView getInstance() {

        if(mc_this == null)
        {
            mc_this = new YBannerView();
        }
        return mc_this;
    }

    public YBannerView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        m_view = inflater.inflate(R.layout.fragment_ybanners_view, container, false);
        m_view.setBackgroundColor(Color.parseColor("#CCCCCC"));

        if(DI._IS_BANNER_HEIGHT_DOUBLE > 50 && DI.getInstance().Is215()) {
            m_scaleFactor = (DI._SCREEN_ORIENTATION == EScreenOrientation._PORTRAIT) ? (float) ((DI._DISPLAY_WIDTH / (6.4 * DI._DISPLAY_DPI * 50.0)) * 0.98f) : 1;
            m_marginVertical = (DI._SCREEN_ORIENTATION == EScreenOrientation._PORTRAIT) ? (((int) DI.getInstance().GetBannerHeight_float()) - (int) (DI._DISPLAY_DPI * 50.0 * m_scaleFactor)) : 0;
            m_layoutParams = new FrameLayout.LayoutParams((int) (m_scaleFactor * 6.4 * DI._DISPLAY_DPI * 50.0), (int)(DI._DISPLAY_DPI * 50.0 * m_scaleFactor));
            SetGravity();
        } else {
            m_scaleFactor = 1;
            m_marginVertical = 0;
            m_layoutParams = new FrameLayout.LayoutParams((int) (6.4 * DI.getInstance().GetBannerHeight_float()), DI.getInstance().GetBannerHeight_int());
            SetGravity();
        }

        m_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClick();
            }
        });

        /*m_imageOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClick();
            }
        });*/

        return m_view;
    }

    @Override
    public void onViewCreated(View _view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(_view, savedInstanceState);

        m_screen = getView().findViewById(R.id.bannerScreen);
        m_icon = getView().findViewById(R.id.bannerIcon);
        m_install = getView().findViewById(R.id.bannerInstall);
        m_star1 = getView().findViewById(R.id.bannerStar1);
        m_star2 = getView().findViewById(R.id.bannerStar2);
        m_star3 = getView().findViewById(R.id.bannerStar3);
        m_star4 = getView().findViewById(R.id.bannerStar4);
        m_star5 = getView().findViewById(R.id.bannerStar5);
        m_title = getView().findViewById(R.id.bannerTitle);

        m_imageOnly = getView().findViewById(R.id.bannerImageOnly);

        CreateXml();
        Hide();
    }

    private void OnClick()
    {
        if(m_callbackActive != null)
        {
            Intent intent = null;
            try {
                intent = new Intent("android.intent.action.VIEW", Uri.parse("googlechrome://navigate?url=" + m_adUnitDataActive.m_clickURL));
                DI.m_activity.startActivity(intent);
            }
            catch (Exception e) {
                intent = new Intent("android.intent.action.VIEW", Uri.parse(m_adUnitDataActive.m_clickURL));
                DI.m_activity.startActivity(intent);
            }
            m_callbackActive.OnAdClicked();
            m_callbackActive.OnAdDestroy();
        }
    }

    private void CreateXml() {

        float _bannerHeightFLOAT = 0;
        int _bannerHeightINT = 0;

        if (DI._IS_BANNER_HEIGHT_DOUBLE == 50 || !DI.getInstance().Is215()) {
            _bannerHeightFLOAT = DI.getInstance().GetBannerHeight_float();
        } else {
            _bannerHeightFLOAT = 50.0f * DI._DISPLAY_DPI;
        }

        m_screen.getLayoutParams().height = (int) (_bannerHeightFLOAT * m_scaleFactor);
        m_screen.getLayoutParams().width = (int) (_bannerHeightFLOAT * (246.0f / 120.0f) * m_scaleFactor);
        ((ViewGroup.MarginLayoutParams) m_screen.getLayoutParams()).setMargins(-0, 0, 0, 0);

        m_icon.getLayoutParams().height = (int) (_bannerHeightFLOAT * m_scaleFactor);
        m_icon.getLayoutParams().width = (int) (_bannerHeightFLOAT * m_scaleFactor);
       // int _marginRight = (int) (DI._IS_BANNER_HEIGHT_DOUBLE * DI._DISPLAY_DPI * 1.05 * (50.0 / DI._IS_BANNER_HEIGHT_DOUBLE) * m_scaleFactor);
        int _marginRight = (int) (DI._IS_BANNER_HEIGHT_DOUBLE * DI._DISPLAY_DPI * 1.05f * (50.0f / ((DI.getInstance().Is215()) ? DI._IS_BANNER_HEIGHT_DOUBLE : 50) * m_scaleFactor));
        ((ViewGroup.MarginLayoutParams) m_icon.getLayoutParams()).setMargins(0, 0, _marginRight, 0);

        m_install.getLayoutParams().height = (int) (_bannerHeightFLOAT * m_scaleFactor);
        m_install.getLayoutParams().width = (int) (_bannerHeightFLOAT * m_scaleFactor);
        ((ViewGroup.MarginLayoutParams) m_install.getLayoutParams()).setMargins(0, 0, 0, 0);

        int _starHeight = (int) (_bannerHeightFLOAT * m_scaleFactor * (21.0f / 50.0f));
        int _starBottonMargin = (int) (_bannerHeightFLOAT * ((3.0f * m_scaleFactor) / 50.0f));
        float _starPosStart = (float) (_bannerHeightFLOAT * m_scaleFactor * ((DI._IS_BANNER_HEIGHT_DOUBLE == 90) ? 1.0233f : 1.0f) * (246.0f / 120.0f) + (_bannerHeightFLOAT * 0.03f));
        float _starPosSpace = (_bannerHeightFLOAT * 0.034f * m_scaleFactor) + _starHeight;

        m_star1.getLayoutParams().height = _starHeight;
        m_star1.getLayoutParams().width = _starHeight;
        ((ViewGroup.MarginLayoutParams) m_star1.getLayoutParams()).setMargins((int) (_starPosStart), 0, 0, _starBottonMargin);

        m_star2.getLayoutParams().height = _starHeight;
        m_star2.getLayoutParams().width = _starHeight;
        ((ViewGroup.MarginLayoutParams) m_star2.getLayoutParams()).setMargins((int) (_starPosStart + _starPosSpace), 0, 0, _starBottonMargin);

        m_star3.getLayoutParams().height = _starHeight;
        m_star3.getLayoutParams().width = _starHeight;
        ((ViewGroup.MarginLayoutParams) m_star3.getLayoutParams()).setMargins((int) (_starPosStart + (_starPosSpace * 2)), 0, 0, _starBottonMargin);

        m_star4.getLayoutParams().height = _starHeight;
        m_star4.getLayoutParams().width = _starHeight;
        ((ViewGroup.MarginLayoutParams) m_star4.getLayoutParams()).setMargins((int) (_starPosStart + (_starPosSpace * 3)), 0, 0, _starBottonMargin);

        m_star5.getLayoutParams().height = _starHeight;
        m_star5.getLayoutParams().width = _starHeight;
        ((ViewGroup.MarginLayoutParams) m_star5.getLayoutParams()).setMargins((int) (_starPosStart + (_starPosSpace * 4)), 0, 0, _starBottonMargin);

        ((ViewGroup.MarginLayoutParams) m_title.getLayoutParams()).setMargins((int) (_bannerHeightFLOAT * 0), 0, 0, (int) (_bannerHeightFLOAT * 0.5f * m_scaleFactor));

        m_imageOnly.getLayoutParams().height = (int) (_bannerHeightFLOAT);
        m_imageOnly.getLayoutParams().width = (int) (_bannerHeightFLOAT * 6.4f);
    }

    private void SetTypeView(boolean _isImageOnly)
    {
        if(!_isImageOnly)
        {
            m_screen.setVisibility(View.VISIBLE);
            m_icon.setVisibility(View.VISIBLE);
            m_install.setVisibility(View.VISIBLE);
            m_star1.setVisibility(View.VISIBLE);
            m_star2.setVisibility(View.VISIBLE);
            m_star3.setVisibility(View.VISIBLE);
            m_star4.setVisibility(View.VISIBLE);
            m_star5.setVisibility(View.VISIBLE);
            m_title.setVisibility(View.VISIBLE);
            m_imageOnly.setVisibility(View.GONE);
        }
        else
        {
            m_screen.setVisibility(View.GONE);
            m_icon.setVisibility(View.GONE);
            m_install.setVisibility(View.GONE);
            m_star1.setVisibility(View.GONE);
            m_star2.setVisibility(View.GONE);
            m_star3.setVisibility(View.GONE);
            m_star4.setVisibility(View.GONE);
            m_star5.setVisibility(View.GONE);
            m_title.setVisibility(View.GONE);
            m_imageOnly.setVisibility(View.VISIBLE);
        }
    }

    private void SetGravity()
    {
        switch (Banners.getInstance().m_gravity)
        {
            case _TOP:
                SetGravityTop(Gravity.CENTER_HORIZONTAL);
                break;
            case _BOTTON:
                SetGravityBotton(Gravity.CENTER_HORIZONTAL);
                break;
            case _TOP_LEFT:
                SetGravityTop(Gravity.LEFT);
                break;
            case _TOP_RIGHT:
                SetGravityTop(Gravity.RIGHT);
                break;
            case _BOTTON_LEFT:
                SetGravityBotton(Gravity.LEFT);
                break;
            case _BOTTON_RIGHT:
                SetGravityBotton(Gravity.RIGHT);
                break;
        }
    }

    public void SetGravityTop(int _isLeft_isCenter_isRight)
    {
        m_layoutParams.gravity = Gravity.TOP + _isLeft_isCenter_isRight;
        m_layoutParams.setMargins(0, m_marginVertical, 0, 0);
        m_view.setLayoutParams(m_layoutParams);
    }

    public void SetGravityBotton(int _isLeft_isCenter_isRight)
    {
        m_layoutParams.gravity = Gravity.BOTTOM + _isLeft_isCenter_isRight;
        m_layoutParams.setMargins(0, 0, 0, m_marginVertical);
        m_view.setLayoutParams(m_layoutParams);
    }



    public void Show(AdUnitData _adUnitDataActive, IAdUnitOnMethod _callbackActive)
    {
        m_adUnitDataActive = null;
        m_adUnitDataActive = new AdUnitData();
        m_adUnitDataActive = _adUnitDataActive;
        m_callbackActive = _callbackActive;

        if(m_isFirstShow)
        {
            m_install.setImageResource(R.drawable.but_download);
            m_star1.setImageResource(R.drawable.star);
            m_star2.setImageResource(R.drawable.star);
            m_star3.setImageResource(R.drawable.star);
            m_star4.setImageResource(R.drawable.star);
            m_star5.setImageResource(R.drawable.star);
            ((TextView)getActivity().findViewById(R.id.bannerAds)).setText("Ads.");
            m_isFirstShow = false;
        }

        if(_adUnitDataActive.m_bitmapIcon == null)
        {
            m_imageOnly.setImageBitmap(_adUnitDataActive.m_bitmapScreen);
            SetTypeView(true);
        }
        else
        {
            m_icon.setImageBitmap(_adUnitDataActive.m_bitmapIcon);
            m_screen.setImageBitmap(_adUnitDataActive.m_bitmapScreen);
            m_title.setText(_adUnitDataActive.m_title);
            SetTypeView(false);
        }

        Show();
        m_callbackActive.OnAdShowing();
    }

    public void Show()
    {
        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                FragmentTransaction _ft = getFragmentManager().beginTransaction();
                _ft.show(mc_this);
                _ft.commitAllowingStateLoss();
            }
        });
    }

    public void Hide()
    {
        int a = 0;
        DI.m_activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                FragmentTransaction _ft = getFragmentManager().beginTransaction();
                _ft.hide(mc_this);
                _ft.commitAllowingStateLoss();
            }
        });
    }

//    public void OnAdDestroy()
//    {
//        Hide();
//        m_callbackActive.OnAdClosed();
//        m_callbackActive.OnAdDestroy();
//    }

}