package com.coding.acronymism.room.dao

import androidx.room.*
import com.coding.acronymism.room.entity.AcronymMeaning

@Dao
interface AcronymMeaningDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(item: List<AcronymMeaning>): List<Long>

    @Query("SELECT * from acronym_meaning_table WHERE acronym_id=:acronymId ORDER BY id ASC")
    fun getAll(acronymId: Long): List<AcronymMeaning>

    @Query("DELETE FROM acronym_meaning_table WHERE acronym_id=:acronymId")
    fun delete(acronymId: Long)

    @Delete
    fun delete(item: AcronymMeaning)
}