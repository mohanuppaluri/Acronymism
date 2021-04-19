package com.coding.acronymism.ui.fragment.searchfragment

import androidx.lifecycle.ViewModel
import com.coding.acronymism.model.Meaning

class AcronymMeaningItemViewModel(private val acronymMeaning: Meaning) : ViewModel() {
    val meaning: String
        get() = "${acronymMeaning.lf}"

    val freq: String
        get() = "${acronymMeaning.freq}"

    val since: String
        get() = "${acronymMeaning.since}"
}
