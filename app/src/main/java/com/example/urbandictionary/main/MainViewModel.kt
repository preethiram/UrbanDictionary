package com.example.urbandictionary.main

import android.util.Log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.urbandictionary.api.AppRepository
import com.example.urbandictionary.model.DictionaryModel
import com.example.urbandictionary.model.DictionaryResponse
import com.jakewharton.rxbinding.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import rx.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor(private val appRepository: AppRepository) :ViewModel(){

    private var dictionaryList = MutableLiveData<DictionaryResponse>()
    private var loadingStatus = MutableLiveData<Boolean>()
    private val compositeDisposable = CompositeDisposable()


    fun callDictionaryApi(search_key : String) {

        loadingStatus.postValue(true)

       compositeDisposable.add(appRepository.getDictionary(search_key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                data -> dictionaryList.postValue(data)
                loadingStatus.postValue(false)

            },
                {
                    Log.i("Exception",it.message)
                    loadingStatus.postValue(false)
                }))
  }
    fun getDictionaryList():MutableLiveData<DictionaryResponse> {
        return dictionaryList
    }

    fun getProgressStatus():MutableLiveData<Boolean> {
        return loadingStatus
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}