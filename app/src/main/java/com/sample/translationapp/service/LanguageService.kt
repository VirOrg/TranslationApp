package com.sample.translationapp.service

import android.content.Context
import androidx.annotation.RawRes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sample.translationapp.R
import com.sample.translationapp.data.ILanguageService
import com.sample.translationapp.data.entity.TranslationVersion
import com.sample.translationapp.data.entity.Translations
import com.sample.translationapp.presentation.SameIsInAlreadySyncException
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

/**
 * Created by Vinit Saxena on 18/09/21.
 */
class LanguageService(private val context: Context, retrofit: Retrofit, private val gson: Gson) :
    ILanguageService {
    private val translationMap = mutableMapOf<String, Map<String, String>>()
    private val translationApi: TranslationApi = retrofit.create(TranslationApi::class.java)

    override fun sync() {

        val localTranslationVersion = Single.fromCallable {
            val localTranslationVersion: TranslationVersion = readRawJson(R.raw.translation_version)
            return@fromCallable localTranslationVersion
        }
        val serverTranslationVersion = translationApi.getTranslationVersion()

        val disposable = Single
            .zip(
                localTranslationVersion,
                serverTranslationVersion,
                BiFunction { localVersion, serverVersion ->
                    Pair<Int, Int>(localVersion.version, serverVersion.version)
                })
            .flatMap { pair ->
                if (pair.second > pair.first) {
                    val listOfLangSingleAndCode = mutableListOf<Single<Translations>>()
                    translationApi
                        .getTranslationSupportedLanguage()
                        .flatMap { langs ->
                            langs.forEach {
                                listOfLangSingleAndCode.add(
                                    translationApi.getTranslationByLanguage(
                                        it.code
                                    )
                                )
                            }

                            return@flatMap Single.zip(listOfLangSingleAndCode, Function {
                                it.toList() as List<Translations>
                            })


                        }
                } else {
                    return@flatMap Single.fromCallable {
                        throw SameIsInAlreadySyncException()
                    }
                }
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ langsTrans ->
                langsTrans.forEach {
                    val transMap = mutableMapOf<String, String>()
                    it.translation.forEach {
                        transMap.put(it.key, it.value)
                    }
                    translationMap.put(it.code, transMap)
                }
            }, { exception ->
                exception.printStackTrace()
            })

    }

    private fun <T> readRawJson(@RawRes rawResId: Int): T {
        context.resources.openRawResource(rawResId).bufferedReader().use {
            return gson.fromJson<T>(it, object : TypeToken<T>() {}.type)
        }
    }
}