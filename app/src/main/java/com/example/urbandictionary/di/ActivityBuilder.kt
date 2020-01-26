package com.example.urbandictionary.di

import com.example.urbandictionary.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {

   @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

}