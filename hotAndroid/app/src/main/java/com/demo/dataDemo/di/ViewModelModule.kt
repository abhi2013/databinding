package com.demo.dataDemo.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.dataDemo.viewmodel.NumbersViewModel
import com.demo.dataDemo.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/*
Creates a map of all the view models that will be provided to ViewModelFactory as argument
 */
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(NumbersViewModel::class)
    abstract fun bindsNumbersViewModel(numbersViewModel: NumbersViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
