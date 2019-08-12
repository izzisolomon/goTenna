package com.gotenna.controllers

import com.gotenna.data.MarkerObject
import com.gotenna.views.MapsActivity
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.CircleManager
import com.mapbox.mapboxsdk.plugins.annotation.CircleOptions
import android.widget.Toast
import com.gotenna.R
import com.gotenna.models.DataManger


class MapController(private val mapsActivity: MapsActivity, private val mapView: MapView) : OnMapReadyCallback {

    private lateinit var mapboxMap: MapboxMap
    private lateinit var dataManager: DataManger

    fun showLocation() {
        val locationComponentActivationOptions = LocationComponentActivationOptions
            .builder(mapsActivity, mapboxMap.style!!)
            .build()

        val locationComponent = mapboxMap.locationComponent
        locationComponent.activateLocationComponent(locationComponentActivationOptions)
        try {
            locationComponent.isLocationComponentEnabled = true
        } catch (e: SecurityException) {
            Toast.makeText(mapsActivity, mapsActivity.resources.getString(R.string.location_denied), Toast.LENGTH_LONG).show()
        }
        locationComponent.cameraMode = CameraMode.TRACKING
        locationComponent.renderMode = RenderMode.COMPASS
        locationComponent.zoomWhileTracking(10.0)
    }


    fun setupMap() {
        mapView.getMapAsync(this)
        dataManager = DataManger(mapsActivity)
    }

    fun chngeMapStyle(style: String) {
        mapboxMap.setStyle(style)
    }

    fun setupMarkers(markers: List<MarkerObject>) {
        val circleManager = CircleManager(mapView, mapboxMap, mapboxMap.style!!)
        val circleOptionsList = ArrayList<CircleOptions>()

        for (marker: MarkerObject in markers) {
            circleOptionsList.add(
                CircleOptions()
                    .withLatLng(LatLng(marker.latitude, marker.longitude))
                    .withCircleRadius(8f)
                    .withDraggable(true)
            )
        }
        circleManager.create(circleOptionsList)
    }

    override fun onMapReady(map: MapboxMap) {
        mapboxMap = map
        mapboxMap.setStyle(Style.MAPBOX_STREETS) {
            mapsActivity.handleMapReady()
            dataManager.getdata()
        }
    }

    interface MapControllerInterface {
        fun handleMapReady()
    }
}