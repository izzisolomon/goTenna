package com.gotenna.data

import android.arch.persistence.room.*

@Dao
interface MarkerDao {
    @Query("SELECT * FROM MarkerObject")
    fun getAll(): List<MarkerObject>

    @Query("SELECT * FROM MarkerObject WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): MarkerObject

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg markers: MarkerObject)

    @Delete
    fun delete(marker: MarkerObject)
}