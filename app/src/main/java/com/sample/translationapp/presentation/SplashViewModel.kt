package com.sample.translationapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.translationapp.data.ILanguageService

/**
 * Created by Vinit Saxena on 19/09/21.
 */
class SplashViewModel(private val languageService: ILanguageService) : ViewModel() {
    private val liveData = MutableLiveData<SplashState>()
    val viewStateLiveData: LiveData<SplashState> = liveData;
    init {
        syncTranslation()
    }

    fun syncTranslation() {
        liveData.value = SplashState.Loading
        languageService.sync {
            liveData.postValue(SplashState.Loaded)
        }
    }
}

sealed class SplashState {
    object Loading : SplashState()
    object Loaded : SplashState()
}