package com.demo.dataDemo

import android.app.Application
import com.demo.dataDemo.api.DataService
import com.demo.dataDemo.di.AppComponent
import com.demo.dataDemo.di.DaggerAppComponent
import com.demo.dataDemo.util.Logger
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class DemoAndroidApp : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject lateinit var dataService: DataService

    lateinit var appComponent : AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        Logger.verbose("Application created")
    }

    override fun androidInjector() = dispatchingAndroidInjector

}