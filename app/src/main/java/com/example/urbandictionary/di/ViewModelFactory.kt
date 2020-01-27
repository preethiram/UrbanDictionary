package com.example.urbandictionary.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.urbandictionary.api.AppRepository
import com.example.urbandictionary.main.MainViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val api : AppRepository)  :ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(api) as T
        }
        throw IllegalAccessException("Unknown View model")
    }

}