package com.coding.acronymism.model

import android.os.Parcelable
import com.coding.acronymism.room.entity.AcronymMeaning
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AcronymResponse(
    @SerializedName("sf")
    val sf: String? = null,
    @SerializedName("lfs")
    val lfs: List<Meaning>? = null
) : Parcelable

@Parcelize
data class Meaning(
    @SerializedName("lf")
    val lf: String? = null,
    @SerializedName("freq")
    val freq: Int? = null,
    @SerializedName("since")
    val since: Int? = null,
    @SerializedName("vars")
    val vars: List<Meaning>? = null
) : Parcelable {

    fun toAcronymMeaning(acronymId: Long): AcronymMeaning {
        return AcronymMeaning(
            acronymId = acronymId,
            meaning = lf.orEmpty(),
            freq = freq ?: 0,
            since = since ?: 0
        )
    }
}