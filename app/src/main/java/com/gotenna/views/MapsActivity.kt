package com.gotenna.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gotenna.controllers.LocationPermissionController
import com.gotenna.controllers.MapController
import com.gotenna.R
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView


class MapsActivity : AppCompatActivity(), LocationPermissionController.LocationPermissionResponse, MapController.MapControllerInterface {

    private lateinit var locationPermissionController: LocationPermissionController
    private lateinit var mapController: MapController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Mapbox.getInstance(this, resources.getString(R.string.mapbox_access_token))
        setContentView(R.layout.activity_maps)

        val mapView = findViewById<MapView>(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapController = MapController(this, mapView)
        locationPermissionController = LocationPermissionController(this)

        mapController.setupMap()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        locationPermissionController.handlePermissionResult(requestCode, permissions, grantResults)
    }

    override fun handleResponse(granted: Boolean) {
        if (granted) mapController.showLocation()
    }

    override fun handleMapReady() {
        // need to wait for the map to be ready, before attempting to display location
        // since the location circle requires the map style as a param
        locationPermissionController.checkLocationPermission()
    }
}
