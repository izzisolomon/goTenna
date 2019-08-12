package com.gotenna

import android.app.Application
import android.content.Context
import com.mapbox.mapboxsdk.Mapbox

class GoTennaApp : Application() {
    private var context: Context? = null


    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        // Mapbox Access token
        Mapbox.getInstance(applicationContext, resources.getString(R.string.mapbox_access_token))
    }

}