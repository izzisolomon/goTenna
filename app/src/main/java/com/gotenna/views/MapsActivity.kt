package com.gotenna.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.PopupMenu
import android.widget.Toast
import com.gotenna.controllers.LocationPermissionController
import com.gotenna.controllers.MapController
import com.gotenna.R
import com.gotenna.controllers.LocationsAdapter
import com.gotenna.data.MarkerObject
import com.gotenna.models.DataManger
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity(), LocationPermissionController.LocationPermissionResponse,
    MapController.MapControllerInterface, DataManger.MarkersManagerInterface, LocationsAdapter.OnItemClick {

    private lateinit var locationPermissionController: LocationPermissionController
    private lateinit var mapController: MapController
    private lateinit var locationsAdapter: LocationsAdapter
    private lateinit var markersList: RecyclerView
    private lateinit var menu : PopupMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Mapbox.getInstance(this, resources.getString(R.string.mapbox_access_token))
        setContentView(R.layout.activity_maps)

        setupSettingsMenu()
        markersList = markers_list
        val mapView = findViewById<MapView>(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapController = MapController(this, mapView)
        locationPermissionController = LocationPermissionController(this)

        mapController.setupMap()

        my_location.setOnClickListener {
            if (::mapController.isInitialized) mapController.showLocation()
            else Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show()
        }
    }

    fun setSelected(marker: MarkerObject) {
        if (::locationsAdapter.isInitialized) locationsAdapter.select(marker)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        locationPermissionController.handlePermissionResult(requestCode, permissions, grantResults)
    }

    override fun handleResponse(granted: Boolean) {
        if (granted) mapController.showLocation()
    }

    override fun handleMapReady() {
        // need to wait for the map to be ready before attempting to display location
        // because the location marker requires the map style as a param

        locationPermissionController.checkLocationPermission()
    }

    override fun handleDataReady(markers: List<MarkerObject>) {
        mapController.setupMarkers(markers)
        markersList.layoutManager = LinearLayoutManager(this)
        locationsAdapter = LocationsAdapter(this, markers, markers_list, this)
        map_settings.setOnClickListener {
            menu.show()
        }
        markersList.adapter = locationsAdapter
    }

    override fun onItemClick(item: MarkerObject) {
        mapController.goToPosition(item)
    }

    private fun setupSettingsMenu() {
        menu = PopupMenu(this, map_settings)
        menu.inflate(R.menu.map_style_menu)
        menu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.satellite -> {
                    it.isChecked = true
                    mapController.changeMapStyle(Style.SATELLITE)
                    true
                }
                R.id.light -> {
                    mapController.changeMapStyle(Style.LIGHT)
                    it.isChecked = true
                    true
                }
                R.id.dark -> {
                    mapController.changeMapStyle(Style.DARK)
                    it.isChecked = true
                    true
                }
                else -> {
                    mapController.changeMapStyle(Style.MAPBOX_STREETS)
                    it.isChecked = true
                    true
                }
            }
        }
    }

}
