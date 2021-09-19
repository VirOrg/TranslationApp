package com.sample.translationapp.data

import androidx.annotation.StringRes

/**
 * Created by Vinit Saxena on 18/09/21.
 */
interface ILanguageService {
    fun sync(call : (Boolean)->Unit)
    fun translate(@StringRes id: Int, lang: String): String
    fun translate(@StringRes id: Int): String
}