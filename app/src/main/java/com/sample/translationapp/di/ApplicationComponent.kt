package com.sample.translationapp.di

import com.sample.translationapp.presentation.MyApplication
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Vinit Saxena on 01/08/21.
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBuilderDI::class
    ]
)
interface ApplicationComponent {
    fun inject(application: MyApplication)
}