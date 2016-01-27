package com.tae.letscook.service;

import android.os.AsyncTask;

import com.tae.letscook.model.Event;

/**
 * Created by Eduardo on 25/01/2016.
 */
public class SaveEventTask extends AsyncTask<Event, Void, Boolean> {
    @Override
    protected Boolean doInBackground(Event... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
