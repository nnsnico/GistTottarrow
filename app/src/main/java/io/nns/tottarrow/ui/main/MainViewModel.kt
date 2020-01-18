package io.nns.tottarrow.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import io.nns.tottarrow.domain.response.Gist
import io.nns.tottarrow.domain.vo.GistError
import io.nns.tottarrow.usecase.GithubCreateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val githubUseCase: GithubCreateUseCase) : ViewModel() {
    val gists: MutableLiveData<Either<GistError, List<Gist>>> = MutableLiveData()

    fun searchInUser(q: String) =
        viewModelScope.launch {
            run {
                withContext(Dispatchers.IO) { gists.postValue(githubUseCase.createGist(q))  }
            }
        }
}
