package com.sample.translationapp.service

import com.sample.translationapp.data.entity.Translations
import com.sample.translationapp.data.entity.TranslationSupportedLanguage
import com.sample.translationapp.data.entity.TranslationVersion
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by Vinit Saxena on 18/09/21.
 */
interface TranslationApi {
    @GET("translation/{lang}")
    fun getTranslationByLanguage(@Path("lang") lang: String): Single<Translations>

    @GET("translation/version")
    fun getTranslationVersion(): Single<TranslationVersion>

    @GET("translation/suppourtedlanguages")
    fun getTranslationSupportedLanguage(): Single<List<TranslationSupportedLanguage>>
}