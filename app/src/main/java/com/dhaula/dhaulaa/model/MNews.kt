package com.dhaula.dhaulaa.model

import android.text.Spanned
import com.dhaula.dhaulaa.utils.Action.getHtml

data class MNews(
    var description: String? = null,
    var heading: String? = null,
    var id: String? = null,
    var image: String? = null
) {
    fun getHead(): Spanned? {
        return getHtml(heading)
    }

    fun getDesc(): Spanned? {
        return getHtml(description)
    }
}