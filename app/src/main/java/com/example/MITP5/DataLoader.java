package com.example.MITP5;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;

public class DataLoader extends AsyncTask<String, Void, String> {

    private Context localContext;

    public DataLoader (Context context){
        localContext = context;
    }

    protected String doInBackground(String... params) {
        try {
            Log.d("LOGGER", "DataLoader.doInBackground() is executed!");
            return DataManager.getRateFromECB();
        } catch (IOException e) {
            Log.d("LOGGER", "DataLoader.doInBackground() FAILED!!!!!!! IOException");
            Log.d("LOGGER", e.toString());
            return "failed to load";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

}
