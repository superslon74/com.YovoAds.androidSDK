package com.yovoads.yovoplugin;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.yovoads.yovoplugin.core.dbLocal;


public class GAID extends AsyncTask<Context, Void, String> {

    public GAID() {

    }

    @Override
    protected String doInBackground(Context... params) {
        Context _context = params[0];

        AdvertisingIdClient.Info _adsIdClien;
        String _gaid = "";
        try {
            _adsIdClien = AdvertisingIdClient.getAdvertisingIdInfo(_context);
            try{
                if (_adsIdClien != null) {
                    _gaid = _adsIdClien.getId();
                }
            } catch (Exception e){
                DI.getInstance().OnSetGaid("", "error 1 : " + e.toString());
            }
        } catch (Exception e) {
            DI.getInstance().OnSetGaid("", "error 2 : " + e.toString());
        }

        if(_gaid.isEmpty())
        {
            _gaid = dbLocal.getInstance().GetGaid();
        }

        return _gaid;
    }

    @Override
    protected void onPostExecute(final String _gaid) {
        super.onPostExecute(_gaid);
        DI.getInstance().OnSetGaid(_gaid, "");
    }
}
