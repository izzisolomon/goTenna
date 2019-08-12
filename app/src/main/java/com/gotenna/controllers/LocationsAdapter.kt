package com.gotenna.controllers

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gotenna.R
import com.gotenna.data.MarkerObject
import kotlinx.android.synthetic.main.location_item.view.*

class LocationsAdapter (private val context: Context, private val locations: List<MarkerObject>): RecyclerView.Adapter<LocationsAdapter.LocationItem>() {


    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LocationItem{
        return  LocationItem(LayoutInflater.from(context).inflate(R.layout.location_item, p0, false))
    }

    override fun onBindViewHolder(p0: LocationItem, p1: Int) {
        val location = locations[p1]
        p0.name.text = location.name
        p0.description.text = location.description

        // hide divider if this is the last item
        if (p1 == locations.size - 1) p0.divider.visibility = View.GONE
    }


    interface OnItemClick {
        fun onItemClick(item: MarkerObject)
    }


    class LocationItem(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.item_name
        val description: TextView = view.item_description
        val divider: View = view.divider
    }

}