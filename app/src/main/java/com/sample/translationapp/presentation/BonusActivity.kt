package com.sample.translationapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sample.translationapp.R
import com.sample.translationapp.data.ILanguageService
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class BonusActivity : AppCompatActivity() {
    @Inject
    lateinit var languageService: ILanguageService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bonus)
        AndroidInjection.inject(this)
        text.text = languageService.translate(R.string.bonus_translation)
    }
}