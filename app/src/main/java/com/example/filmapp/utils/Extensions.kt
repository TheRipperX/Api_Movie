package com.example.filmapp.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.initItemMe(adapter: RecyclerView.Adapter<*>, layout: RecyclerView.LayoutManager ) {

    this.adapter = adapter
    this.layoutManager = layout

}

fun initToastMe(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}