package com.gotenna.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(MarkerObject::class), version = 1, exportSchema = false)
abstract class MarkerDB : RoomDatabase() {
    abstract fun markerDao(): MarkerDao
}