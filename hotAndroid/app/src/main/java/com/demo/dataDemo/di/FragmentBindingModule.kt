package com.demo.dataDemo.di

import com.demo.dataDemo.ui.main.NumbersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @ContributesAndroidInjector
    abstract fun contributeNumbersFragment(): NumbersFragment

}