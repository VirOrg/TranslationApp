package com.sample.translationapp.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by Vinit Saxena on 19/09/21.
 */
data class TranslationSupportedLanguage(
    @SerializedName("code") val code: String, @SerializedName("name") val name: String
)