package com.coding.acronymism.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.coding.acronymism.room.dao.AcronymDao
import com.coding.acronymism.room.dao.AcronymMeaningDao
import com.coding.acronymism.room.entity.Acronym
import com.coding.acronymism.room.entity.AcronymMeaning

@Database(
    entities = [Acronym::class, AcronymMeaning::class],
    version = 4,
    exportSchema = false
)
abstract class AcronymDatabase : RoomDatabase() {
    abstract fun acronymDao(): AcronymDao
    abstract fun acronymMeaningDao(): AcronymMeaningDao

    companion object {
        @Volatile
        private var INSTANCE: AcronymDatabase? = null

        private const val DATABASE_NAME = "acronym_database"

        fun getDatabase(context: Context): AcronymDatabase? {
            if (INSTANCE == null) {
                synchronized(AcronymDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AcronymDatabase::class.java, DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}