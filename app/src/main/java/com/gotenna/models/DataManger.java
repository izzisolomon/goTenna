package com.gotenna.models;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import com.gotenna.data.MarkerDB;
import com.gotenna.data.MarkerObject;
import com.gotenna.views.MapsActivity;

import java.util.List;
import java.util.concurrent.Callable;

public class DataManger implements Receiver<List<MarkerObject>> {

    private MapsActivity mapsActivity;
    private MarkerDB db;

    public DataManger(MapsActivity mapsActivity) {
        this.mapsActivity = mapsActivity;
    }

    Runnable setupDB(final List<MarkerObject> markerObjects) {
        db = Room.databaseBuilder(
                mapsActivity,
                MarkerDB.class, "markers"
        ).build();
        return new Runnable() {
            @Override
            public void run() {
                db.markerDao().insertAll(markerObjects.toArray(new MarkerObject[0]));
            }
        };
    }

    public void getdata() {
        try {
            // try to get values from local db first
            new GetDBData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } catch (Exception e) {
            loadFromApi();
        }
    }

    private void loadFromApi() {
        Api.Companion.newInstance().getData(this);
    }

    List<MarkerObject> getDBValues () {
        db = Room.databaseBuilder(
                mapsActivity,
                MarkerDB.class, "markers"
        ).build();

        return db.markerDao().getAll();
    }

    public class GetDBData extends AsyncTask<Void, Void, Void> {
        List<MarkerObject> list;
        @Override
        protected Void doInBackground(Void... params) {
           list = getDBValues();
           return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            if (list != null && list.size() > 0) mapsActivity.handleDataReady(list);
            else loadFromApi();
        }
    }


    @Override
    public void onRequestFailed(int statusCode) {

    }

    @Override
    public void onRequestSucceeded(List<MarkerObject> markerObjects) {
        mapsActivity.handleDataReady(markerObjects);
        AsyncTask.execute(setupDB(markerObjects));
    }

    public interface MarkersManagerInterface {
        void handleDataReady(List<MarkerObject> markerObjects);
    }
}
