package com.sample.translationapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sample.translationapp.R
import com.sample.translationapp.data.ILanguageService
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var languageService: ILanguageService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)
        text.text = languageService.translate(R.string.hello_world)
        btn_bonus.text = languageService.translate(R.string.bonus)

        btn_english.setOnClickListener {
            text.text = languageService.translate(R.string.hello_world, "en")
            btn_bonus.text = languageService.translate(R.string.bonus, "en")
        }

        btn_hindi.setOnClickListener {
            text.text = languageService.translate(R.string.hello_world, "hi")
            btn_bonus.text = languageService.translate(R.string.bonus, "hi")
        }

        btn_chinese.setOnClickListener {
            text.text = languageService.translate(R.string.hello_world, "zh")
            btn_bonus.text = languageService.translate(R.string.bonus, "zh")
        }

        btn_bonus.setOnClickListener {
            Intent(this, BonusActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}