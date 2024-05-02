package com.example.bookapp.extension

import androidx.recyclerview.widget.RecyclerView

fun <VH : RecyclerView.ViewHolder?> RecyclerView.Adapter<VH>.rightPosition(adapterPosition: Int): Int? {
    return if (adapterPosition in 0 until itemCount) {
        adapterPosition
    } else {
        null
    }
}