package com.sample.translationapp.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by Vinit Saxena on 18/09/21.
 */
data class Translations(@SerializedName("translation") val translation: List<TranslationData>, @SerializedName("code") val code: String)
data class TranslationData(
    @SerializedName("key") val key: String,
    @SerializedName("value") val value: String
)
