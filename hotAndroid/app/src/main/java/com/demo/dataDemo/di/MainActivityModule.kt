package com.demo.dataDemo.di

import com.demo.dataDemo.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBindingModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
