package com.kenny.bookreview.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenny.bookreview.data.local.BookReviewVo
import com.kenny.bookreview.data.local.BookVo
import com.kenny.bookreview.domain.paginator.DefaultPaginator
import com.kenny.bookreview.domain.paginator.ScreenState
import com.kenny.bookreview.domain.use_case.GetBookReviewsUseCase
import com.kenny.bookreview.domain.use_case.GetPopularBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getPopularBookUseCase: GetPopularBookUseCase,
    getBookReviewsUseCase: GetBookReviewsUseCase
) : ViewModel() {

    private val _recentPopularBookList = MutableStateFlow<List<BookVo>>(emptyList())
    val recentPopularBookList = _recentPopularBookList.asStateFlow()

    init {
        viewModelScope.launch {
            getPopularBookUseCase().collect {
                _recentPopularBookList.value = it
            }
        }
    }

    var state by mutableStateOf(ScreenState<BookReviewVo>())

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            getBookReviewsUseCase(nextPage, 20)
        },
        getNextKey = {
            state.page + 1
        },
        onError = { error ->
            state = state.copy(error = error?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                items = state.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    fun loadNextItem() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    init {
        loadNextItem()
    }

}