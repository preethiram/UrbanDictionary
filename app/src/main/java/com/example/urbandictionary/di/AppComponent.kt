package com.example.urbandictionary.di

import android.app.Application
import com.example.urbandictionary.AppApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [(AppModule::class),(ActivityBuilder::class),(AndroidInjectionModule::class)])
interface AppComponent {

    fun inject(app :AppApplication);

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(app :Application) :Builder

        fun build():AppComponent
    }


}