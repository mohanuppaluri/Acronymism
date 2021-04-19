package com.coding.acronymism.ui.fragment.searchfragment

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.coding.acronymism.model.AcronymResponse
import com.coding.acronymism.networrk.RetrofitSingleton
import com.coding.acronymism.room.entity.Acronym
import com.coding.acronymism.room.entity.AcronymMeaning
import com.coding.acronymism.room.repository.AcronymRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModelFactory constructor(private val acronymRepository: AcronymRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            SearchViewModel(this.acronymRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}

class SearchViewModel(private val acronymRepository: AcronymRepository) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _result: MutableLiveData<List<AcronymResponse>> = MutableLiveData()
    val result: LiveData<List<AcronymResponse>> = _result

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: MutableLiveData<Throwable> = _error

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    val newAcronymId: MediatorLiveData<Long> = MediatorLiveData()

    init {
        newAcronymId.addSource(acronymRepository.newAcronymId, Observer { id ->
            id?.let { _id ->
                result.value?.first()?.lfs?.map { it -> it.toAcronymMeaning(_id) }?.let { list ->
                    addAcronymMeaning(list)
                }
            }
        })

    }

    fun searchAcronym(searchString: String, mode: SearchMode) {
        _isLoading.postValue(true)

        val result = acronymRepository.getAcronym(searchString)

        if (result != null && result.isNullOrEmpty().not()) {
            result.first().id?.let { id ->
                val meanings = acronymRepository.getAcronymMeaning(id)

                if (meanings != null && meanings.isNullOrEmpty().not()) {
                    handleResponse(listOf(
                        AcronymResponse(
                            sf = searchString,
                            lfs = meanings.map { it.toMeaning() }
                        )
                    ), true)
                }
            }
        } else {
            mCompositeDisposable.add(
                if (mode == SearchMode.SHORT_FORM) {
                    RetrofitSingleton.requestInterface.getShortForm(searchString)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                            { response -> handleResponse(response) },
                            { t -> handleError(t) })
                } else {
                    RetrofitSingleton.requestInterface.getFullForm(searchString)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                            { response -> handleResponse(response) },
                            { t -> handleError(t) })
                }
            )
        }
    }

    fun validateSearchString(searchString: String) = searchString.isNotEmpty()

    private fun handleResponse(response: List<AcronymResponse>, isFromDatabase: Boolean = false) {
        _isLoading.postValue(false)
        _result.postValue(response)

        if (response.isNotEmpty() && isFromDatabase.not()) {
            response.first().sf?.let { sf ->
                addAcronym(Acronym(name = sf))
            }
        }
    }

    private fun handleError(error: Throwable) {
        _isLoading.postValue(false)
        _error.postValue(error)
    }

    @VisibleForTesting
    fun addAcronym(acronym: Acronym) {
        acronymRepository.addAcronym(acronym)
    }

    @VisibleForTesting
    fun addAcronymMeaning(acronymMeanings: List<AcronymMeaning>) {
        acronymRepository.addAcronymMeaning(acronymMeanings)
    }

    @VisibleForTesting
    fun deleteAcronym(acronym: Acronym) {
        acronymRepository.deleteAcronym(acronym)
    }

    enum class SearchMode {
        SHORT_FORM,
        FULL_FORM
    }

}