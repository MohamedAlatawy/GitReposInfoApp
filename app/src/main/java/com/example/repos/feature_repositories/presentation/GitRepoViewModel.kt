package com.example.repos.feature_repositories.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repos.core.util.PAGE_SIZE
import com.example.repos.core.util.Resource
import com.example.repos.core.util.TAG
import com.example.repos.feature_repositories.domain.model.GitRepoInfo
import com.example.repos.feature_repositories.domain.use_case.GetGitRepos
import com.example.repos.feature_repositories.presentation.GitRepoListEvent.NextPageEvent
import com.example.repos.feature_repositories.presentation.GitRepoListEvent.FirstPageEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GitRepoViewModel @Inject constructor(
    private val getGitRepos: GetGitRepos
): ViewModel() {

    val gitReposInfo: MutableState<List<GitRepoInfo?>> = mutableStateOf(listOf())

    val loading = mutableStateOf(false)

    private val _state = mutableStateOf(GitRepoState())
    val state: State<GitRepoState> = _state

    val page = mutableStateOf(1)

    var gitRepoScrollPosition = 0

    private var gitPageJob: Job? = null


    init {
        onTriggerEvent(FirstPageEvent)
    }

    fun onTriggerEvent(event: GitRepoListEvent) {
        viewModelScope.launch {

            try {
                when (event) {
                    is FirstPageEvent -> {
                        firstPage()
                    }

                    is NextPageEvent -> {
                        nextPage()
                    }
                }

            } catch (e: Exception) {
                Log.e(TAG, "onTriggerEvent: Exception: ${e}, ${e.cause}")
            }
        }
    }




    private suspend fun firstPage() {

        gitPageJob?.cancel()
        gitPageJob = viewModelScope.launch {
            delay(500L)
            getGitRepos(0)
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                gitRepoItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                            gitReposInfo.value = _state.value.gitRepoItems

                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                gitRepoItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                            gitReposInfo.value = _state.value.gitRepoItems

                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                gitRepoItems = result.data ?: emptyList(),
                                isLoading = true
                            )
                            gitReposInfo.value = _state.value.gitRepoItems

                        }
                    }
                }.launchIn(this)
        }

    }

    private suspend fun nextPage() {

        if ((gitRepoScrollPosition + 1) >= (page.value * PAGE_SIZE)) {


            if (page.value > 1) {
                gitPageJob?.cancel()
                gitPageJob = viewModelScope.launch {
                    delay(500L)
                    getGitRepos(page.value)
                        .onEach { result ->
                            when(result) {
                                is Resource.Success -> {
                                    _state.value = state.value.copy(
                                        gitRepoItems = appendGitRepos(result.data ?: emptyList()),
                                        isLoading = false
                                    )
                                }
                                is Resource.Error -> {
                                    _state.value = state.value.copy(
                                        gitRepoItems = appendGitRepos(result.data ?: emptyList()),
                                        isLoading = false
                                    )

                                }
                                is Resource.Loading -> {
                                    _state.value = state.value.copy(
                                        gitRepoItems = appendGitRepos(result.data ?: emptyList()),
                                        isLoading = true
                                    )
                                }
                            }
                        }.launchIn(this)
                }
            }
            incrementPage()

        }
    }

    private fun appendGitRepos(gitRepos: List<GitRepoInfo?>): List<GitRepoInfo?> {
        val current = ArrayList(gitReposInfo.value)
        current.addAll(gitRepos)

        gitReposInfo.value = current
        return gitReposInfo.value
    }

    private fun incrementPage() {
        page.value = page.value + 1
    }

    fun onChangeGitRepoScrollPosition(position: Int) {
        gitRepoScrollPosition = position
    }

}