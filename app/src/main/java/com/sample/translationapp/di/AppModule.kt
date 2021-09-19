package com.sample.translationapp.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.sample.translationapp.data.ILanguageService
import com.sample.translationapp.service.LanguageService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Vinit Saxena on 01/08/21.
 */
@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideContext(): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://demo4108721.mockable.io/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideLanguageService(context: Context, retrofit: Retrofit, gson: Gson): ILanguageService {
        return LanguageService(context, retrofit, gson)
    }
}
