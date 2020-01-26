package com.example.urbandictionary.api


import com.example.urbandictionary.model.DictionaryResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface AppServices {

    @GET("define")
    fun getDictionary(@Query("term")term :String): Observable<DictionaryResponse>

}