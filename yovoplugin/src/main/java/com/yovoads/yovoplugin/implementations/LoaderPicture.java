package com.yovoads.yovoplugin.implementations;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoaderPicture extends AsyncTask<String, Void, Bitmap>
{

    private IOnEventLoaderPicture m_onEventLoaderPicture;
    private AdUnitTypeMode m_adUnitTypeMode;

    public LoaderPicture(AdUnitTypeMode _adUnitTypeMode, IOnEventLoaderPicture _onEventLoaderPicture)
    {
        m_adUnitTypeMode = _adUnitTypeMode;
        m_onEventLoaderPicture = _onEventLoaderPicture;
    }


    @Override
    protected Bitmap doInBackground(String... params) {
//        if (params.length == 0)
//            return null;
//        URL spec = params[0];
        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        try {
            URL myurl = new URL(params[0]);
            urlConnection = (HttpURLConnection) myurl.openConnection();
            urlConnection.setConnectTimeout(13000);
            urlConnection.setReadTimeout(13000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            //bitmap = BitmapFactory.decodeStream(inputStream);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[1024];

            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            inputStream.close();
            buffer.flush();
            byte[] imageBytes = buffer.toByteArray();


            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);

            bitmap = BitmapCreator.createBitmap(options.outWidth, options.outHeight);

            options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            //options.inMutable = true;
            options.inBitmap = bitmap;
            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);

        } catch (Exception e) {
            //loadingFailed();
            m_onEventLoaderPicture.OnLoadingFailed(1);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap _bitmap) {
        super.onPostExecute(_bitmap);
        m_onEventLoaderPicture.OnLoading(m_adUnitTypeMode, _bitmap);
    }
}
