package com.gotenna.controllers

import android.content.Context
import com.gotenna.R
import com.gotenna.views.MapsActivity
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style

class MapController(private val mapsActivity: MapsActivity, private val mapView: MapView) : OnMapReadyCallback {

    private lateinit var mapboxMap: MapboxMap
    fun showLocation() {
            val locationComponentActivationOptions = LocationComponentActivationOptions
                .builder(mapsActivity, mapboxMap.style!!)
                .build()

            val locationComponent = mapboxMap.locationComponent
            locationComponent.activateLocationComponent(locationComponentActivationOptions)
            try {
                locationComponent.isLocationComponentEnabled = true
            } catch (e : SecurityException) {

            }
            locationComponent.cameraMode = CameraMode.TRACKING
            locationComponent.renderMode = RenderMode.COMPASS
    }


    fun setupMap() {
        mapView.getMapAsync(this)
    }

    override fun onMapReady(map: MapboxMap) {
        mapboxMap = map
        mapboxMap.setStyle(Style.MAPBOX_STREETS) {
           mapsActivity.handleMapReady()
        }
    }

    interface MapControllerInterface {
        fun handleMapReady()
    }
}