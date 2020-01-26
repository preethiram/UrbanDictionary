package com.example.urbandictionary.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionary.R
import com.example.urbandictionary.model.DictionaryModel
import com.jakewharton.rxbinding.widget.RxTextView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription


import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    lateinit var linearLayoutManager: RecyclerView.LayoutManager

    lateinit var adapter: DictionaryAdapter
    lateinit var compositeSubscription: CompositeSubscription

    var list = mutableListOf<DictionaryModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // injecting activity
        AndroidInjection.inject(this)

        compositeSubscription = CompositeSubscription()
        addAdapter()
        observeData()
        addTextWatcher(edt_search)
    }

    private fun addAdapter(){

        linearLayoutManager = LinearLayoutManager(this)
        adapter = DictionaryAdapter(list)
        dic_recycler_view.layoutManager = linearLayoutManager
        dic_recycler_view.adapter = adapter
    }

    private fun observeData(){

        mainViewModel.getDictionaryList().observe(this, Observer {
            if (it != null) {

                notifiAdapter(it.list)
            }
        })

        mainViewModel.getProgressStatus().observe(this, Observer {

            if (it) {
                progress_bar.visibility = View.VISIBLE
            } else {
                progress_bar.visibility = View.GONE
            }
        })

    }

    override fun onStart() {
        super.onStart()

         makeSearch()

    }

    private fun addTextWatcher(editText: EditText) {

        val textWatcher = object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().length < 2 ) {
                    list.clear()
                    notifiAdapter(list)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        }

        editText.addTextChangedListener(textWatcher)
    }

   private fun makeSearch(){

        val subscription = RxTextView.textChanges(edt_search)
            .debounce(1000, TimeUnit.MILLISECONDS)
            .filter { changes ->
                changes.trim().length > 1
            }
            .switchMap { it -> rx.Observable.just(it.toString()) }
            .observeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                mainViewModel.callDictionaryApi(it.toString())

            })
      compositeSubscription.add(subscription)

    }

    fun notifiAdapter(dicList: List<DictionaryModel>) {

        list.clear()
        list.addAll(dicList)
        adapter.notifyDataSetChanged()

        if(list.size > 0) {

            txt_no_data.visibility = View.GONE
            dic_recycler_view.visibility = View.VISIBLE
        }else {

            txt_no_data.visibility = View.VISIBLE
            dic_recycler_view.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if(compositeSubscription.hasSubscriptions())
            compositeSubscription.clear()
    }

}
