package com.example.desafio_tcnico_android.util

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.desafio_tcnico_android.R

class TabNavigator(
    private val tabOneButton: TextView,
    private val tabTwoButton: TextView,
    private val tabOneIndicator: View,
    private val tabTwoIndicator: View,
    private val context: Context
) {
    fun setupTabs() {
        tabOneButton.setOnClickListener { selectTabOne() }
        tabTwoButton.setOnClickListener { selectTabTwo() }
    }

    private fun selectTabOne() {
        tabOneIndicator.visibility = View.VISIBLE
        tabTwoIndicator.visibility = View.GONE
        tabOneButton.setTextColor(ContextCompat.getColor(context, R.color.primary_blue))
        tabTwoButton.setTextColor(ContextCompat.getColor(context, R.color.gray))
    }

    private fun selectTabTwo() {
        tabOneIndicator.visibility = View.GONE
        tabTwoIndicator.visibility = View.VISIBLE
        tabOneButton.setTextColor(ContextCompat.getColor(context, R.color.gray))
        tabTwoButton.setTextColor(ContextCompat.getColor(context, R.color.primary_blue))
    }
}