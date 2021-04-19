package com.coding.acronymism.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coding.acronymism.model.Meaning

@Entity(tableName = "acronym_meaning_table")
data class AcronymMeaning(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "acronym_id")
    var acronymId: Long,

    @ColumnInfo(name = "meaning")
    var meaning: String,

    @ColumnInfo(name = "freq")
    var freq: Int,

    @ColumnInfo(name = "since")
    var since: Int
) {
    override fun toString(): String {
        return meaning
    }

    fun toMeaning(): Meaning {
        return Meaning(
            lf = meaning,
            freq = freq,
            since = since
        )
    }
}
