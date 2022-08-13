/*
 * Created by Nitheesh AG on 2022/8/13
 */

package com.heirarchylist.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heirarchylist.data.Repo
import com.heirarchylist.data.model.Heirarchy
import com.heirarchylist.data.model.ResponseModel
import com.heirarchylist.util.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


data class UIState(
    val loading: Boolean = true,
    val result: List<Heirarchy>? = null,
    val resultCopy: List<Heirarchy>? = null,
    val error: String? = null,
)

@HiltViewModel
class ListingScreenVM @Inject constructor(
    private val repository: Repo,
) : ViewModel() {

    private val _uiState = mutableStateOf(UIState())
    val uiState: State<UIState> = _uiState

    init {
        attemptAPICall()
    }

    fun search(query: String) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            resultCopy = _uiState.value.result?.filter {
                it.contactName.lowercase(Locale.ROOT).contains(query) ||
                        it.designationName.lowercase(Locale.ROOT).contains(query) ||
                        it.contactNumber.lowercase(Locale.ROOT).contains(query)
            }
        )
    }

    fun clear() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            resultCopy = _uiState.value.result,
        )
    }

    fun attemptAPICall() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            loading = true,
            error = null
        )
        when (val res = repository.getList()) {
            is ResultWrapper.Success -> {
                res.value.dataObject.getOrNull(0)?.myHierarchy?.getOrNull(0)?.heirarchyList?.let {
                    if (it.isEmpty())
                        _uiState.value = _uiState.value.copy(
                            loading = false,
                            result = null,
                            error = "Seems like there is nothing to show"
                        )
                    else
                        _uiState.value = _uiState.value.copy(
                            loading = false,
                            result = it,
                            resultCopy = it,
                            error = null
                        )
                }
            }
            is ResultWrapper.NetworkError -> {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    error = "No internet :("
                )
            }
            is ResultWrapper.GenericError -> {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    error = res.error
                )
            }
        }
    }


}