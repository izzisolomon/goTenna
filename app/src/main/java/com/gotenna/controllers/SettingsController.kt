package com.gotenna.controllers

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.widget.PopupMenu
import com.gotenna.R
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.android.synthetic.main.activity_maps.*

class SettingsController (private val context: Context, private val view: FloatingActionButton, private val mapController: MapController) {

    private lateinit var menu: PopupMenu

    init {
        setupSettingsMenu()
    }
    private fun setupSettingsMenu() {
        menu = PopupMenu(context, view)
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

    fun show() {
        if (::menu.isInitialized) menu.show()
    }

}