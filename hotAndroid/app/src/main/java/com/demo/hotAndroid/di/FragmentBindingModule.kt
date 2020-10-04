package com.demo.hotAndroid.di

import com.demo.hotAndroid.ui.main.PostsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @ContributesAndroidInjector
    abstract fun contributePostsListFragment(): PostsListFragment

}