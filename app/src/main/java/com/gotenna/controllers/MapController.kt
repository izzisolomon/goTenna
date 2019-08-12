package com.gotenna.controllers

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
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
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.plugins.annotation.LineManager
import com.mapbox.mapboxsdk.plugins.annotation.LineOptions
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager
import com.mapbox.mapboxsdk.style.layers.PropertyFactory


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
        locationComponent.zoomWhileTracking(17.0)
    }


    fun setupMap() {
        mapView.getMapAsync(this)
        dataManager = DataManger(mapsActivity)
    }

    fun changeMapStyle(style: String) {
        mapboxMap.setStyle(style)
    }

    fun setupMarkers(markers: List<MarkerObject>) {
        val markerViewManager = MarkerViewManager(mapView, mapboxMap)

        for (marker: MarkerObject in markers) {
            val view = LayoutInflater.from(mapsActivity).inflate(R.layout.marker_view, null) as LinearLayout
            view.layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            view.setOnClickListener {
                mapsActivity.setSelected(marker)
            }
            val marker = MarkerView(LatLng(marker.latitude, marker.longitude), view)
            marker.let {
                markerViewManager.addMarker(it)
            }
        }
    }

    fun goToPosition(marker : MarkerObject) {
        val position = CameraPosition.Builder()
            .target(LatLng(marker.latitude, marker.longitude))
            .zoom(17.0)
            .build()

        mapboxMap.cameraPosition = position
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