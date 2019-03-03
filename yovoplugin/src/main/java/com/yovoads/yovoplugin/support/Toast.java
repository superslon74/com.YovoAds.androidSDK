package com.yovoads.yovoplugin.support;

import com.yovoads.yovoplugin.DI;

public class Toast {

    public static void ShowShort(int _showTime, String _message)
    {
        if (_showTime == 0) {
            android.widget.Toast.makeText(DI.m_activity, _message, android.widget.Toast.LENGTH_SHORT).show();
        } else {
            android.widget.Toast.makeText(DI.m_activity, _message, android.widget.Toast.LENGTH_LONG).show();
        }
    }
}
