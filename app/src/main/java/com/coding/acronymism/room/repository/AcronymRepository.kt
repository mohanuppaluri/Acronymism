package com.coding.acronymism.room.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coding.acronymism.room.dao.AcronymDao
import com.coding.acronymism.room.dao.AcronymMeaningDao
import com.coding.acronymism.room.database.AcronymDatabase
import com.coding.acronymism.room.entity.Acronym
import com.coding.acronymism.room.entity.AcronymMeaning
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class AcronymRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var acronymDao: AcronymDao?
    private var acronymMeaningDao: AcronymMeaningDao?

    private val _newAcronymId: MutableLiveData<Long> = MutableLiveData()
    val newAcronymId: LiveData<Long> = _newAcronymId

    init {
        val db = AcronymDatabase.getDatabase(application)
        acronymDao = db?.acronymDao()
        acronymMeaningDao = db?.acronymMeaningDao()
    }

    fun addAcronym(acronym: Acronym) = launch { addAcronymBG(acronym) }

    fun addAcronymMeaning(acronymMeanings: List<AcronymMeaning>) =
        launch { addAcronymMeanings(acronymMeanings) }

    fun getAcronym(name: String) = acronymDao?.getByName(name)

    fun getAcronymMeaning(acronymId: Long) = acronymMeaningDao?.getAll(acronymId)

    fun deleteAcronym(acronym: Acronym) = launch { deleteAcronymBG(acronym) }

    private suspend fun addAcronymBG(acronym: Acronym) {
        withContext(Dispatchers.IO) {
            _newAcronymId.postValue(acronymDao?.add(acronym))
        }
    }

    private suspend fun addAcronymMeanings(acronymMeanings: List<AcronymMeaning>) {
        withContext(Dispatchers.IO) {
            acronymMeaningDao?.addAll(acronymMeanings)
        }
    }

    private suspend fun deleteAcronymBG(acronym: Acronym) {
        withContext(Dispatchers.IO) {
            acronymDao?.delete(acronym)
            acronym.id?.let {
                acronymMeaningDao?.delete(acronymId = it)
            }
        }
    }
}
