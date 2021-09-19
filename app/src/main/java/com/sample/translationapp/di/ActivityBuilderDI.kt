package com.sample.translationapp.di

import com.sample.translationapp.presentation.BonusActivity
import com.sample.translationapp.presentation.MainActivity
import com.sample.translationapp.presentation.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Vinit Saxena on 19/09/21.
 */
@Module

abstract class ActivityBuilderDI {
    @ContributesAndroidInjector(
        modules = [
            ViewModelDI::class
        ]
    )
    abstract fun provideSplashActivity(): SplashActivity

    @ContributesAndroidInjector(
        modules = [
            ViewModelDI::class
        ]
    )
    abstract fun provideMainActivity(): MainActivity

    @ContributesAndroidInjector(
        modules = [
            ViewModelDI::class
        ]
    )
    abstract fun provideBonusActivity(): BonusActivity
}