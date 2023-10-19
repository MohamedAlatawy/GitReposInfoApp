package com.example.repos

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.repos.feature_repositories.presentation.GitRepoListEvent
import com.example.repos.feature_repositories.presentation.GitRepoViewModel
import com.example.repos.feature_repositories.presentation.composables.GitRepoList
import com.example.repos.ui.theme.ReposTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReposTheme {

                val viewModel: GitRepoViewModel = hiltViewModel()

//                val gitReposInfo = viewModel.gitReposInfo.value
//
//                val loading = viewModel.loading.value

                val page = viewModel.page.value

                val state = viewModel.state.value



                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = " Git repositories ") },
                            colors = TopAppBarDefaults.mediumTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
                            )
                        )
                    },
                    bottomBar = {},
                ) {
                    GitRepoList(
                        gitRepoState = state,
                        onChangeGitRepoScrollPosition = viewModel::onChangeGitRepoScrollPosition,
                        page = page,
                        onNextPage = {
                            viewModel.onTriggerEvent(GitRepoListEvent.NextPageEvent)
                        }
                    )
                }
            }
        }
    }
}

