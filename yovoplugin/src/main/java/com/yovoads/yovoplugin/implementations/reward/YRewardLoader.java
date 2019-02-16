package com.yovoads.yovoplugin.implementations.reward;

import android.os.AsyncTask;
import android.widget.Toast;

import com.yovoads.yovoplugin.DI;
import com.yovoads.yovoplugin.YovoSDK;
import com.yovoads.yovoplugin.common.EAdNetworkType;
import com.yovoads.yovoplugin.implementations.AdUnitTypeMode;
import com.yovoads.yovoplugin.implementations.IOnEventLoaderPicture;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Date;


// https://ru.stackoverflow.com/questions/412582/%D0%A1%D0%BE%D0%B7%D0%B4%D0%B0%D0%BD%D0%B8%D0%B5-%D0%BF%D0%B0%D0%BF%D0%BA%D0%B8-%D0%B2-%D0%B4%D0%B8%D1%80%D0%B5%D0%BA%D1%82%D0%BE%D1%80%D0%B8%D0%B8-%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D1%8F
// https://stackoverflow.com/questions/9215004/android-file-writing-read-only-file-system-warning
// https://www.baeldung.com/java-download-file
public class YRewardLoader extends AsyncTask<String, Void, Void> {

    public final static String m_yovoPath = "/yovoads/";

    public static void CreateFolder() {
        File file = new File(DI.m_activity.getFilesDir(), m_yovoPath);
        if (!file.exists()){
            file.mkdir();
            file.setWritable(true);
        }
    }


    private AdUnitTypeMode me_adUnitTypeMode = null;
    private EAdNetworkType me_adNetworkType = null;
    private IOnEventLoaderPicture mi_onEventLoaderPicture = null;


    public YRewardLoader(AdUnitTypeMode _adUnitTypeMode, EAdNetworkType _adNetworkType, IOnEventLoaderPicture _onEventLoaderPicture)
    {
        me_adUnitTypeMode = _adUnitTypeMode;
        me_adNetworkType = _adNetworkType;
        mi_onEventLoaderPicture = _onEventLoaderPicture;
    }




private String _urlPath = "http://q5.yovoads.com/mp4_720.mp4";


Date _date = null;

    @Override
    protected Void doInBackground(String... _urlVideo) {
        try {

            URL _url = new URL(_urlVideo[0]);
            String _strPath = DI.m_activity.getFilesDir() + m_yovoPath + EAdNetworkType.GetValue(me_adNetworkType);
            File _file = new File(_strPath);
            if (_file.exists()) {
                _file.delete();
            }
            _date = new Date();
            try {
                ReadableByteChannel readableByteChannel = Channels.newChannel(_url.openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(_strPath);
                FileChannel fileChannel = fileOutputStream.getChannel();
                fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            } catch (Exception _err) {

                try {
                    BufferedInputStream _inputStream = new BufferedInputStream(_url.openStream());
                    FileOutputStream fileOutputStream = new FileOutputStream(_strPath);
                    OutputStreamWriter osw = new OutputStreamWriter(fileOutputStream);
                    byte dataBuffer[] = new byte[102400];
                    int bytesRead;
                    while ((bytesRead = _inputStream.read(dataBuffer, 0, 102400)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                    }
                } catch (Exception _err2) {
                    _err2.printStackTrace();
                    mi_onEventLoaderPicture.OnLoadingFailed(34535);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mi_onEventLoaderPicture.OnLoadingFailed(2131231);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void _void) {
        super.onPostExecute(_void);
        float _date2 = (float) ((new Date().getTime() - _date.getTime()) / 1000f);
        Toast.makeText(DI.m_activity, String.valueOf(_date2), Toast.LENGTH_LONG ).show();
        YovoSDK.ShowLog("LoaderVideo-time-", String.valueOf(_date2));
        mi_onEventLoaderPicture.OnLoading( me_adUnitTypeMode, null);
    }



}
