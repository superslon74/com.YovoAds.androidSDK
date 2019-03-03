package com.yovoads.yovoplugin.support;

import android.app.Activity;

public class Support {

    private static Support mc_this = null;
    private static Popups m_popups = null;

    public static Support getInstance() {
        if (mc_this == null) {
            mc_this = new Support();
            m_popups = new Popups();
        }
        return mc_this;
    }

    private Support(){
    }

    public void ToastShow(int _showTime, String _message) {
        if (mc_this != null) {
            Toast.ShowShort(_showTime, _message);
        }
    }

    public void PopupsShow(String _title, String _mess, String _butYes) {
        if (mc_this != null) {
            m_popups.Show(_title, _mess, _butYes);
        }
    }

    public void PopupsShow(String _title, String _mess, String _butYes, String _butNo) {
        if (mc_this != null) {
            m_popups.Show(_title, _mess, _butYes, _butNo);
        }
    }

    public void PopupsShow(String _title, String _mess, String _butYes, String _butNo, String _butLaiter) {
        if (mc_this != null) {
            m_popups.Show(_title, _mess, _butYes, _butNo, _butLaiter);
        }
    }

    public void AppTryQuitShow(int _language) {
        if (mc_this != null) {
            m_popups.ShowAppTryQuit(_language);
        }
    }
}
