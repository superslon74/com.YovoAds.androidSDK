package com.yovoads.yovoplugin.common;

public enum EGravity {

    _TOP,
    _BOTTON,
    _TOP_LEFT,
    _TOP_RIGHT,
    _BOTTON_LEFT,
    _BOTTON_RIGHT,
    _LEFT,
    _RIGHT;


    public static EGravity GetName(int _index)
    {
        switch (_index)
        {
            case 0:
                return _TOP;
            case 1:
                return _BOTTON;
            case  2:
                return _TOP_LEFT;
            case  3:
                return _TOP_RIGHT;
            case  4:
                return _BOTTON_LEFT;
            case  5:
                return _BOTTON_RIGHT;
            case 6:
                return _LEFT;
            case 7:
                return _RIGHT;
        }

        return _BOTTON;
    }

    public static int GetIndex(EGravity _eGravity)
    {
        switch (_eGravity)
        {
            case _TOP:
                return 0;
            case _BOTTON:
                return 1;
            case  _TOP_LEFT:
                return 2;
            case  _TOP_RIGHT:
                return 3;
            case  _BOTTON_LEFT:
                return 4;
            case  _BOTTON_RIGHT:
                return 5;
            case _LEFT:
                return 6;
            case _RIGHT:
                return 7;
        }

        return 1;
    }
}
