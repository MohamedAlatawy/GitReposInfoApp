package com.example.repos.feature_repositories.presentation.composables

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.repos.R


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LoadAvatar(
    url: String,
) {
    GlideImage(
        model = url ,
        contentDescription = " Owner avatar",
        modifier = Modifier.size(100.dp)
    ) {
        it.error(R.drawable.default_avatar)
            .load(url)
    }
}






