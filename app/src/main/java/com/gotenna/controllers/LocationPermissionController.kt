package com.gotenna.controllers

import com.gotenna.views.MapsActivity
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager

class LocationPermissionController(private val mapsActivity : MapsActivity) : PermissionsListener {


    private lateinit var permissionsManager : PermissionsManager

    fun checkLocationPermission () {
        if (PermissionsManager.areLocationPermissionsGranted(mapsActivity))
            mapsActivity.handleResponse(true)
        else {
            permissionsManager = PermissionsManager(this)
            permissionsManager.requestLocationPermissions(mapsActivity)
        }

    }

    fun handlePermissionResult(requestCode: Int, permissions: Array<String>,
                               grantResults: IntArray) {
        if (::permissionsManager.isInitialized)
            permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
    }

    override fun onPermissionResult(granted: Boolean) {
        mapsActivity.handleResponse(granted)
    }

    interface LocationPermissionResponse {
        fun handleResponse(granted: Boolean)
    }
}
