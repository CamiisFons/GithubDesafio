package camila.githubapp.ui.fragment.listRepository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import camila.githubapp.model.Repository
import camila.githubapp.repository.GitHubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryListViewModel @Inject constructor(private val repository: GitHubRepository) : ViewModel() {

    private lateinit var _repositoryList: Flow<PagingData<Repository>>
    val repositoryList: Flow<PagingData<Repository>>
    get() = _repositoryList

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error


    init {
        getRepositories()
    }

    private fun getRepositories() {
        viewModelScope.launch {
            val result =
                kotlin.runCatching { repository.getRepositoriesList().cachedIn(viewModelScope) }
            result.onSuccess { _repositoryList = it }
            result.onFailure { _error.value = it }
        }
    }

}