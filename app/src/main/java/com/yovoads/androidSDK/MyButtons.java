package com.yovoads.androidSDK;

import android.view.View;
import android.widget.Button;


import com.yovoads.yovoplugin.common.EGravity;

public class MyButtons {
    private static MyButtons mc_this;

    private Button m_butBannerShow;
    private Button m_butBannerHide;
    private Button m_butBannerSetBG;
    private Button m_butBannerSetColor;
    private Button m_butBannerSetGravity;

    private Button m_butInterShow;

    private Button m_butRewardShow;
    private Button m_butRewardShowIgnor;

    private Button m_butAppQuit;




    public static MyButtons getInstance() {
        if (mc_this == null) {
            mc_this = new MyButtons();
            mc_this.Init();
        }

        return mc_this;
    }

    public void Init() {
        m_butBannerShow = (Button) MainActivity.m_activity.findViewById(R.id.but_banner_show);
        m_butBannerHide = (Button) MainActivity.m_activity.findViewById(R.id.but_banner_hide);
        m_butBannerSetBG = (Button) MainActivity.m_activity.findViewById(R.id.but_banner_setBG);
        m_butBannerSetColor = (Button) MainActivity.m_activity.findViewById(R.id.but_banner_setColor);
        m_butBannerSetGravity = (Button) MainActivity.m_activity.findViewById(R.id.but_banner_setGravity);
        m_butInterShow = (Button) MainActivity.m_activity.findViewById(R.id.but_inter_show);
        m_butRewardShow = (Button) MainActivity.m_activity.findViewById(R.id.but_reward_show);
        m_butRewardShowIgnor = (Button) MainActivity.m_activity.findViewById(R.id.but_reward_showIgnor);
        m_butAppQuit = (Button) MainActivity.m_activity.findViewById(R.id.but_appQuit);

        m_butBannerShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.m_yovoSDK.BannerShow();
            }
        });

        m_butBannerHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.m_yovoSDK.BannerHide();
            }
        });

        m_butBannerSetBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Init.m_isBackground = !Init.m_isBackground;
                MainActivity.m_yovoSDK.BannerSetBackground(Init.m_isBackground);
            }
        });

        m_butBannerSetColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Init.m_color = (Init.m_color == "#FF0000") ? "#00FF00" : (Init.m_color == "#00FF00") ? "#0000FF" : (Init.m_color == "#0000FF") ? "#A0AFC9" : (Init.m_color == "#A0AFC9") ? "#FF0FF9" : "#FF0000";
                MainActivity.m_yovoSDK.BannerSetBackgroundColor(Init.m_color);
            }
        });

        m_butBannerSetGravity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (Init.m_gravity)
                {
                    case _TOP:
                        Init.m_gravity = EGravity._TOP_RIGHT;
                        MainActivity.m_yovoSDK.BannerSetGravity(EGravity.GetIndex(Init.m_gravity));
                        return;
                    case _TOP_RIGHT:
                        Init.m_gravity = EGravity._BOTTON_LEFT;
                        MainActivity.m_yovoSDK.BannerSetGravity(EGravity.GetIndex(Init.m_gravity));
                        return;
                    case _BOTTON_LEFT:
                        Init.m_gravity = EGravity._BOTTON;
                        MainActivity.m_yovoSDK.BannerSetGravity(EGravity.GetIndex(Init.m_gravity));
                        return;
                    case _BOTTON:
                        Init.m_gravity = EGravity._BOTTON_RIGHT;
                        MainActivity.m_yovoSDK.BannerSetGravity(EGravity.GetIndex(Init.m_gravity));
                        return;
                    case _BOTTON_RIGHT:
                        Init.m_gravity = EGravity._TOP_LEFT;
                        MainActivity.m_yovoSDK.BannerSetGravity(EGravity.GetIndex(Init.m_gravity));
                        return;
                    case _TOP_LEFT:
                        Init.m_gravity = EGravity._TOP;
                        MainActivity.m_yovoSDK.BannerSetGravity(EGravity.GetIndex(Init.m_gravity));
                        return;
                }
            }
        });

        m_butInterShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.m_yovoSDK.InterstitialShow();
                //DI.m_activity.startActivity(new Intent(DI.m_activity, YInterstitialView.class));
            }
        });

        m_butRewardShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.m_yovoSDK.RewardShow("key", "value");
            }
        });

        m_butRewardShowIgnor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.m_yovoSDK.RewardShowIgnore("key", "value");
            }
        });



        m_butAppQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.m_yovoSDK.AppQuit();
                System.exit(0);
            }
        });

    }

}
