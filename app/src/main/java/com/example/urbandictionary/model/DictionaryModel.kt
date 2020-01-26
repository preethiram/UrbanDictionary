package com.example.urbandictionary.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class DictionaryModel(val definition:String,
                           val permalink:String,
                           val thumbs_up:Int,
                           val sound_urls :List<String>,
                           val word:String,
                           val defid:String,
                           val current_vote:String,
                           val written_on:String,
                           val example:String,
                           val author:String,
                           val thumbs_down:Int)

data class DictionaryResponse(val list :List<DictionaryModel>)



