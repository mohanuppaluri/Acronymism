package com.coding.acronymism.interfaces

import com.coding.acronymism.model.AcronymResponse
import com.coding.acronymism.networrk.Endpoints.GET_ALL_MEANINGS
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET(GET_ALL_MEANINGS)
    fun getFullForm(
        @Query("sf") sf: String
    ): Observable<List<AcronymResponse>>

    @GET(GET_ALL_MEANINGS)
    fun getShortForm(
        @Query("lf") lf: String
    ): Observable<List<AcronymResponse>>
}