package com.example.repos

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
                    topBar = { Text(text = "Git Repos") },
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

