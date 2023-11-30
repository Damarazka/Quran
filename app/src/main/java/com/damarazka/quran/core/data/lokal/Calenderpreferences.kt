package com.damarazka.quran.core.data.lokal

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Calenderpreferences {
    val calendar = Calendar.getInstance().time

    fun getCurrentDate(): List<String> {
        val year = SimpleDateFormat("yyyy", Locale.getDefault()).format(calendar).toString()
        val month = SimpleDateFormat("MM", Locale.getDefault()).format(calendar).toString()
        val date = SimpleDateFormat("dd", Locale.getDefault()).format(calendar).toString()
        val now =
            SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault()).format(calendar).toString()
        return listOf(year, month, date, now)
    }
}