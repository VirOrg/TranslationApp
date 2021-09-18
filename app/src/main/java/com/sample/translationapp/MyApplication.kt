package com.sample.translationapp

import android.app.Application
import com.example.assignmentproject.di.AppModule
import com.example.assignmentproject.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Created by Vinit Saxena on 18/09/21.
 */
class MyApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    companion object {

        lateinit var INSTANCE: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .build().inject(this);


    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}