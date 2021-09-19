package com.sample.translationapp.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sample.translationapp.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: SplashViewModelFactory
    val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        AndroidInjection.inject(this)
        viewModel.viewStateLiveData.observe(this, Observer { state ->
            when (state) {
                SplashState.Loaded -> {
                    Intent(this, MainActivity::class.java).also {
                        startActivity(it)
                    }
                }
                SplashState.Loading ->
                    progressBar.visibility = View.VISIBLE

            }
        })
    }
}