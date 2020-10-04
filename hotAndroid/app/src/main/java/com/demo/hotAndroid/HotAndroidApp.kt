package com.demo.hotAndroid

import android.app.Application
import com.demo.hotAndroid.di.AppComponent
import com.demo.hotAndroid.di.DaggerAppComponent
import com.demo.hotAndroid.util.Logger
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class HotAndroidApp : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    // TODO - Initialise all app singletons e.g. DataService, ConnectivityService

    lateinit var appComponent : AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        Logger.verbose("Application  created")
    }

    override fun androidInjector() = dispatchingAndroidInjector

}