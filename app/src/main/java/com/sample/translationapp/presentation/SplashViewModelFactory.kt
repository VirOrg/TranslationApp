package com.sample.translationapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.translationapp.data.ILanguageService

/**
 * Created by Vinit Saxena on 19/09/21.
 */
class SplashViewModelFactory constructor(private val languageService: ILanguageService) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(languageService) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }

    }

}