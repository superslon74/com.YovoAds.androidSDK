package com.yovoads.yovoplugin.core;

import com.yovoads.yovoplugin.common.IYHttpConnectResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class YBaseHttp {

    public static String CreateConnect(String _url, String _postParams, IYHttpConnectResult _iHttpConnectResult) {

        HttpURLConnection _connection = null;
        try {

            URL myurl = new URL(_url);
            _connection = (HttpURLConnection) myurl.openConnection();

            _connection.setDoOutput(true);
            _connection.setConnectTimeout(5000);
            _connection.setReadTimeout(5000);
            _connection.setInstanceFollowRedirects(false);
            _connection.setUseCaches(false);
            _connection.setRequestMethod("POST");
            _connection.setRequestProperty("User-Agent", "Java client");
            _connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


            OutputStream out = _connection.getOutputStream();
            PrintWriter print = new PrintWriter(new OutputStreamWriter(out, "UTF-8"));
            print.append(_postParams);
            print.flush();
            print.close();
            out.close();
            _connection.connect();

            StringBuilder _answerStr = new StringBuilder();;
            BufferedReader _bufferedReader = new BufferedReader(new InputStreamReader(_connection.getInputStream()));
            String line;

            while ((line = _bufferedReader.readLine()) != null) {
                _answerStr.append(line);
                _answerStr.append("\n");
            }

            return _answerStr.toString();

        } catch (ProtocolException e) {
            e.printStackTrace();
            _iHttpConnectResult.ResultError("asj1d564aksdj " + e.getMessage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            _iHttpConnectResult.ResultError("sdjh2345jshfds " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            _iHttpConnectResult.ResultError("kshs3d9a7jshsh " + e.getMessage());
        } finally {
            _connection.disconnect();
        }
        return null;
    }



}
