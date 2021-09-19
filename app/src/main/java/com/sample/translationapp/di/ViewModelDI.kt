package com.sample.translationapp.di


import com.sample.translationapp.data.ILanguageService
import com.sample.translationapp.presentation.SplashViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Vinit Saxena on 19/09/21.
 */
@Module
class ViewModelDI {

    @Provides
    fun provideSplashVMFactory(syncData: ILanguageService): SplashViewModelFactory =
        SplashViewModelFactory(syncData)

}