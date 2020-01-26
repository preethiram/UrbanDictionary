package com.example.urbandictionary.api


import com.example.urbandictionary.model.DictionaryResponse
import io.reactivex.Observable
import javax.inject.Inject

class AppRepository @Inject constructor(private val api:AppServices) {

    fun getDictionary(serach_key: String):Observable<DictionaryResponse>{
        return api.getDictionary(serach_key)
    }
}