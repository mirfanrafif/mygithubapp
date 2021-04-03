package com.mirfanrafif.consumerapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favorite (
    var id: Int = 0,
    var login: String? = null,
    var nama: String? = null,
    var imageUrl: String? = null
): Parcelable