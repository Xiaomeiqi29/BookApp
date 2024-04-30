package com.example.bookapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book(
    val id: String? = null,
    val title: String? = null,
    val author: String? = null,
    val publishedDate: String? = null,
    val isbn: String? = null
) : Parcelable
