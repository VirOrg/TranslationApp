package com.sample.translationapp.service

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.google.gson.Gson
import com.sample.translationapp.R
import com.sample.translationapp.data.ILanguageService
import com.sample.translationapp.data.entity.TranslationVersion
import com.sample.translationapp.data.entity.Translations
import com.sample.translationapp.presentation.TranslationIsInAlreadySyncException
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.util.*


/**
 * Created by Vinit Saxena on 18/09/21.
 */
class LanguageService(private val context: Context, retrofit: Retrofit, private val gson: Gson) :
    ILanguageService {
    private val translationMap = mutableMapOf<String, Map<String, String>>()
    private val translationApi: TranslationApi = retrofit.create(TranslationApi::class.java)
    private val resources = context.resources
    private val assets = context.assets

    override fun sync(call: (Boolean) -> Unit) {
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
                        throw TranslationIsInAlreadySyncException()
                    }
                }
            }.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribe({ langsTrans ->
                langsTrans.forEach {
                    val transMap = mutableMapOf<String, String>()
                    it.translation.forEach {
                        transMap.put(it.key, it.value)
                    }
                    translationMap.put(it.code, transMap)
                }
                call.invoke(true)
            }, { exception ->
                exception.printStackTrace()
                call.invoke(false)
            })

    }

    override fun translate(@StringRes id: Int, lang: String): String {
        return getTranslation(id, lang)
    }

    override fun translate(id: Int): String {
        val defaultLocale = Resources.getSystem().getConfiguration().locale
        return getTranslation(id, defaultLocale.language)
    }

    private fun getTranslation(@StringRes resId: Int, lang: String): String {
        val key = try {
            resources.getResourceEntryName(resId)
        } catch (e: Resources.NotFoundException) {
            return ""
        }
        return if (translationMap.get(lang)?.containsKey(key) == true) {
            setLocale(lang)
            return translationMap.get(lang)?.get(key) ?: ""
        } else {
            try {
                getTranslationFromResource(resId, lang)
            } catch (e: Resources.NotFoundException) {
                return key
            }
        }
    }

    private fun getTranslationFromResource(@StringRes resId: Int, lang: String): String {
        return setLocale(lang).getString(resId)
    }

    private fun setLocale(lang: String): Resources {
        val confAr = resources.getConfiguration()
        confAr.locale = Locale(lang)
        val metrics = DisplayMetrics()
        val resources = Resources(assets, metrics, confAr)
        return resources
    }

    private fun readRawJson(@RawRes rawResId: Int): TranslationVersion {
        context.resources.openRawResource(rawResId).bufferedReader().use {
            return gson.fromJson(it, TranslationVersion::class.java)
        }
    }
}