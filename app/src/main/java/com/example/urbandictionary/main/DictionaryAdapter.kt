package com.example.urbandictionary.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionary.R
import com.example.urbandictionary.databinding.DictionayRecyclerItemBinding
import com.example.urbandictionary.model.DictionaryModel
import kotlinx.android.synthetic.main.dictionay_recycler_item.view.*

class DictionaryAdapter(private var list: List<DictionaryModel>) :
    RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DictionayRecyclerItemBinding.inflate(layoutInflater)
        return DictionaryViewHolder(binding)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        holder.bindData(list[position])
    }

   class DictionaryViewHolder(private val binding: DictionayRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {


       fun bindData(dictionaryModel: DictionaryModel){
           binding.item = dictionaryModel
           binding.executePendingBindings()
       }
    }

}