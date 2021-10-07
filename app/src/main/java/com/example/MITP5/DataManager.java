package com.example.MITP5;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class DataManager {

    public static String getRateFromECB() throws IOException {
        String result = "";
        InputStream stream = downloadUrl("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
        try
        {
            result = XmlParser.getRatesFromECB(stream);
        }
        catch (IOException e)
        {
            Log.d("LOGGER", "DataManager.getRateFromECB() failed with IOException");
        }
        finally
        {
            if (stream != null)
            {
                stream.close();
            }
        }
        Log.d("LOGGER", "DataManager.GetRatesFromECB() is executed!");
        return result;
    }

    private static InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        Log.d("LOGGER", "DataManager.downloadUrl() is executed!");
        return conn.getInputStream();
    }
}
