package com.gotenna

import android.app.Application
import com.mapbox.mapboxsdk.Mapbox

class GoTennaApp : Application() {

    override fun onCreate() {
        super.onCreate()

// Mapbox Access token
        Mapbox.getInstance(applicationContext, resources.getString(R.string.mapbox_access_token))
    }
}