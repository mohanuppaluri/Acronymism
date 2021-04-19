package com.coding.acronymism.room.dao

import androidx.room.*
import com.coding.acronymism.room.entity.Acronym

@Dao
interface AcronymDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(item: Acronym): Long

    @Query("SELECT * from acronym_table ORDER BY id ASC")
    fun getAll(): List<Acronym>

    @Query("SELECT * from acronym_table WHERE lower(name)= lower(:name) ORDER BY id ASC")
    fun getByName(name: String): List<Acronym>

    @Delete
    fun delete(item: Acronym)
}