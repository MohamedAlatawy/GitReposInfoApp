package com.example.repos.feature_repositories.presentation

import android.util.Log

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GitRepoViewModel @Inject constructor(
    private val getGitRepos: GetGitRepos
): ViewModel() {

    private val _state = MutableStateFlow(GitRepoState())
    val state = _state.asStateFlow()

    private val _page = MutableStateFlow(1)
    val page = _page.asStateFlow()


    private val gitRepoScrollPosition = MutableStateFlow(0)

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




    private fun firstPage() {
        gitPageJob?.cancel()
        gitPageJob = viewModelScope.launch {
            getGitRepos(0)
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                gitRepoItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                gitRepoItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                gitRepoItems = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }

    }

    private suspend fun nextPage() {

        if ((gitRepoScrollPosition.value + 3) >= (_page.value * PAGE_SIZE)) {

            if (_page.value > 1) {
                gitPageJob?.cancel()
                gitPageJob = viewModelScope.launch {
                    delay(500L)
                    getGitRepos(_page.value * PAGE_SIZE)
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
       return  _state.value.gitRepoItems.toMutableList().apply {
            addAll(gitRepos)
        }
    }

    private fun incrementPage() {
        _page.value = _page.value + 1
    }

    fun onChangeGitRepoScrollPosition(position: Int) {
        gitRepoScrollPosition.value = position
    }

}