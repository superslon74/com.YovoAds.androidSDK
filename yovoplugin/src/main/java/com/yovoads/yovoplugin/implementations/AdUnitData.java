package com.yovoads.yovoplugin.implementations;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

public class AdUnitData
{
    public String m_id = "";
    public String m_clickURL =  null;
    public String m_urlIcon = null;
    public String m_urlScreen = null;
    public String m_title = "---";
    public String m_description = "-----";
    public int m_showTime = 5;
    public int m_screenWidth = 0;
    public int m_screenHeight = 0;
    public Bitmap m_bitmapIcon = null;
    public Bitmap m_bitmapScreen = null;


    public String Set(String _json)
    {
        try {
            JSONObject _obj = new JSONObject(_json);
            if(_obj.getString("error").isEmpty()) {

                _obj = _obj.getJSONObject("ad");
                m_id = _obj.getString("AdID");
                m_clickURL = _obj.getString("ClickURL");
                m_urlIcon = _obj.getString("IconURLImg");
                m_urlScreen = _obj.getString("ScreenshotURLImg");
                m_title = _obj.getString("Title");
                m_description = _obj.getString("Description");
                m_showTime = _obj.getInt("ShowTime");
                m_screenWidth = _obj.getInt("ImgW");
                m_screenHeight = _obj.getInt("ImgH");
            } else {
                return  "ERROR_CODE_hjgsdflaivcnsjhsgs";
            }
        } catch (JSONException e) {
            return  "ERROR_CODE_hsnsjghfdfldid";
        }
        return "";
    }
}
