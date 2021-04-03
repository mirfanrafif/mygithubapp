package com.mirfanrafif.mygithubapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavouritesItem (
    var id: Int = 0,
    var login: String? = null,
    var nama: String? = null,
    var imageUrl: String? = null
): Parcelable