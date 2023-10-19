package com.example.repos.feature_repositories.domain.model

import java.text.SimpleDateFormat
import java.util.*

data class RepoInfo(
    val creationDate: String,
    val id: Int
) {

    fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        val currentDate = Date()

        val sixMonthsInMillis = 6L * 30L * 24L * 60L * 60L * 1000L
        val diffInMillis = currentDate.time - date.time

        return if (diffInMillis > sixMonthsInMillis) {
            val outputFormat = SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.getDefault())
            outputFormat.format(date)
        } else {
            val diffInMonths = (diffInMillis / (30L * 24L * 60L * 60L * 1000L)).toInt()
            "$diffInMonths months ago"
        }
    }

}
