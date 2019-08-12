package com.gotenna.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json
import java.io.Serializable

@Entity
data class MarkerObject(
    @PrimaryKey @field:Json(name = "id") val id: Int,
    @ColumnInfo @field:Json(name = "name") val name: String,
    @ColumnInfo @field:Json(name = "latitude") val latitude: Double,
    @ColumnInfo @field:Json(name = "longitude") val longitude: Double,
    @ColumnInfo @field:Json(name = "description") val description: String
) : Serializable